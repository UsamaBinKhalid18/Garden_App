package com.example.gardenapp

import com.example.gardenapp.datahandling.Plant

interface ItemSelectListener {
    fun onClick(plant: Plant)
}