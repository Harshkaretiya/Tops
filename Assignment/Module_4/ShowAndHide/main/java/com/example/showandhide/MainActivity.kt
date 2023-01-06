package com.example.showandhide

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var txt : TextView
    lateinit var show : Button
    lateinit var hide : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt = findViewById(R.id.txt)
        show = findViewById(R.id.show)
        hide = findViewById(R.id.hide)

        show.setOnClickListener {
            txt.visibility = View.VISIBLE
        }
        hide.setOnClickListener {
            txt.visibility = View.GONE
        }

    }
}