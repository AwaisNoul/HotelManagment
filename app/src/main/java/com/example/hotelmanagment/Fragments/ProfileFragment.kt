package com.example.hotelmanagment.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.hotelmanagment.Activitys.EditProfile
import com.example.hotelmanagment.Activitys.Signup
import com.example.hotelmanagment.Models.SigninModel
import com.example.hotelmanagment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.HashMap

class ProfileFragment : Fragment() {

    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var logout: TextView
    lateinit var editprofile: TextView
    private val PICK_IMAGE_REQUEST = 1
    lateinit var firebase: DatabaseReference
    lateinit var photofirebase: DatabaseReference
    lateinit var authkey: String
    lateinit var circularImageView: CircleImageView
    private lateinit var auth: FirebaseAuth
    lateinit var selectedProfileImage:String

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        firebase = FirebaseDatabase.getInstance().getReference("User")
        photofirebase = FirebaseDatabase.getInstance().getReference("Photo")
        auth = FirebaseAuth.getInstance()
        name = view.findViewById(R.id.name)
        email = view.findViewById(R.id.email)
        editprofile = view.findViewById(R.id.editprofile)
        logout = view.findViewById(R.id.logout)
        circularImageView = view.findViewById(R.id.circularImageView)

        circularImageView.setOnClickListener {
            openGallery()
        }
        editprofile.setOnClickListener {
            startActivity(Intent(requireActivity(),EditProfile::class.java))
        }

        logout.setOnClickListener {
            showConfirmationDialog()
        }
        authkey = auth.currentUser?.uid.toString()

        FirebaseDatabase.getInstance().getReference("Photo").child(authkey).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children){
                    val str = snap.getValue(String::class.java)
                    selectedProfileImage = str!!
                    Picasso.get().load(selectedProfileImage).into(circularImageView)
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
        return view
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data

            if (selectedImageUri != null) {
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference.child("images/$authkey")
                storageRef.putFile(selectedImageUri).addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                        Log.i("tag", it.toString())
                        var map = HashMap<String,Any>()
                        map["url"]=it.toString()
                        photofirebase.child(authkey).updateChildren(map)


                    }.addOnFailureListener{
                        Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { dialog, which ->
                auth.signOut()
                val intent = Intent(requireContext(), Signup::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}