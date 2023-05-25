package com.example.e_commerce2.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.Extra.ModelOrder
import com.example.e_commerce2.adapter.OrderRecyclerListAdapter
import com.example.e_commerce2.databinding.ActivityOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderActivity : AppCompatActivity() {

    lateinit var binding : ActivityOrderBinding
    lateinit var list : MutableList<ModelOrder>
    lateinit var apiInterface: ApiInterface
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        val manager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding!!.listItemOrderActivity.layoutManager=manager

        auth = Firebase.auth
        val uid = auth.currentUser!!.uid

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        val call: Call<List<ModelOrder>> = apiInterface.orderidview(uid)
        call.enqueue(object: Callback<List<ModelOrder>> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<List<ModelOrder>>, response: Response<List<ModelOrder>>) {
                if (this != null) {
                    list = response.body() as MutableList<ModelOrder>
//                    binding!!.progressBar.visibility = View.GONE
                    val adapter = OrderRecyclerListAdapter(this@OrderActivity, list)
                    binding!!.listItemOrderActivity.adapter = adapter

                }
            }

            override fun onFailure(call: Call<List<ModelOrder>>, t: Throwable) {
                Toast.makeText(this@OrderActivity, "No Internet", Toast.LENGTH_LONG).show()

            }
        })

        binding.backButtonOrderActivity.setOnClickListener {
            onBackPressed()
        }
    }
}