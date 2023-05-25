package com.example.e_commerce2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.RecyclerGridAdapter
import com.example.e_commerce2.RecyclerListAdapter
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.Extra.Model
import com.example.e_commerce2.Extra.ModelUserAddress
import com.example.e_commerce2.adapter.UserAddressAdapter
import com.example.e_commerce2.databinding.ActivityUserAddressBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserAddressBinding
    lateinit var list : MutableList<ModelUserAddress>
    lateinit var apiInterface: ApiInterface
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        binding.addNewAddressButtonActivity.setOnClickListener {
            startActivity(Intent(this,UserAddressInsertActivity::class.java))
        }

        auth = Firebase.auth

        var uid = auth.currentUser!!.uid

        var manager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding!!.addressList.layoutManager=manager

        list = ArrayList()

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        var call: Call<List<ModelUserAddress>> = apiInterface.useraddressidview(uid)
        call.enqueue(object: Callback<List<ModelUserAddress>>
        {
            override fun onResponse(call: Call<List<ModelUserAddress>>, response: Response<List<ModelUserAddress>>) {
                if (this != null) {
                    list = response.body() as MutableList<ModelUserAddress>

                    var adapter = UserAddressAdapter(this@UserAddressActivity, list)
                    binding!!.addressList.adapter = adapter


                }
            }

            override fun onFailure(call: Call<List<ModelUserAddress>>, t: Throwable) {
                Toast.makeText(this@UserAddressActivity,"No Internet", Toast.LENGTH_LONG).show()
            }
        })

        binding.backButtonUserAddressActivity.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }
}