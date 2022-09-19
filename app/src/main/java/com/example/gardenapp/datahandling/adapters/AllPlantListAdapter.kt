package com.example.gardenapp.datahandling.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.ItemSelectListener
import com.example.gardenapp.R
import com.example.gardenapp.datahandling.Data

class AllPlantsListAdapter(private val itemSelectListener: ItemSelectListener) :
    RecyclerView.Adapter<AllPlantsListAdapter.PlantViewHolder>() {
    inner class PlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlantName: TextView = view.findViewById(R.id.tv_plant_name_card)
        val ivPlant: ImageView = view.findViewById(R.id.iv_plant_card)
        val cardView: CardView = view.findViewById(R.id.cv_all_plants)
        val ibAddToGarden: ImageButton = view.findViewById(R.id.bt_add_to_garden)
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
        if (plant.imageUriPath != null)
            holder.ivPlant.setImageURI(Uri.parse(plant.imageUriPath))

        holder.cardView.setOnClickListener {
            itemSelectListener.onClick(plant)
        }
        holder.ibAddToGarden.setImageResource(
            if (plant.inMyGarden)
                R.drawable.ic_card_button
            else
                R.drawable.outline_box
        )
        holder.ibAddToGarden.setOnClickListener {
            plant.inMyGarden = !plant.inMyGarden
            if (plant.inMyGarden) {
                Data.addToGardenList(plant)
                (it as ImageButton).setImageResource(R.drawable.ic_card_button)
            } else {
                Data.removeFromGardenList(plant)
                (it as ImageButton).setImageResource(R.drawable.outline_box)
            }
        }

    }
}
