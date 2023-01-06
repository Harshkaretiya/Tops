package com.example.colorsbutton

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton

class MainActivity : AppCompatActivity() {

    lateinit var red : RadioButton
    lateinit var blue : RadioButton
    lateinit var green : RadioButton
    lateinit var yellow : RadioButton
    lateinit var ll : LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        red = findViewById(R.id.red)
        blue = findViewById(R.id.blue)
        green = findViewById(R.id.green)
        yellow = findViewById(R.id.yellow)

        ll = findViewById(R.id.ll)

        red.setOnCheckedChangeListener { compoundButton, b ->
            if (red.isChecked)
            {
                ll.setBackgroundColor(Color.parseColor("#FF0000"))
            }
        }
        blue.setOnCheckedChangeListener { compoundButton, b ->
            if (blue.isChecked)
            {
                ll.setBackgroundColor(Color.parseColor("#1100FF"))
            }
        }
        green.setOnCheckedChangeListener { compoundButton, b ->
            if (green.isChecked)
            {
                ll.setBackgroundColor(Color.parseColor("#08FF00"))
            }
        }
        yellow.setOnCheckedChangeListener { compoundButton, b ->
            if (yellow.isChecked)
            {
                ll.setBackgroundColor(Color.parseColor("#FFE600"))
            }
        }



    }
}