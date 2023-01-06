package com.example.seekbar

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {

    lateinit var red : SeekBar
    lateinit var green : SeekBar
    lateinit var blue : SeekBar
    lateinit var ll : LinearLayout
    lateinit var btn1 : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        red = findViewById(R.id.R)
        green = findViewById(R.id.G)
        blue = findViewById(R.id.B)
        ll = findViewById(R.id.ll)
        btn1 = findViewById(R.id.btn1)

        btn1.setOnClickListener {
          ll.setBackgroundColor(Color.rgb(red.progress,green.progress,blue.progress))
        }

    }
}