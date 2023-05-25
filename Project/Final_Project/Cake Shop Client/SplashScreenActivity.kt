package com.example.e_commerce2client

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.core.os.postDelayed
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.e_commerce2client.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivitySplashScreenBinding.inflate(layoutInflater)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        installSplashScreen()
        setContentView(binding.root)



        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        auth = Firebase.auth

//        auth.currentUser!!.reload()
//        Handler().postDelayed(1000){
        if (auth.currentUser != null)
        {

            startActivity(Intent(this, MainActivity::class.java))
        }
        else
            startActivity(Intent(this, LoginActivity::class.java))
//        }
    }
}