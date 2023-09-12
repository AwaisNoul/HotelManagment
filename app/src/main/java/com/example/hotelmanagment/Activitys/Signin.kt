package com.example.hotelmanagment.Activitys

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.hotelmanagment.R
import com.example.hotelmanagment.Models.SigninModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class Signin : AppCompatActivity() {

    private lateinit var dateOfBirthEditText: EditText
    private val calendar = Calendar.getInstance()
    lateinit var fullName : EditText
    lateinit var pNumber : EditText
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var signin : TextView
    private lateinit var auth: FirebaseAuth
    lateinit var firebase :DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        firebase = FirebaseDatabase.getInstance().getReference("User")

        fullName = findViewById(R.id.fullName)
        pNumber = findViewById(R.id.pNumber)
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText)
        email = findViewById(R.id.semail)
        password = findViewById(R.id.spassword)
        signin = findViewById(R.id.signin)
        auth = FirebaseAuth.getInstance()

        dateOfBirthEditText.setOnClickListener {
            showDatePickerDialog()
        }

        signin.setOnClickListener {
            val fName = fullName.text.toString()
            val number = pNumber.text.toString()
            val bDate = dateOfBirthEditText.text.toString()
            val mail = email.text.toString().trim()
            val pWord = password.text.toString().trim()

            if (mail.isEmpty() || pWord.isEmpty()) {
                Toast.makeText(this@Signin, "Please enter email and password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                auth.createUserWithEmailAndPassword(mail, pWord)
                    .addOnCompleteListener(this@Signin) { task ->
                        if (task.isSuccessful) { Toast.makeText(this@Signin,"User created successfully", Toast.LENGTH_SHORT).show()
                            var key = auth.currentUser?.uid.toString()
                            firebase.child(key).setValue(SigninModel(fName, number, bDate, mail, pWord))
                            startActivity(Intent(this, AddHotel::class.java))
                        } else {
                            Toast.makeText(
                                this@Signin,
                                "User not created: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    private fun showDatePickerDialog() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateOfBirthEditText()
        }

        DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateOfBirthEditText() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        dateOfBirthEditText.setText(dateFormat.format(calendar.time))
    }
}