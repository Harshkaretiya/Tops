package com.example.fontsize

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var txt : TextView
    lateinit var inc : Button
    lateinit var dec : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt = findViewById(R.id.txt)
        inc = findViewById(R.id.inc)
        dec = findViewById(R.id.dec)

        inc.setOnClickListener {
            txt.setTextSize(0,txt.textSize+3f)
        }
        dec.setOnClickListener {
            txt.setTextSize(0,txt.textSize-3f)
        }

    }
}