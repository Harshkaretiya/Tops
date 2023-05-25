package com.example.e_commerce2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.*
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.activity.ProductViewActivity
import com.example.e_commerce2.Extra.Model
import com.squareup.picasso.Picasso

class RecyclerListAdapter(var context: Context, var list: MutableList<Model>) : RecyclerView.Adapter<MyView>()
{
    private lateinit var apiInterface: ApiInterface
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        var data = LayoutInflater.from(context)
        var view = data.inflate(R.layout.list_design,parent,false)
        return MyView(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyView, @SuppressLint("RecyclerView") position: Int) {

        sharedPreferences = context.getSharedPreferences("User_Session",Context.MODE_PRIVATE)

        holder.itemView.setOnClickListener {
            var i = Intent(context, ProductViewActivity::class.java)
            i.putExtra("pid",list[position].pid)
//            i.putExtra("cpage",page)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
        Picasso.get().load(list[position].image).placeholder(R.mipmap.ic_launcher).into(holder.product_img)
        holder.name.setText(list[position].name)
        holder.price.setText(list[position].price)
//        holder.descrition.setText(list[position].desc)
    }
}
class MyView(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    var product_img : ImageView =itemView.findViewById(R.id.product_img)
    var name : TextView =itemView.findViewById(R.id.product_name)
    var price : TextView = itemView.findViewById(R.id.product_price)
//    var descrition : TextView = itemView.findViewById(R.id.product_desc)
}
