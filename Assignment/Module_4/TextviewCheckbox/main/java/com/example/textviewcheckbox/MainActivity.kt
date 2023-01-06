package com.example.textviewcheckbox

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var chk1 : CheckBox
    lateinit var txt1 : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chk1 = findViewById(R.id.chk1)
        txt1 = findViewById(R.id.txt1)
        txt1.visibility = View.GONE

        chk1.setOnCheckedChangeListener { compoundButton, b ->
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