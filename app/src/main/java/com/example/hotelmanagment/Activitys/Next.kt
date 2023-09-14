package com.example.hotelmanagment.Activitys

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.hotelmanagment.Models.AddHotelModel
import com.example.hotelmanagment.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Next : AppCompatActivity() {

    lateinit var description: EditText
    lateinit var whatsnear: EditText
    lateinit var topattractions: EditText
    lateinit var addhotel: TextView
    lateinit var firebase: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        firebase = FirebaseDatabase.getInstance().getReference("AddHotel")
        description = findViewById(R.id.description)
        whatsnear = findViewById(R.id.whatsnear)
        topattractions = findViewById(R.id.topattractions)
        addhotel = findViewById(R.id.addhotel)
        val hName = intent.getStringExtra("hName")
        val hEmail = intent.getStringExtra("hEmail")
        val hphonenumber = intent.getStringExtra("hphonenumber")
        val hlocation = intent.getStringExtra("hlocation")
        val hPrice = intent.getStringExtra("hPrice")
        val hoverview = intent.getStringExtra("hoverview")
        val fImage = intent.getStringExtra("fImage")
        val sImage = intent.getStringExtra("sImage")
        val key = intent.getStringExtra("key")

        addhotel.setOnClickListener {
            val des = description.text.toString()
            val wnear = whatsnear.text.toString()
            val attract = topattractions.text.toString()
            if (des.isEmpty() || wnear.isEmpty() || attract.isEmpty()) {
                Toast.makeText(this@Next, "Please Fill all blank edittext", Toast.LENGTH_SHORT)
            } else {
                firebase.child(key.toString()).setValue(
                    AddHotelModel(
                        key.toString(),
                        hName.toString(),
                        hEmail.toString(),
                        hphonenumber.toString(),
                        hPrice.toString(),
                        hlocation.toString(),
                        fImage.toString(),
                        sImage.toString(),
                        hoverview.toString(),
                        des,
                        wnear,
                        attract
                    )
                )
                finish()
            }
        }


    }
}