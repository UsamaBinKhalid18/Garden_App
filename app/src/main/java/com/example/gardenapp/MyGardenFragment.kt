package com.example.gardenapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.datahandling.Plant
import com.example.gardenapp.datahandling.adapters.MyGardenAdapter
import com.google.gson.Gson


class MyGardenFragment : Fragment(),ItemSelectListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_garden, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView:RecyclerView=view.findViewById(R.id.rv_my_garden)
        recyclerView.adapter=MyGardenAdapter(this)
    }

    override fun onResume() {
        view?.findViewById<RecyclerView>(R.id.rv_my_garden)?.adapter?.notifyDataSetChanged()
        super.onResume()
    }
    override fun onClick(plant: Plant) {
        startActivity(
            Intent(context,PlantDetailActivity::class.java).putExtra("flowerString",
                Gson().toJson(plant)))
    }
}