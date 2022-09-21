package com.example.gardenapp.datahandling.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.ItemSelectListener
import com.example.gardenapp.R
import com.example.gardenapp.datahandling.Data

class MyGardenChildAdapter(
    private val categoryID: Int,
    private val itemSelectListener: ItemSelectListener
) : RecyclerView.Adapter<MyGardenChildAdapter.ChildViewHolder>() {
    inner class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlantName: TextView = view.findViewById(R.id.tv_plant_name_card)
        val ivPlant: ImageView = view.findViewById(R.id.iv_plant_card)
        val cardView: CardView = view.findViewById(R.id.card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_my_garden, parent, false)
        return ChildViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val plant = Data.categoryList[categoryID].plantsList[position]
        holder.tvPlantName.text = plant.name
        try{
            holder.ivPlant.setImageURI(Uri.parse(plant.imageUriPath))
        }catch(e:Exception){
            holder.ivPlant.setImageResource(R.drawable.img_place_holder)
        }
        holder.cardView.setOnClickListener {
            itemSelectListener.onClick(plant)
        }
    }

    override fun getItemCount(): Int = Data.categoryList[categoryID].plantsList.count()


}