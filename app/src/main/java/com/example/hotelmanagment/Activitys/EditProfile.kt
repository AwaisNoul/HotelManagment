package com.example.hotelmanagment.Activitys

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.hotelmanagment.Models.SigninModel
import com.example.hotelmanagment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.*

class EditProfile : AppCompatActivity() {

    lateinit var firebase: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var authkey: String
    lateinit var fullName: EditText
    lateinit var pNumber: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    private lateinit var dateOfBirthEditText: EditText
    lateinit var editProfile: TextView
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        fullName = findViewById(R.id.fullName)
        pNumber = findViewById(R.id.pNumber)
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText)
        email = findViewById(R.id.semail)
        password = findViewById(R.id.spassword)
        editProfile = findViewById(R.id.editProfile)
        auth = FirebaseAuth.getInstance()

        authkey = auth.currentUser?.uid.toString()
        firebase = FirebaseDatabase.getInstance().getReference("User").child(authkey)
        Log.i("TAG", "onCreate:key -  $authkey")
//        firebase.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                    var str = snapshot.getValue(SigninModel::class.java)
//                    fullName.setText(str!!.firename)
//                    dateOfBirthEditText.setText(str!!.birthDate)
//                    email.setText(str!!.email)
//                    password.setText(str!!.password)
//                    pNumber.setText(str!!.pNumber)
//            }
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })

        editProfile.setOnClickListener {
            val fName = fullName.text.toString()
            val number = pNumber.text.toString()
            val bDate = dateOfBirthEditText.text.toString()
            val mail = email.text.toString().trim()
            val pWord = password.text.toString().trim()

            if (mail.isEmpty() || pWord.isEmpty()) {
                Toast.makeText(this@EditProfile, "Please fill all edittext", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val ref = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().currentUser!!.uid)
                val model = SigninModel(fName, number, bDate, mail, pWord)
                ref.setValue(model)
                Toast.makeText(this, "User Updated Successfully", Toast.LENGTH_SHORT).show()
                finish()

//                startActivity(Intent(this@EditProfile, ProfileFragment::class.java))
            }
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}