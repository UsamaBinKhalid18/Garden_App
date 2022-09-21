package com.example.gardenapp.datahandling.adapters

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.ItemSelectListener
import com.example.gardenapp.R
import com.example.gardenapp.datahandling.Data
import com.example.gardenapp.datahandling.Plant

class AllPlantsListAdapter(private val itemSelectListener: ItemSelectListener) :
    RecyclerView.Adapter<AllPlantsListAdapter.PlantViewHolder>() {
    inner class PlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlantName: TextView = view.findViewById(R.id.tv_plant_name_card)
        val ivPlant: ImageView = view.findViewById(R.id.iv_plant_card)
        val cardView: CardView = view.findViewById(R.id.cv_all_plants)
        val btAddToGarden: ImageButton = view.findViewById(R.id.bt_add_to_garden)
        val btSharePlant:ImageButton=view.findViewById(R.id.bt_share_plant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_all_plants, parent, false)
        return PlantViewHolder(layout)
    }

    override fun getItemCount(): Int = Data.plantsList.count()

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = Data.plantsList[position]
        holder.tvPlantName.text = plant.name
        try {
            holder.ivPlant.setImageURI(Uri.parse(plant.imageUriPath))
        }catch (e:Exception){
            holder.ivPlant.setImageResource(R.drawable.img_place_holder)
        }
        setOnClickListeners(holder,plant)
    }

    private fun setOnClickListeners(holder:PlantViewHolder,plant: Plant) {
        holder.btSharePlant.setOnClickListener{
            val intent= Intent(Intent.ACTION_SEND)
            intent.type="image/jpg"
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(plant.imageUriPath))
            ContextCompat.startActivity(
                holder.itemView.context,
                Intent.createChooser(intent, "Share Image"),
                null
            )
        }
        holder.cardView.setOnClickListener {
            itemSelectListener.onClick(plant)
        }
        holder.btAddToGarden.setImageResource(
            when (plant.location) {
                1 -> R.drawable.ic_card_button_frontyard
                2 -> R.drawable.ic_card_button_backyard
                3 -> R.drawable.ic_card_button_rooftop
                4 -> R.drawable.ic_card_button_indoors
                else -> R.drawable.outline_box
            }
        )
        holder.btAddToGarden.setOnClickListener {
            val categories = arrayOf("Frontyard", "Backyard", "Rooftop", "Indoor")
            if (plant.location != 0) {
                Data.removePlantFromGarden(plant, plant.location)
                plant.location = 0
                (it as ImageButton).setImageResource(R.drawable.outline_box)
            } else {
                AlertDialog.Builder(holder.itemView.context).apply {
                    setTitle("Select Location")
                    setSingleChoiceItems(
                        categories,
                        -1
                    )
                    { _, i ->
                        plant.location = i + 1
                        Data.addPlantToGarden(plant, plant.location)
                        holder.btAddToGarden.setImageResource(
                            when (plant.location) {
                                1 -> R.drawable.ic_card_button_frontyard
                                2 -> R.drawable.ic_card_button_backyard
                                3 -> R.drawable.ic_card_button_rooftop
                                4 -> R.drawable.ic_card_button_indoors
                                else -> R.drawable.outline_box
                            }
                        )
                    }
                    setPositiveButton("Done"){ _, _ -> }
                    setNegativeButton("Cancel") { _, _ ->
                        plant.location = 0
                        (it as ImageButton).setImageResource(R.drawable.outline_box)
                    }
                    create().apply { setCanceledOnTouchOutside(false);show() }
                }
            }
        }
    }
}
