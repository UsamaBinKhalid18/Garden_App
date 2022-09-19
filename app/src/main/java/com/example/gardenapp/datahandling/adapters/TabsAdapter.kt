package com.example.gardenapp.datahandling.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gardenapp.AllPlantListFragment
import com.example.gardenapp.MyGardenFragment

class TabsAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =when(position){
        0->AllPlantListFragment()
        else->MyGardenFragment()
    }
}