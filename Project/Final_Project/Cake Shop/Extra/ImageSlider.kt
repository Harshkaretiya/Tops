package com.example.e_commerce2.Extra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.e_commerce2.R
import com.smarteist.autoimageslider.SliderViewAdapter

class ImageSlider(var images : ArrayList<String>) : SliderViewAdapter<ImageSlider.MyHolder>() {
    inner class MyHolder(itemView: View?) : ViewHolder(itemView) {
        var imageView: ImageView = itemView!!.findViewById(R.id.imageView)
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): MyHolder? {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.slider_item,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyHolder?, position: Int) {
        if (images[position] != null) {
//            Picasso.get().load(images[position]).into(viewHolder!!.imageView)
            Glide.with(viewHolder!!.imageView)
                .load(images[position])
                .into(viewHolder.imageView)
        }
    }

}