package com.example.inputtwonumber

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {

    lateinit var txt1 : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        txt1 = findViewById(R.id.txt1)

        var i = intent


        var array = ArrayList<Int>()

        var start = i.getStringExtra("start")
        var end = i.getStringExtra("end")

        var a = start.toString().toInt()
        var b = end.toString().toInt()


        for (j in 0..b-a)
        {
            array.add(a)
            a++
        }
        Toast.makeText(this, "${array[0]}", Toast.LENGTH_SHORT).show()
        txt1.setText(array.toString())
    }
}