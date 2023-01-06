package com.example.nedittext

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    lateinit var edt1 : EditText
    lateinit var btn1 : Button
    lateinit var layout : LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt1 = findViewById(R.id.edt1)
        btn1 = findViewById(R.id.btn1)
        layout = findViewById(R.id.layout)

        btn1.setOnClickListener {
            addEdittext(edt1.text.toString().toInt())
        }

    }

    fun addEdittext(n:Int)
    {
        for (i in 1..n)
        {
            var et = EditText(this)
            layout.addView(et)

        }
    }
}