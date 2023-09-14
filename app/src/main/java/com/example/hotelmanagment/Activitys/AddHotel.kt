package com.example.hotelmanagment.Activitys

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelmanagment.Models.AddHotelModel
import com.example.hotelmanagment.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddHotel : AppCompatActivity() {

    lateinit var image1: ImageView
    lateinit var image2: ImageView
    private val PICK_IMAGE_REQUEST1 = 1
    private val PICK_IMAGE_REQUEST2 = 2
    lateinit var firebase: DatabaseReference
    lateinit var key: String
    lateinit var hotelfullname: EditText
    lateinit var hotelemail: EditText
    lateinit var hotelphonenumber: EditText
    lateinit var hotellocation: EditText
    lateinit var hotelprice: EditText
    lateinit var overview: EditText
    lateinit var addhotel: TextView
    var fImage: String = ""
    var sImage: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hotel)

        firebase = FirebaseDatabase.getInstance().getReference("AddHotel")
        key = firebase.push().key.toString()

        image1 = findViewById(R.id.image1)
        image2 = findViewById(R.id.image2)
        hotelfullname = findViewById(R.id.hotelfullname)
        hotelemail = findViewById(R.id.hotelemail)
        hotelphonenumber = findViewById(R.id.hotelphonenumber)
        hotellocation = findViewById(R.id.hotellocation)
        overview = findViewById(R.id.overview)
        addhotel = findViewById(R.id.addhotel)
        hotelprice = findViewById(R.id.hotelprice)

//        addhotel.setOnClickListener {
//            startActivity(Intent(this@AddHotel,Next::class.java))
//        }


        addhotel.setOnClickListener {

            val hName = hotelfullname.text.toString()
            val hEmail = hotelemail.text.toString()
            val hphonenumber = hotelphonenumber.text.toString()
            val hlocation = hotellocation.text.toString()
            val hPrice = hotelprice.text.toString()
            val hoverview = overview.text.toString()

            if (hName.isEmpty() || hEmail.isEmpty() || hphonenumber.isEmpty() || hlocation.isEmpty() || hPrice.isEmpty() || fImage.isEmpty() || hoverview.isEmpty()) {
                Toast.makeText(this@AddHotel, "Please Fill all blank edittext", Toast.LENGTH_SHORT)
                    .show()
            }

            else {
                val intent = Intent(this@AddHotel,Next::class.java)
                intent.putExtra("hName",hName)
                intent.putExtra("hEmail",hEmail)
                intent.putExtra("hphonenumber",hphonenumber)
                intent.putExtra("hlocation",hlocation)
                intent.putExtra("hPrice",hPrice)
                intent.putExtra("hoverview",hoverview)
                intent.putExtra("fImage",fImage)
                intent.putExtra("sImage",sImage)
                intent.putExtra("key",key)
                startActivity(intent)
                finish()
            }

        }

        image1.setOnClickListener {
            openGallery1()
        }

        image2.setOnClickListener {
            openGallery2()
        }

    }

    private fun openGallery1() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST1)
    }

    private fun openGallery2() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST1 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            image1.setImageURI(selectedImageUri)
            if (selectedImageUri != null) {
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference.child("images/$key")
                storageRef.putFile(selectedImageUri).addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                        Log.i("tag", it.toString())
                        fImage = it.toString()
                    }.addOnFailureListener {
                        Toast.makeText(this@AddHotel, it.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST2 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            image2.setImageURI(selectedImageUri)
            if (selectedImageUri != null) {
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference.child("images/$key"+"1")
                storageRef.putFile(selectedImageUri).addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                        Log.i("tag", it.toString())
                        sImage = it.toString()
                    }.addOnFailureListener {
                        Toast.makeText(this@AddHotel, it.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}