package com.example.gardenapp.datahandling

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.example.gardenapp.datahandling.adapters.CategoryItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type
import java.net.URL
import java.util.concurrent.Executors

object Data {

    private const val FILE_PATH = "/plants.json"
    val plantsList = mutableListOf<Plant>()
    val categoryList = mutableListOf(
        CategoryItem("Frontyard", mutableListOf()),
        CategoryItem("Backyard", mutableListOf()),
        CategoryItem("Rooftop", mutableListOf()),
        CategoryItem("Indoors", mutableListOf())
    )

    private val threadPool = Executors.newFixedThreadPool(3)

    fun loadPlantsList(context: Context) {
        val dataString = getDataString(context)
        val list = plantListFromDataString(dataString, context)
        plantsList.addAll(list)
        initializeMyGarden()
        downloadImages(context)
    }

    private fun getDataString(context: Context): String {
        val dataFile = File(context.filesDir.path + FILE_PATH)

        return if (dataFile.exists()) {
            val string: String = FileInputStream(dataFile).reader().readText()
            if (string.isNotEmpty())
                string
            else
                context.assets.open("flowers.json").reader().readText()
        } else {
            context.assets.open("flowers.json").reader().readText()
        }
    }


    private fun plantListFromDataString(dataString: String, context: Context): List<Plant> {
        val listType: Type = object : TypeToken<ArrayList<Plant?>?>() {}.type
        try {
            return Gson().fromJson(dataString, listType)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Could not load local data, Using default data for app",
                Toast.LENGTH_SHORT
            ).show()
            return Gson().fromJson(
                context.assets.open("flowers.json").reader().readText(),
                listType
            )
        }

    }

    private fun downloadImages(context: Context) {
        for (plant in plantsList) {
            var imageDoesNotExist = true

            try {
                val inputStream: InputStream? =
                    context.contentResolver.openInputStream(Uri.parse(plant.imageUriPath))
                inputStream?.close()
                imageDoesNotExist = false
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (plant.imageUriPath == null || imageDoesNotExist) {
                threadPool.execute {
                    try {
                        val inputStream = URL(plant.imageUrl).openStream()
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        val filename = "${plant.name}.jpg"
                        var fos: OutputStream? = null
                        context.contentResolver?.also { resolver ->
                            val contentValues = ContentValues().apply {
                                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                                put(
                                    MediaStore.MediaColumns.RELATIVE_PATH,
                                    Environment.DIRECTORY_PICTURES
                                )
                            }
                            val imageUri: Uri? = resolver.insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                contentValues
                            )
                            fos = imageUri?.let { resolver.openOutputStream(it) }
                            plant.imageUriPath = imageUri?.toString()

                        }
                        fos?.use {
                            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Error while downloading please check your internet connectivity or try later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        threadPool.shutdown()
    }

    private fun initializeMyGarden() {
        for (plant in plantsList) {
            if (plant.location != 0) {
                categoryList[plant.location - 1].plantsList.add(plant)
            }
        }
    }

    fun savePlantsList(context: Context) {
        try {
            val outStream = FileOutputStream(File(context.filesDir.path + FILE_PATH))
            outStream.write(Gson().toJson(plantsList).toByteArray())
            outStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun removePlantFromGarden(plant: Plant, location: Int) {
        categoryList[location - 1].plantsList.remove(plant)
    }

    fun addPlantToGarden(plant: Plant, location: Int) {
        categoryList[location - 1].plantsList.add(plant)
    }


}