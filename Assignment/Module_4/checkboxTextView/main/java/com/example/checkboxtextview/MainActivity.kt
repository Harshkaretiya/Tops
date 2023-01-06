package com.example.checkboxtextview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var txt1 : TextView
    lateinit var chk1 : CheckBox

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt1 = findViewById(R.id.txt1)
        chk1 = findViewById(R.id.chk1)

        txt1.visibility = View.GONE

        chk1.setOnCheckedChangeListener { button, b ->
            if (chk1.isChecked)
            {
                txt1.visibility = View.VISIBLE
            }
            else
            {
                txt1.visibility = View.GONE
            }
        }
    }
}