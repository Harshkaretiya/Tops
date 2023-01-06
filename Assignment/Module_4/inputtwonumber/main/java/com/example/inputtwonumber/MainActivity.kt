package com.example.inputtwonumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var edt1 : EditText
    lateinit var edt2 : EditText
    lateinit var btn1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1 = findViewById(R.id.btn1)


        btn1.setOnClickListener {
            edt1 = findViewById(R.id.edt1)
            edt2 = findViewById(R.id.edt2)

//            Toast.makeText(this, "${edt1.text}", Toast.LENGTH_SHORT).show()

            var i = Intent(this,MainActivity2::class.java)
            i.putExtra("start",edt1.text.toString())
            i.putExtra("end",edt2.text.toString())
            startActivity(i)

        }
    }
}