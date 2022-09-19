package com.example.gardenapp.datahandling.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.ItemSelectListener
import com.example.gardenapp.R
import com.example.gardenapp.datahandling.Data

class MyGardenParentAdapter(private val itemSelectListener: ItemSelectListener) :
    RecyclerView.Adapter<MyGardenParentAdapter.ParentViewHolder>() {
    inner class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategory: TextView = view.findViewById(R.id.tv_category_name)
        val childRecyclerView: RecyclerView = view.findViewById(R.id.rv_child)
        val cardView: CardView = view.findViewById(R.id.card_view)
    }

    override fun getItemCount(): Int = Data.categoryList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_layout, parent, false)
        return ParentViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val categoryItem = Data.categoryList[position]
        holder.tvCategory.text = categoryItem.categoryName
        holder.childRecyclerView.adapter = MyGardenChildAdapter(position, itemSelectListener)

        holder.cardView.background.setTint(
            ContextCompat.getColor(
                holder.itemView.context, when (position) {
                    1 -> R.color.frontyard
                    2 -> R.color.backyard
                    3 -> R.color.rooftop
                    else -> R.color.indoors
                }
            )
        )
    }

}