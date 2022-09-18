package com.example.gardenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.commit
import com.example.gardenapp.datahandling.Data

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.tlb_main_activity))

        if(Data.plantsList.isEmpty()) {
            Data.loadPlantsList(applicationContext)
        }

        supportFragmentManager.commit { setReorderingAllowed(true)
            add(R.id.fcv_main_activity,AllPlantListFragment())
        }
    }

    override fun onDestroy() {
        Data.savePlantsList(applicationContext)
        super.onDestroy()
    }
}