package com.example.gardenapp

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gardenapp.datahandling.Plant
import com.google.gson.Gson

class PlantDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)
        setSupportActionBar(findViewById(R.id.tlb_detail_activity))

        val jsonString = intent.extras?.getString("flowerString")
        val plant = Gson().fromJson(jsonString, Plant::class.java)

        setUIValues(plant)
    }

    private fun setUIValues(plant: Plant) {
        findViewById<com.google.android.material.appbar.CollapsingToolbarLayout>(R.id.collapsing_toolbar).title =
            plant.name
        findViewById<ImageView>(R.id.iv_plant_detail).setImageURI(Uri.parse(plant.imageUriPath))
        findViewById<TextView>(R.id.tv_plantId).text = getString(R.string.plantId, plant.plantId)
        findViewById<TextView>(R.id.tv_plant_description).text = plant.description
        findViewById<TextView>(R.id.tv_zone_number).text =
            getString(R.string.zone_number, plant.growZoneNumber)
        findViewById<TextView>(R.id.tv_watering_interval).text = getString(
            R.string.watering_interval,
            plant.wateringInterval,
            resources.getQuantityString(R.plurals.hour, plant.wateringInterval)
        )
    }
}
