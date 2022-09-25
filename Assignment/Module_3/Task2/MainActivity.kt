package com.example.colourchange

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout

class MainActivity : AppCompatActivity() {

lateinit var red : Button
lateinit var blue : Button
lateinit var background : RelativeLayout

    @SuppressLint("ResourceType", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        red = findViewById(R.id.btn1)
        blue = findViewById(R.id.btn2)
        background = findViewById(R.id.rl1)

        red.setOnClickListener {
            background.setBackgroundColor(Color.parseColor("#FF0000"))
        }
        blue.setOnClickListener {
            background.setBackgroundColor(Color.parseColor("#0000FF"))
        }
    }
}
