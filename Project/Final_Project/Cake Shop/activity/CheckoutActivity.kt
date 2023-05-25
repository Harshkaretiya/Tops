package com.example.e_commerce2.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.Extra.Model
import com.example.e_commerce2.Extra.ModelUser
import com.example.e_commerce2.Extra.ModelUserAddress
import com.example.e_commerce2.databinding.ActivityCheckoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var apiInterface: ApiInterface
    lateinit var auth: FirebaseAuth
    lateinit var list3: MutableList<Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        var uid = auth.currentUser?.uid

        list3 = ArrayList()

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        var aid = 0

        var i = intent

        var subtotal = i.getIntExtra("subtotal",101)
        var total = i.getIntExtra("total",101)

        binding.subtotalPrice.text = subtotal.toString()
        binding.totalCostPrice.text = total.toString()


        binding.emailCheckout.text = auth.currentUser!!.email

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        val call: Call<ModelUser> = apiInterface.getiduser(uid!!)
        call.enqueue(object : Callback<ModelUser> {
            override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                if (this!=null) {
                    if (response.isSuccessful) {
                        var number = response.body()!!.number
                        if (number.isNotEmpty())
                            binding.phoneCheckout.setText(number)
                    }
                }

            }
            override fun onFailure(call: Call<ModelUser>, t: Throwable) {
                Toast.makeText(applicationContext,"some error occurs", Toast.LENGTH_LONG).show()
            }
        })

        var call2: Call<List<ModelUserAddress>> = apiInterface.primaryaddressview(uid!!)
        call2.enqueue(object: Callback<List<ModelUserAddress>>
        {
            override fun onResponse(call: Call<List<ModelUserAddress>>, response: Response<List<ModelUserAddress>>) {
                if (this != null) {

                    var list2 = response.body() as MutableList<ModelUserAddress>

                    if (list2.isEmpty())
                    {
                        binding.paymentButton.visibility = View.GONE
                    }
                    else {
                        binding!!.locationArea.text = list2[0].street
                        binding!!.locationCity.text = list2[0].city
                        aid = list2[0].aid
                    }
                }
            }

            override fun onFailure(call: Call<List<ModelUserAddress>>, t: Throwable) {
                Toast.makeText(this@CheckoutActivity,"No Internet", Toast.LENGTH_LONG).show()
            }
        })

        val call3: Call<List<Model>> = apiInterface.cartidview(uid)
        call3.enqueue(object: Callback<List<Model>>
        {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {

                if (this != null) {
                    list3 = response.body() as MutableList<Model>
                }
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                Toast.makeText(this@CheckoutActivity,"No Internet", Toast.LENGTH_LONG).show()

            }
        })

        binding.paymentButton.setOnClickListener {
            for (i in 1..list3.size) {

                var pid = list3[i-1].pid
                var qty = list3[i-1].qty
                var sid = list3[i-1].sid
                var call4: Call<Void> = apiInterface.orderinsert(uid,pid,qty,aid,sid)
                call4.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        var call8: Call<Void> = apiInterface.cartdelete(pid,uid)
                        call8.enqueue(object:Callback<Void?>{
                            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                                Toast.makeText(this@CheckoutActivity, "order placed $i", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@CheckoutActivity,OrderActivity::class.java))

                            }
                            override fun onFailure(call: Call<Void?>, t: Throwable) {
                                Toast.makeText(this@CheckoutActivity,"Error",Toast.LENGTH_LONG).show()
                            }
                        })
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@CheckoutActivity, "Fail order $i", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        binding.backButtonCheckoutActivity.setOnClickListener {
            onBackPressed()
        }

        binding.changeAddress.setOnClickListener {
            startActivity(Intent(this,UserAddressActivity::class.java))
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }
}