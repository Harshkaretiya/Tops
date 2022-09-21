package com.example.asiacup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        supportActionBar?.hide()

        Handler().postDelayed(Runnable {
            var i = Intent(this,MainActivity4::class.java)
            startActivity(i)
        },3000)
    }
}