package com.example.passdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    lateinit var value : EditText
    lateinit var btn1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        value = findViewById(R.id.value1)
        btn1 = findViewById(R.id.btn1)

        btn1.setOnClickListener {
            var text = value.text.toString()
            var i = Intent(this,MainActivity2::class.java)
            i.putExtra("Text",text.toString())
            startActivity(i)
        }


    }
}