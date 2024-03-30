package com.example.milo010.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.milo010.Data.UserPhotos
import com.example.milo010.R

class UserPhotosAdapter(
    private val listofPhotos: List<UserPhotos>,
    private val context: Context
):RecyclerView.Adapter<UserPhotosAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val personImg : ImageView = itemView.findViewById(R.id.person_img)
//        val hobby1 : TextView = itemView.findViewById(R.id.hobby_1)
//        val hobby2 : TextView = itemView.findViewById(R.id.hobby_2)
//        val hobby3 : TextView = itemView.findViewById(R.id.hobby_3)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_photos_layout,parent,false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Return the number of items in your data set
        return listofPhotos.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        val currentPhoto = listofPhotos[position]
//        holder.hobby1.text = currentPhoto.hobby1
//        holder.hobby2.text = currentPhoto.hobby2
//        holder.hobby3.text = currentPhoto.hobby3

        //image code is left for that we use glide
        Glide.with(context)
            .load(currentPhoto.personPhoto)
            .into(holder.personImg)
    }

}