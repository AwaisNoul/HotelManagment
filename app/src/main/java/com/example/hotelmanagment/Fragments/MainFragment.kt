package com.example.hotelmanagment.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelmanagment.Adapter.MainAdapter
import com.example.hotelmanagment.Models.AddHotelModel
import com.example.hotelmanagment.R
import com.google.firebase.database.*

class MainFragment : Fragment() {

    private lateinit var firebase: DatabaseReference
    lateinit var recyclerview : RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerview = view.findViewById(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        val list: MutableList<AddHotelModel> = mutableListOf()
        firebase = FirebaseDatabase.getInstance().getReference("AddHotel")
        firebase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (snap in snapshot.children){
                    val model = snap.getValue(AddHotelModel::class.java)
                    list.add(model!!)
                }
                val adapter = MainAdapter(list)
                recyclerview.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return view
    }
}