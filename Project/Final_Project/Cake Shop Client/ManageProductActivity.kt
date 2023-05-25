package com.example.e_commerce2client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.RecyclerListAdapter
import com.example.e_commerce2client.Extra.Model
import com.example.e_commerce2client.databinding.ActivityMainBinding
import com.example.e_commerce2client.databinding.ActivityManageProductBinding
import com.example.e_commerce2client.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smarteist.autoimageslider.SliderView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageProductActivity : AppCompatActivity() {

    private lateinit var binding : ActivityManageProductBinding
    lateinit var list : MutableList<Model>
    lateinit var apiInterface: ApiInterface
    lateinit var sliderView: SliderView
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var manager2 : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding!!.listItem.layoutManager=manager2

        auth = Firebase.auth
        var uid = auth.currentUser!!.uid

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)


        list = ArrayList()

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        var call: Call<List<Model>> = apiInterface.getsellerdata(uid)
        call.enqueue(object: Callback<List<Model>>
        {
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                if (this != null) {
                    list = response.body() as MutableList<Model>

                    if (list.isNotEmpty()) {
                        var adapter2 = RecyclerListAdapter(
                            this@ManageProductActivity,
                            list
                        )
                        binding!!.listItem.adapter = adapter2
                    }
                }

            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                Toast.makeText(this@ManageProductActivity, "No Internet", Toast.LENGTH_LONG).show()
            }
        })

        binding.addNewProduct.setOnClickListener {
            startActivity(Intent(this,AddNewProductActivity::class.java))
        }

    }
}