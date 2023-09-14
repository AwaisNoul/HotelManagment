package com.example.hotelmanagment.Activitys

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.hotelmanagment.Models.AddHotelModel
import com.example.hotelmanagment.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class CompleteHotelDetails : AppCompatActivity() {

    lateinit var image2: ImageView
    lateinit var image1: CircleImageView
    lateinit var hname: TextView
    lateinit var email: TextView
    lateinit var atrraction: TextView
    lateinit var hnumber: TextView
    lateinit var discription: TextView
    lateinit var firebase: DatabaseReference
    lateinit var key: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_hotel_details)

        image2 = findViewById(R.id.coverimage)
        image1 = findViewById(R.id.profileimage)
        hname = findViewById(R.id.name)
        email = findViewById(R.id.hemail)
        hnumber = findViewById(R.id.hnumber)
        atrraction = findViewById(R.id.atrraction)
        discription = findViewById(R.id.discription)
        key = intent.getStringExtra("KEY_NAME").toString()

        firebase = FirebaseDatabase.getInstance().getReference("AddHotel")

        firebase.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val hotel = dataSnapshot.getValue(AddHotelModel::class.java)
                    if (hotel != null) {
                        val hotelName = hotel.hotelName
                        hname.text = hotelName
                        val fimage = hotel.profileImage
                        Picasso.get().load(fimage).into(image1)
                        val simage = hotel.coverImage
                        Picasso.get().load(simage).into(image2)
                        val disc = hotel.description
                        discription.text = disc
                        val mail = hotel.hotelEmail
                        email.text = mail
                        val num = hotel.hotelphoneNumber
                        hnumber.text = num
                        val atr = hotel.attractions
                        atrraction.text = atr
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}