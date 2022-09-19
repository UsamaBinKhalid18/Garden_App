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

class MyGardenAdapter(private val itemSelectListener: ItemSelectListener) :
    RecyclerView.Adapter<MyGardenAdapter.PlantViewHolder>() {
    inner class PlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlantName: TextView = view.findViewById(R.id.tv_plant_name_card)
        val ivPlant: ImageView = view.findViewById(R.id.iv_plant_card)
        val cardView: CardView = view.findViewById(R.id.cv_all_plants)
        val ibAddToGarden: ImageButton = view.findViewById(R.id.bt_add_to_garden)
    }

    override fun getItemCount(): Int = Data.myGardenList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_all_plants, parent, false)
        return PlantViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = Data.myGardenList[position]
        holder.tvPlantName.text = plant.name
        if (plant.imageUriPath != null)
            holder.ivPlant.setImageURI(Uri.parse(plant.imageUriPath))

        holder.cardView.setOnClickListener {
            itemSelectListener.onClick(plant)
        }

        holder.ibAddToGarden.setOnClickListener {
            plant.inMyGarden = false
            Data.removeFromGardenList(plant)
            notifyItemRemoved(position)

        }
    }

}