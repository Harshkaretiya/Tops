package com.example.e_commerce2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.Extra.ModelOrder
import com.example.e_commerce2.Extra.ModelUser
import com.example.e_commerce2.R
import com.example.e_commerce2.databinding.ActivityOrderViewBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityOrderViewBinding
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
                    }
                }

            }
            override fun onFailure(call: Call<ModelOrder>, t: Throwable) {
                Toast.makeText(applicationContext,"some error occurs", Toast.LENGTH_LONG).show()
            }
        })

    }
}