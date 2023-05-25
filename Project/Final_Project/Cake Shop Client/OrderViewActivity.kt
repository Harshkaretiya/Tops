package com.example.e_commerce2client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2client.Extra.ModelOrder
import com.example.e_commerce2client.databinding.ActivityManageProductBinding
import com.example.e_commerce2client.databinding.ActivityOrderViewBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewActivity : AppCompatActivity() {

    private lateinit var binding :ActivityOrderViewBinding
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val oid = i.getIntExtra("oid",0)


        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        val call: Call<ModelOrder> = apiInterface.getorderid(oid)
        call.enqueue(object : Callback<ModelOrder> {
            override fun onResponse(call: Call<ModelOrder>, response: Response<ModelOrder>) {
                if (this!=null) {
                    if (response.isSuccessful) {
                        Picasso.get().load(response.body()!!.image).into(binding.image)
                        binding.productName.setText(response.body()!!.name)
                        binding.productPriceOrderDesign.setText(response.body()!!.price)
                        binding.orderStatus.setText(response.body()!!.status)
                        binding.locationArea.setText(response.body()!!.street)
                        binding.locationCity.setText(response.body()!!.city)

                        var status = response.body()!!.status
                        binding.AcceptOrder.visibility = View.VISIBLE

                        if (status == "Order Accepted"){
                            binding.AcceptOrder.visibility = View.GONE
                            binding.startPreparing.visibility = View.VISIBLE
                        } else if (status == "started Preparing"){
                            binding.startPreparing.visibility = View.GONE
                            binding.AcceptOrder.visibility = View.GONE
                            binding.outForDelivery.visibility = View.VISIBLE
                        } else if (status == "Out For Delivery"){
                            binding.outForDelivery.visibility = View.GONE
                            binding.AcceptOrder.visibility = View.GONE
                            binding.delivered.visibility = View.VISIBLE
                        } else if (status == "Delivered"){
                            binding.AcceptOrder.visibility = View.GONE
                            binding.delivered.visibility = View.GONE
                        }
                    }
                }

            }
            override fun onFailure(call: Call<ModelOrder>, t: Throwable) {
                Toast.makeText(applicationContext,"some error occurs", Toast.LENGTH_LONG).show()
            }
        })

        binding.AcceptOrder.setOnClickListener {
            val call2: Call<Void> = apiInterface.orderupdate("Order Accepted",oid)
            call2.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        binding.AcceptOrder.visibility = View.GONE
                        binding.startPreparing.visibility = View.VISIBLE
                        recreate()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fail to add Address", Toast.LENGTH_LONG).show()
                }
            })
        }
        binding.startPreparing.setOnClickListener {
            val call3: Call<Void> = apiInterface.orderupdate("started Preparing",oid)
            call3.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        binding.startPreparing.visibility = View.GONE
                        binding.outForDelivery.visibility = View.VISIBLE
                        recreate()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fail to add Address", Toast.LENGTH_LONG).show()
                }
            })
        }
        binding.outForDelivery.setOnClickListener {
            val call4: Call<Void> = apiInterface.orderupdate("Out For Delivery",oid)
            call4.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        binding.outForDelivery.visibility = View.GONE
                        binding.delivered.visibility = View.VISIBLE
                        recreate()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fail to add Address", Toast.LENGTH_LONG).show()
                }
            })
        }
        binding.delivered.setOnClickListener {
            val call4: Call<Void> = apiInterface.orderupdate("Delivered",oid)
            call4.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        binding.delivered.visibility = View.GONE
                        recreate()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fail to add Address", Toast.LENGTH_LONG).show()
                }
            })
        }

    }
}