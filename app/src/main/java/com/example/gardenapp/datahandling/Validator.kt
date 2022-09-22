package com.example.gardenapp.datahandling

object Validator {
    fun areValid(plantId: String, plantName: String, growthZone: Int, wateringInterval: Int): Boolean {
        var result=true
        result=result&&isValidName(plantId)
        result=result&& isValidName(plantName)
        result=result && isValidGrowthZone(growthZone)
        result=result && isValidWateringInterval(wateringInterval)

        return result
    }

    fun isValidWateringInterval(wateringInterval: Int)= (wateringInterval in 0..48)
    fun isValidName(plantId: String)= (plantId.matches(Regex("[a-z A-Z]+")))
    fun isValidGrowthZone(growthZone: Int)=(growthZone in 0..99)
}