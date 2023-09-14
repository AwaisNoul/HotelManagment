package com.example.hotelmanagment.Activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.hotelmanagment.Guides.Guide1
import com.example.hotelmanagment.R
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

         val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
         val editor: SharedPreferences.Editor = sharedPreferences.edit()
        var g=sharedPreferences.getBoolean("g",false)

        Handler().postDelayed({
            if (!g){
                val intent = Intent(this@SplashScreen, Guide1::class.java)
                startActivity(intent)
                finish()
                editor.putBoolean("g",true)
                editor.apply()
            }else{
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, SPLASH_DELAY)
    }
}