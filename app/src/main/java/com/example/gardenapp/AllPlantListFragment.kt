package com.example.gardenapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.datahandling.Plant
import com.example.gardenapp.datahandling.adapters.AllPlantsListAdapter
import com.google.gson.Gson


class AllPlantListFragment : Fragment(),ItemSelectListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_plant_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView:RecyclerView=view.findViewById(R.id.rv_all_flower_list_fragment)
        recyclerView.adapter= AllPlantsListAdapter(this)
    }

    override fun onClick(plant: Plant) {
        startActivity(
            Intent(context,PlantDetailActivity::class.java).putExtra("flowerString",
                Gson().toJson(plant)))
    }


}
