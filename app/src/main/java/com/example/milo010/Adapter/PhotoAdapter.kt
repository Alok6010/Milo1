package com.example.milo010.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.milo010.Data.Photo
import com.example.milo010.R

class PhotoAdapter(
    private val listofPhotos: List<Photo>,
    private val context: Context

):RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(
        itemView: View
    ):RecyclerView.ViewHolder(itemView){
        val personImg : ImageView = itemView.findViewById(R.id.person_img)
        val hobby1 : TextView = itemView.findViewById(R.id.hobby_1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_photo,parent,false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Return the number of items in your data set
        return listofPhotos.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentPhoto = listofPhotos[position]
        holder.hobby1.text = currentPhoto.hobby1
        //image code is left for that we use glide
        Glide.with(context)
            .load(currentPhoto.personPhoto)
            .into(holder.personImg)

    }
}