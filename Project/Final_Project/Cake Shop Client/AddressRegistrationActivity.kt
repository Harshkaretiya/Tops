package com.example.e_commerce2client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2client.Extra.ModelUser
import com.example.e_commerce2client.Extra.ModelUserAddress
import com.example.e_commerce2client.databinding.ActivityAddressRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressRegistrationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddressRegistrationBinding
    lateinit var auth: FirebaseAuth
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        var uid = auth.currentUser!!.uid

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        binding.backButtonUserAddressInsertActivity.setOnClickListener {
            onBackPressed()
        }
        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)

        var call: Call<ModelUserAddress> = apiInterface.getshopaddressid(auth.currentUser!!.uid)
        call.enqueue(object : Callback<ModelUserAddress> {
            override fun onResponse(call: Call<ModelUserAddress>, response: Response<ModelUserAddress>) {
                if (response.isSuccessful) {

                    startActivity(Intent(this@AddressRegistrationActivity, MainActivity::class.java))
                }
            }

            override fun onFailure(call: Call<ModelUserAddress>, t: Throwable) {

            }
        })

        binding.addNewAddressButtonUserAddressInsertActivity.setOnClickListener {

            var street = binding.streetUserAddressInsertActivity.text.toString()
            var city = binding.cityUserAddressInsertActivity.text.toString()
            var state = binding.stateUserAddressInsertActivity.text.toString()
            var zipcode = binding.zipcodeUserAddressInsertActivity.text.toString()
            var country = binding.countryUserAddressInsertActivity.text.toString()


            val call2: Call<Void> = apiInterface.shopaddressinsert(
                uid,street,city,state,zipcode,country
            )
            call2.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        startActivity(Intent(this@AddressRegistrationActivity,MainActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fail to add Address", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}