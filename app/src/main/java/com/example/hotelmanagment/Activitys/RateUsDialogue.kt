package com.example.hotelmanagment.Activitys

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.hotelmanagment.R

class RateUsDialogue(context: Context) : Dialog(context) {
    var userRate = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rate_us_dialogue_layout)

        val rateNowBtn: AppCompatButton = findViewById(R.id.rateNow)
        val laterBtn: AppCompatButton = findViewById(R.id.laterBtn)
        val ratingBar: RatingBar = findViewById(R.id.ratingBar)
        val ratingImage: ImageView = findViewById(R.id.ratingImage)

        rateNowBtn.setOnClickListener {
            Toast.makeText(context, "Rate now", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        laterBtn.setOnClickListener {
            Toast.makeText(context, "Later", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            when {
                rating <= 1 -> ratingImage.setImageResource(R.drawable.image_one)
                rating <= 2 -> ratingImage.setImageResource(R.drawable.image_1)
                rating <= 3 -> ratingImage.setImageResource(R.drawable.image_2)
                rating <= 4 -> ratingImage.setImageResource(R.drawable.image_3)
                rating <= 5 -> ratingImage.setImageResource(R.drawable.image_4)
            }
            animateImage(ratingImage)

            userRate = rating
        }

    }

    private fun animateImage(ratingImage: ImageView) {
        val scaleAnimation = ScaleAnimation(
            0f, 1f, 0f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.fillAfter = true
        scaleAnimation.duration = 200
        ratingImage.startAnimation(scaleAnimation)
    }

}