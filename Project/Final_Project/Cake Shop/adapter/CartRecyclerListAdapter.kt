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
import com.example.e_commerce2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRecyclerListAdapter(var context: Context, var list: MutableList<Model>) : RecyclerView.Adapter<MyView3>()
{
    private lateinit var apiInterface: ApiInterface
    lateinit var sharedPreferences: SharedPreferences
    var onImageClickListener: OnImageClickListener? = null
    lateinit var auth: FirebaseAuth

    interface OnImageClickListener {
        fun onImageClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView3 {
        var data = LayoutInflater.from(context)
        var view = data.inflate(R.layout.cart_design,parent,false)
        return MyView3(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyView3, @SuppressLint("RecyclerView") position: Int) {

        sharedPreferences = context.getSharedPreferences("User_Session", Context.MODE_PRIVATE)
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
            var i = Intent(context, ProductViewActivity::class.java)
            i.putExtra("pid",list[position].pid)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }

        Picasso.get().load(list[position].image).placeholder(R.mipmap.ic_launcher).into(holder.product_img)
        holder.name.setText(list[position].name)
        holder.qty.text = list[position].qty.toString()
        holder.price.text = list[position].totalprice.toString()

        var qty = holder.qty.text.toString().toInt()

        holder.add.setOnClickListener {
            progressDialog.show()
            if (qty<10) {
                qty++
                var call: Call<Void> = apiInterface.cartupdate(pid, uid, qty)
                call.enqueue(object : Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        holder.qty.text = qty.toString()

                        var call2: Call<Model> = apiInterface.cartcheck(pid,uid)
                        call2.enqueue(object : Callback<Model> {
                            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                                holder.price.text = response.body()!!.totalprice.toString()
                                list[position].totalprice = response.body()!!.totalprice
                                onImageClickListener?.onImageClick()
                                progressDialog.dismiss()
                            }
                            override fun onFailure(call: Call<Model>, t: Throwable) {
                                progressDialog.dismiss()
                                //update the number on display only not no database
                            }
                        })

                    }
                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        qty--
                        holder.qty.text = qty.toString()
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                })
            }
            else
                Toast.makeText(context, "maximum reached", Toast.LENGTH_SHORT).show()
        }
        holder.subtract.setOnClickListener {
            progressDialog.show()
            if (qty>1) {
                qty--
                var call: Call<Void> = apiInterface.cartupdate(pid,uid,qty)
                call.enqueue(object :Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        holder.qty.text = qty.toString()

                        var call2: Call<Model> = apiInterface.cartcheck(pid,uid)
                        call2.enqueue(object : Callback<Model> {
                            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                                holder.price.text = response.body()!!.totalprice.toString()
                                list[position].totalprice = response.body()!!.totalprice
                                onImageClickListener?.onImageClick()
                                progressDialog.dismiss()
                            }
                            override fun onFailure(call: Call<Model>, t: Throwable) {
                                progressDialog.dismiss()
                                //update the number on display only not no database
                            }
                        })
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        qty++
                        holder.qty.text = qty.toString()
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                })
            } else
                Toast.makeText(context, "minimum reached", Toast.LENGTH_SHORT).show()

        }

        holder.delete.setOnClickListener {
            progressDialog.show()
            var call3: Call<Void> = apiInterface.cartdelete(pid,uid)
            call3.enqueue(object:Callback<Void?>{
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    list.removeAt(position)
                    notifyDataSetChanged()
                    onImageClickListener?.onImageClick()
                    progressDialog.dismiss()
                }
                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                }
            })
        }

    }
}
class MyView3(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    var product_img : ImageView =itemView.findViewById(R.id.image_cart_design)
    var name : TextView =itemView.findViewById(R.id.product_name_cart_design)
    var price : TextView = itemView.findViewById(R.id.product_price_cart_design)
    var qty : TextView = itemView.findViewById(R.id.quantity_cart_design)
    var add : ImageView = itemView.findViewById(R.id.quantity_add_cart_design)
    var subtract : ImageView = itemView.findViewById(R.id.quantity_subtract_cart_design)
    var delete : ImageView = itemView.findViewById(R.id.delete_cart_design)
}