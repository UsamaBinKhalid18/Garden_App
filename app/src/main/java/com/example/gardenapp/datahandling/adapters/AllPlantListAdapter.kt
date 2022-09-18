package com.example.gardenapp.datahandling.adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.R
import com.example.gardenapp.datahandling.Data

class AllPlantsListAdapter():RecyclerView.Adapter<AllPlantsListAdapter.PlantViewHolder>() {
    inner class PlantViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvPlantName:TextView=view.findViewById(R.id.tv_plant_name_card)
        val ivPlant:ImageView=view.findViewById(R.id.iv_plant_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val layout=LayoutInflater.from(parent.context).inflate(R.layout.card_view_all_plants,parent,false)
        return PlantViewHolder(layout)
    }

    override fun getItemCount(): Int = Data.plantsList.count()

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant=Data.plantsList[position]
        holder.tvPlantName.text=plant.name
        Log.d("TAG", "onBindViewHolder: $position")
        if(plant.imageUriPath!=null)
            holder.ivPlant.setImageURI(Uri.parse(plant.imageUriPath))
    }
}
