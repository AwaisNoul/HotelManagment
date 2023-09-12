package com.example.hotelmanagment.Activitys

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelmanagment.Models.AddHotelModel
import com.example.hotelmanagment.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddHotel : AppCompatActivity() {

    lateinit var image1: ImageView
    lateinit var image2: ImageView
    lateinit var image3: ImageView
    private val PICK_IMAGE_REQUEST = 1
    private val PICK_IMAGE_REQUEST1 = 2
    private val PICK_IMAGE_REQUEST2 = 3
    lateinit var firebase : DatabaseReference
    lateinit var key : String
    lateinit var hotelfullname : EditText
    lateinit var hotelemail : EditText
    lateinit var hotelphonenumber : EditText
    lateinit var hotellocation : EditText
    lateinit var overview : EditText
    lateinit var addhotel : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hotel)

        firebase = FirebaseDatabase.getInstance().getReference("AddHotel")
        key = firebase.push().key.toString()

        image1 = findViewById(R.id.image1)
        image2 = findViewById(R.id.image2)
        image3 = findViewById(R.id.image3)
        hotelfullname = findViewById(R.id.hotelfullname)
        hotelemail = findViewById(R.id.hotelemail)
        hotelphonenumber = findViewById(R.id.hotelphonenumber)
        hotellocation = findViewById(R.id.hotellocation)
        overview = findViewById(R.id.overview)
        addhotel = findViewById(R.id.addhotel)


        image1.setOnClickListener {
            openGallery()
        }
        image2.setOnClickListener {
            openGallery1()
        }
        image3.setOnClickListener {
            openGallery2()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    private fun openGallery1() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST1)
    }   private fun openGallery2() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            image1.setImageURI(selectedImage)
        }

        if (requestCode == PICK_IMAGE_REQUEST1 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            image2.setImageURI(selectedImage)
        }


        if (requestCode == PICK_IMAGE_REQUEST2 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            image3.setImageURI(selectedImage)
        }
    }


}