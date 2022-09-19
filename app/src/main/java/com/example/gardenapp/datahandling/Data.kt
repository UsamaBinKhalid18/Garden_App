package com.example.gardenapp.datahandling

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type
import java.net.URL
import java.util.concurrent.Executors

object Data {

    private const val FILE_PATH="/plants.json"
    val plantsList= mutableListOf<Plant>()
    val myGardenList= mutableListOf<Plant>()
    private val threadPool = Executors.newFixedThreadPool(3)

    fun loadPlantsList(context: Context){
            val dataString=getDataString(context)
            val list=plantListFromDataString(dataString)
            plantsList.addAll(list)
            updateMyGarden()
            downloadImages(context)
    }

    private fun getDataString(context: Context):String {
        val dataFile = File(context.filesDir.path + FILE_PATH)

        return if (dataFile.exists()) {
            val string:String=FileInputStream(dataFile).reader().readText()
            if(string.isNotEmpty())
                string
            else
                context.assets.open("flowers.json").reader().readText()
        } else {
            context.assets.open("flowers.json").reader().readText()
        }
    }


    private fun plantListFromDataString(dataString: String): List<Plant> {
        val listType: Type = object : TypeToken<ArrayList<Plant?>?>() {}.type
        return Gson().fromJson(dataString, listType)

    }

    private fun downloadImages(context: Context) {
        for (plant in plantsList){
            var imageDoesNotExist=true

            try{
                val inputStream: InputStream? = context.contentResolver.openInputStream(Uri.parse(plant.imageUriPath))
                inputStream?.close()
                imageDoesNotExist=false
            }catch (e:Exception){
                e.printStackTrace()
            }

            if(plant.imageUriPath==null||imageDoesNotExist){
                Log.d("TAG", "downloadImages: ")
                threadPool.execute{
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
                    Log.d("TAG", "downloadImages: ${plant.name}")
                }
                fos?.use {
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
                }
            }
        }
        threadPool.shutdown()
    }

    private fun updateMyGarden() {
        for(plant in plantsList){
            if(plant.inMyGarden){
                myGardenList.add(plant)
            }
        }
    }

    fun savePlantsList(context: Context){
            val outStream= FileOutputStream(File(context.filesDir.path+FILE_PATH))
            outStream.write(Gson().toJson(plantsList).toByteArray())
            outStream.close()
    }

    fun addToGardenList(plant:Plant){
        myGardenList.add(plant)
    }

    fun removeFromGardenList(plant: Plant) {
        myGardenList.remove(plant)
    }
}