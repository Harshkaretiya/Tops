package com.example.e_commerce2client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2client.Extra.ModelUser
import com.example.e_commerce2client.databinding.ActivityCompanyRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyRegistrationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCompanyRegistrationBinding
    lateinit var auth: FirebaseAuth
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        var uid = auth.currentUser!!.uid

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        binding.backButtonUserAddressInsertActivity.setOnClickListener {
            onBackPressed()
        }
        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)

        var call: Call<ModelUser> = apiInterface.getshopdetailid(auth.currentUser!!.uid)
        call.enqueue(object : Callback<ModelUser> {
            override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                if (response.isSuccessful) {

                    startActivity(Intent(this@CompanyRegistrationActivity, AddressRegistrationActivity::class.java))
                }
            }

            override fun onFailure(call: Call<ModelUser>, t: Throwable) {

            }
        })

        binding.submitButton.setOnClickListener {

            var sname = binding.sname.text.toString()
            var semail = binding.semail.text.toString()
            var gstno = binding.gstNo.text.toString()


            val call2: Call<Void> = apiInterface.shopdetailinsert(uid,sname,semail,gstno)
            call2.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        startActivity(Intent(this@CompanyRegistrationActivity,AddressRegistrationActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fail to add Address", Toast.LENGTH_LONG).show()
                }
            })
        }



    }
}