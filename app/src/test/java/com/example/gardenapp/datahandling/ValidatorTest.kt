package com.example.gardenapp.datahandling

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class ValidatorTest{
    @Test
    fun validName_returnsTrue(){
        val name="Roots"
        val result=Validator.isValidName(name)
        assertThat(result).isTrue()
    }

    @Test
    fun inValidName_returnsFalse(){
        val name="23 westwood"
        val result=Validator.isValidName(name)
        assertThat(result).isFalse()
    }

    @Test
    fun validWateringInterval_returnsTrue(){
        val interval=23
        val result=Validator.isValidWateringInterval(interval)
        assertThat(result).isTrue()
    }
    @Test
    fun inValidWateringInterval_returnFalse(){
        val interval=49
        val result=Validator.isValidWateringInterval(interval)
        assertThat(result).isFalse()
    }

    @Test
    fun validZoneNumber_returnsTrue(){
        val zoneNumber=23
        val result=Validator.isValidGrowthZone(zoneNumber)
        assertThat(result).isTrue()
    }
    @Test
    fun inValidZoneNumber_returnsFalse(){
        val zoneNumber=-1
        val result=Validator.isValidGrowthZone(zoneNumber)
        assertThat(result).isFalse()
    }
}