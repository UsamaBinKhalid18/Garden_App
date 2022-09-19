package com.example.gardenapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2
import com.example.gardenapp.datahandling.Data
import com.example.gardenapp.datahandling.adapters.TabsAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.tlb_main_activity))

        if(Data.plantsList.isEmpty()) {
            Data.loadPlantsList(applicationContext)
        }

        val viewPager=findViewById<ViewPager2>(R.id.main_viewPager)
        val tabLayout=findViewById<TabLayout>(R.id.main_tab_layout)

        viewPager.adapter= TabsAdapter(this)

        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text=when(position){
                0->"All Plants"
                else->"My Garden"
            }
            tab.icon=when(position){
                0->AppCompatResources.getDrawable(this,R.drawable.ic_all_plants)
                else->AppCompatResources.getDrawable(this,R.drawable.ic_my_garden)
            }
        }.attach()
    }

    override fun onDestroy() {
        Data.savePlantsList(applicationContext)
        super.onDestroy()
    }
}