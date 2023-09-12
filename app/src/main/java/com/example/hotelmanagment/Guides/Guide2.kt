package com.example.hotelmanagment.Guides

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.hotelmanagment.Activitys.MainActivity
import com.example.hotelmanagment.R
import com.example.hotelmanagment.Activitys.Signup

class Guide2 : AppCompatActivity() {

    lateinit var nextBtn : TextView
    lateinit var skipBtn : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide2)

        nextBtn = findViewById(R.id.nextBtn)
        skipBtn = findViewById(R.id.skipBtn)
        nextBtn.setOnClickListener {
            var intent = Intent(this@Guide2, Guide3::class.java)
            startActivity(intent)
        }
        skipBtn.setOnClickListener {
            var intent = Intent(this@Guide2, MainActivity::class.java)
            startActivity(intent)
        }
    }
}