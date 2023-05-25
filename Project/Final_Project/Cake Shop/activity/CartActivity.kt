package com.example.e_commerce2.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.adapter.CartRecyclerListAdapter
import com.example.e_commerce2.Extra.Model
import com.example.e_commerce2.R
import com.example.e_commerce2.databinding.ActivityCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity(),CartRecyclerListAdapter.OnImageClickListener {

    private lateinit var binding : ActivityCartBinding
    lateinit var list : MutableList<Model>
    lateinit var apiInterface: ApiInterface
    lateinit var sharedPreferences: SharedPreferences
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()

        val manager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding!!.listItemCartActivity.layoutManager=manager

        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)
//        val uid = sharedPreferences.getInt("uid",101)
        auth = Firebase.auth
        val uid = auth.currentUser!!.uid
        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        var progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)
        progressDialog.show()


        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        val call: Call<List<Model>> = apiInterface.cartidview(uid)
        call.enqueue(object: Callback<List<Model>>
        {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {


                if (response.body() == null || response.body()!!.isEmpty())
                {
//                    Toast.makeText(this@CartActivity, "list is empty", Toast.LENGTH_SHORT).show()
                    binding.checkoutLayout.visibility = View.GONE
                    binding.emptyList.visibility = View.VISIBLE
                    progressDialog.dismiss()
                }
                if (this != null) {
                    list = response.body() as MutableList<Model>
//                    binding!!.progressBar.visibility = View.GONE
                    val adapter = CartRecyclerListAdapter(this@CartActivity, list)
                    adapter.onImageClickListener = this@CartActivity
                    binding!!.listItemCartActivity.adapter = adapter

                    subtotalprice()

                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                Toast.makeText(this@CartActivity,"No Internet", Toast.LENGTH_LONG).show()

                progressDialog.dismiss()
            }
        })

        binding.backButtonCartActivity.setOnClickListener {
            onBackPressed()
        }

        binding.checkoutButton.setOnClickListener {
            var total1 = 0
            for (i in 0..list.size-1)
            {
                total1 += list[i].totalprice
            }
            var ftotal = total1+50
            var i = Intent(this,CheckoutActivity::class.java)
            i.putExtra("subtotal",total1)
            i.putExtra("total",ftotal)
            startActivity(i)
        }

    }
    fun subtotalprice()
    {
        var total = 0
        for (i in 0..list.size-1)
        {
            total += list[i].totalprice
        }
        binding.subtotalPriceCartActivity.text = total.toString()

        binding.totalCostPriceCartActivity.text = (total+50).toString()
    }
    override fun onImageClick() {
        subtotalprice()
    }
    override fun onRestart() {
        super.onRestart()
        recreate()
    }

}