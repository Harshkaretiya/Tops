package com.example.e_commerce2client

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2client.Adapters.OrderRecyclerListAdapter
import com.example.e_commerce2client.Extra.ModelOrder
import com.example.e_commerce2client.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var list : MutableList<ModelOrder>
    lateinit var apiInterface: ApiInterface
    private lateinit var client: GoogleSignInClient
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        val manager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding!!.listItemOrderActivity.layoutManager=manager

        auth = Firebase.auth
        val uid = auth.currentUser!!.uid

        binding.manageProducts.setOnClickListener {
            startActivity(Intent(this,ManageProductActivity::class.java))
        }

        binding.settingIcon.setOnClickListener {
            auth.signOut()
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                // dont worry about this error
                .requestEmail()
                .build()
            client = GoogleSignIn.getClient(this,options)
            client.signOut()
        }

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        val call: Call<List<ModelOrder>> = apiInterface.orderidseller(uid)
        call.enqueue(object: Callback<List<ModelOrder>> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<List<ModelOrder>>, response: Response<List<ModelOrder>>) {
                if (this != null) {
                    list = response.body() as MutableList<ModelOrder>
//                    binding!!.progressBar.visibility = View.GONE
                    val adapter = OrderRecyclerListAdapter(this@MainActivity, list)
                    binding!!.listItemOrderActivity.adapter = adapter

                }
            }

            override fun onFailure(call: Call<List<ModelOrder>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "No Internet", Toast.LENGTH_LONG).show()

            }
        })

    }
    fun setStatusBarColor(activity: Activity, color: String, DarkText: Boolean) {
        val window = activity.window
        window.statusBarColor = Color.parseColor(color)
        if (DarkText) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
    fun customProgressBar(context: Context, show: Boolean) {
        val progressDialog = Dialog(context)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        if (show) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}