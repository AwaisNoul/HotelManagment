package com.example.hotelmanagment.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.example.hotelmanagment.R

class RateUs : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var txtRatingValue: TextView
    private lateinit var btnSubmit: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_us)


        addListenerOnRatingBar()
        addListenerOnButton()

    }

    private fun addListenerOnRatingBar() {
        ratingBar = findViewById(R.id.ratingBar)
        txtRatingValue = findViewById(R.id.txtRatingValue)

        // If the rating value is changed,
        // display the current rating value in the result (TextView) automatically.
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            txtRatingValue.text = rating.toString()
        }
    }

    private fun addListenerOnButton() {
        ratingBar = findViewById(R.id.ratingBar)
        btnSubmit = findViewById(R.id.btnSubmit)

        // If clicked, display the current rating value.
        btnSubmit.setOnClickListener {
            Toast.makeText(
                this@RateUs,
                ratingBar.rating.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}