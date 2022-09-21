    package com.example.asiacup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

    class MainActivity : AppCompatActivity() {

    lateinit var bt1 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt1 = findViewById(R.id.button1)
        bt1.setOnClickListener {
            var i2 = Intent(this,MainActivity2::class.java)
            startActivity(i2)
        }
    }
}