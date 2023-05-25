package com.example.e_commerce2.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.activity.ProductViewActivity
import com.example.e_commerce2.Extra.Model
import com.example.e_commerce2.Extra.ModelOrder
import com.example.e_commerce2.R
import com.example.e_commerce2.activity.OrderViewActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRecyclerListAdapter(var context: Context, var list: MutableList<ModelOrder>) : RecyclerView.Adapter<MyView6>()
{
    private lateinit var apiInterface: ApiInterface
    lateinit var sharedPreferences: SharedPreferences
    lateinit var auth: FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView6 {
        var data = LayoutInflater.from(context)
        var view = data.inflate(R.layout.order_design,parent,false)
        return MyView6(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyView6, @SuppressLint("RecyclerView") position: Int) {

//        val uid = sharedPreferences.getInt("uid",101)
        auth = Firebase.auth
        val uid = auth.currentUser!!.uid

        var pid = list[position].pid

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)

        var progressDialog = Dialog(context)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)
        progressDialog.dismiss()

        holder.itemView.setOnClickListener {
            var i = Intent(context, OrderViewActivity::class.java)
            i.putExtra("oid",list[position].oid)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }

        Picasso.get().load(list[position].image).placeholder(R.mipmap.ic_launcher).into(holder.product_img)
        holder.name.setText(list[position].name)
        holder.qty.text = list[position].qty.toString()
        holder.price.text = list[position].totalprice.toString()




    }
}
class MyView6(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    var product_img : ImageView =itemView.findViewById(R.id.image_order_design)
    var name : TextView =itemView.findViewById(R.id.product_name_order_design)
    var price : TextView = itemView.findViewById(R.id.product_price_order_design)
    var qty : TextView = itemView.findViewById(R.id.quantity_order_design)
}