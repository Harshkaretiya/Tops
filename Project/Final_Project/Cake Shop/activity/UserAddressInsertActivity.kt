package com.example.e_commerce2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.databinding.ActivityUserAddressInsertBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAddressInsertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserAddressInsertBinding
    lateinit var auth: FirebaseAuth
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAddressInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        var uid = auth.currentUser!!.uid

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        binding.backButtonUserAddressInsertActivity.setOnClickListener {
            onBackPressed()
        }

        binding.addNewAddressButtonUserAddressInsertActivity.setOnClickListener {

            var street = binding.streetUserAddressInsertActivity.text.toString()
            var city = binding.cityUserAddressInsertActivity.text.toString()
            var state = binding.stateUserAddressInsertActivity.text.toString()
            var zipcode = binding.zipcodeUserAddressInsertActivity.text.toString()
            var country = binding.countryUserAddressInsertActivity.text.toString()


            apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
            val call2: Call<Void> = apiInterface.useraddressinsert(
                uid,street,city,state,zipcode,country
            )
            call2.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                            Toast.makeText(applicationContext, "Address added", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fail to add Address", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}