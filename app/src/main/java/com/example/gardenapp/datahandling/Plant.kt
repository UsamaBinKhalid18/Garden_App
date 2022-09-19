package com.example.gardenapp.datahandling

data class Plant (val plantId:String, val name:String, val description:String, val growZoneNumber:Int, val wateringInterval:Int, val imageUrl:String?, var imageUriPath: String?,var location:Int=0)