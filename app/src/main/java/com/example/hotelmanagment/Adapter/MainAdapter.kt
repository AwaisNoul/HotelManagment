package com.example.hotelmanagment.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelmanagment.Activitys.CompleteHotelDetails
import com.example.hotelmanagment.Fragments.MainFragment
import com.example.hotelmanagment.Models.AddHotelModel
import com.example.hotelmanagment.R
import com.squareup.picasso.Picasso

class MainAdapter (val songs: List<AddHotelModel>): RecyclerView.Adapter<MainAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.main_item_view,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = songs[position].hotelName
        holder.location.text = songs[position].location
        holder.price.text = songs[position].hotelPrice
        val uri = songs[position].profileImage
        Picasso.get().load(uri).into(holder.pImage)
        val key = songs[position].key

        holder.container.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CompleteHotelDetails::class.java)
            intent.putExtra("KEY_NAME", key)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.name)
        var location = itemView.findViewById<TextView>(R.id.hotelLocation)
        var price = itemView.findViewById<TextView>(R.id.price)
        var pImage = itemView.findViewById<ImageView>(R.id.profileImage)
        var container = itemView.findViewById<LinearLayout>(R.id.contanier)
    }
}