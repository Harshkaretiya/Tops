package com.example.e_commerce2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_commerce2.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        Picasso.get().load(auth.currentUser!!.photoUrl).into(binding.imageProfileActivity)
        binding.nameProfileActivity.text = auth.currentUser!!.displayName

        binding.backButtonProfileActivity.setOnClickListener {
            onBackPressed()
        }

        binding.editImage.setOnClickListener {
            startActivity(Intent(this,ProfileEditActivity::class.java))
        }

        binding.addressLayout.setOnClickListener {
            startActivity(Intent(this,UserAddressActivity::class.java))
        }
        binding.orderHistoryLayout.setOnClickListener {
            startActivity(Intent(this,OrderActivity::class.java))
        }
    }

    override fun onRestart() {
        super.onRestart()
        binding.nameProfileActivity.text = auth.currentUser!!.displayName
        Picasso.get().load(auth.currentUser!!.photoUrl).into(binding.imageProfileActivity)
    }
}