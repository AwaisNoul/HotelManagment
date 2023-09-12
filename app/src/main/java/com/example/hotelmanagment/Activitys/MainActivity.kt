package com.example.hotelmanagment.Activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.hotelmanagment.Fragments.MainFragment
import com.example.hotelmanagment.Fragments.ProfileFragment
import com.example.hotelmanagment.Models.AddHotelModel
import com.example.hotelmanagment.Models.SigninModel
import com.example.hotelmanagment.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav: SmoothBottomBar
    lateinit var nav_view: NavigationView
    lateinit var profileImage: CircleImageView
    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var authkey: String
    private lateinit var auth: FirebaseAuth
    lateinit var selectedProfileImage:String
    lateinit var firebase: DatabaseReference
    lateinit var addhotelfirebase: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, MainFragment())
            .commit()

        firebase = FirebaseDatabase.getInstance().getReference("User")
        addhotelfirebase = FirebaseDatabase.getInstance().getReference("Add Hotel")
        bottomNav = findViewById(R.id.bottomNav)
        nav_view = findViewById(R.id.nav_view)
        val headerView: View = nav_view.getHeaderView(0)
        profileImage = headerView.findViewById(R.id.profileImage)
        name = headerView.findViewById(R.id.name)
        email = headerView.findViewById(R.id.email)
        auth = FirebaseAuth.getInstance()
        authkey = auth.currentUser?.uid.toString()

        FirebaseDatabase.getInstance().getReference("Photo").child(authkey).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children){
                    val str = snap.getValue(String::class.java)
                    selectedProfileImage = str!!
                    Picasso.get().load(selectedProfileImage).into(profileImage)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        firebase.child(authkey).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val data = dataSnapshot.getValue(SigninModel::class.java)
                    name.text = data?.firename
                    email.text = data?.email
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read data.", error.toException())
            }
        })

        nav_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_rate -> {
                    val rateUsDialog = RateUsDialogue(context = this@MainActivity)
                    rateUsDialog.window?.setBackgroundDrawable(ColorDrawable(getColor(android.R.color.transparent)))
                    rateUsDialog.setCancelable(false)
                    rateUsDialog.show()
                    true
                }

                R.id.nav_share -> {
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "This is the text you want to share")
                    startActivity(Intent.createChooser(shareIntent, "Share via"))
                    true
                }

                R.id.add_hotel -> {
                    if (auth.currentUser != null){
                        startActivity(Intent(this@MainActivity,AddHotel::class.java))
                    }else{
                        startActivity(Intent(this@MainActivity,Signup::class.java))
                    }
                    true
                }
                else -> false
            }
        }

        bottomNav.setOnItemSelectedListener {
            when (it) {
                0 -> replaceFragment(MainFragment())
                1 -> replaceFragment(ProfileFragment())
                2 -> replaceFragment(MainFragment())
                3 -> replaceFragment(ProfileFragment())
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}