package com.example.gardenapp

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.gardenapp.datahandling.Data
import com.example.gardenapp.datahandling.Plant
import com.google.android.material.textfield.TextInputEditText
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class InsertPlantActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var uriForCapturedImage: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_plant)
        setSupportActionBar(findViewById(R.id.tlb_insert_plant_activity))
        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        findViewById<Button>(R.id.bt_new_photo).setOnClickListener {
            val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }
            uriForCapturedImage = FileProvider.getUriForFile(
                applicationContext,
                "${BuildConfig.APPLICATION_ID}.provider",
                tmpFile
            )
            if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
                imageResultActivity.launch(uriForCapturedImage)
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "Need Camera Access",
                    101,
                    Manifest.permission.CAMERA
                )
            }
        }

//        findViewById<Button>(R.id.bt_photo_from_gallery).setOnClickListener {
//            selectImageFromGalleryResult.launch("image/*")
//
//        }

        findViewById<Button>(R.id.bt_add_new_plant).setOnClickListener {
            val plantId = findViewById<TextInputEditText>(R.id.et_plantId).text.toString()
            val plantName = findViewById<TextInputEditText>(R.id.et_plant_name).text.toString()
            val plantDesc =
                findViewById<TextInputEditText>(R.id.et_plant_description).text.toString()
            val growthZone =
                findViewById<TextInputEditText>(R.id.et_zone_number).text.toString().toInt()
            val wateringInterval =
                findViewById<TextInputEditText>(R.id.et_watering_interval).text.toString().toInt()
            //findViewById<ImageView>(R.id.image_view_insert_plant).bitm
            Data.plantsList.add(
                Plant(
                    plantName,
                    plantId,
                    plantDesc,
                    growthZone,
                    wateringInterval,
                    location = 0,
                    imageUriPath = uriForCapturedImage.toString(),
                    imageUrl = null
                )
            )
            finish()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    private val imageResultActivity =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            findViewById<ImageView>(R.id.image_view_insert_plant).setImageURI(uriForCapturedImage)
        }

//    private val selectImageFromGalleryResult =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            uri?.let{uriForCapturedImage=it}
//            uri?.let { findViewById<ImageView>(R.id.image_view_insert_plant).setImageURI(uri) }
//        }

}