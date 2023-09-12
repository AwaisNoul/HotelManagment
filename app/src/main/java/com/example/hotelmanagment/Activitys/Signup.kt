package com.example.hotelmanagment.Activitys

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.*
import com.example.hotelmanagment.R
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {

    lateinit var signup : TextView
    lateinit var signin : ImageView
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var auth : FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signup = findViewById(R.id.signup)
        signin = findViewById(R.id.signin)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        auth = FirebaseAuth.getInstance()

        val checkBox = findViewById<CheckBox>(R.id.checkbox)
        val passwordEditText = findViewById<EditText>(R.id.password)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Show password
                passwordEditText.transformationMethod = null
            } else {
                // Hide password
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        signup.setOnClickListener {

            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()

            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this@Signup, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(this@Signup, "wrong email and password please first signin", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@Signup,AddHotel::class.java))
                        }
                    }
            }
        }
        signin.setOnClickListener {
            var intent = Intent(this@Signup, Signin::class.java)
            startActivity(intent)
        }
    }
}