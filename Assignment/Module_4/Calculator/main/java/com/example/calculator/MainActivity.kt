package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var edt1 : EditText
    lateinit var edt2 : EditText
    lateinit var btn1 : Button
    lateinit var txt1 : TextView
    lateinit var add : RadioButton
    lateinit var sub : RadioButton
    lateinit var mul : RadioButton
    lateinit var div : RadioButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt1 = findViewById(R.id.edt1)
        edt2 = findViewById(R.id.edt2)

        btn1 = findViewById(R.id.btn1)

        txt1 = findViewById(R.id.txt1)

        add = findViewById(R.id.add)
        sub = findViewById(R.id.sub)
        mul = findViewById(R.id.mul)
        div = findViewById(R.id.div)

        add.setOnCheckedChangeListener { compoundButton, b ->
            if (add.isChecked)
            {
                btn1.setOnClickListener {
                    txt1.setText("${edt1.text.toString().toInt()+edt2.text.toString().toInt()}")
                }
            }
        }
        sub.setOnCheckedChangeListener { compoundButton, b ->
            if (sub.isChecked)
            {
                btn1.setOnClickListener {
                    txt1.setText("${edt1.text.toString().toInt()-edt2.text.toString().toInt()}")
                }
            }
        }
        mul.setOnCheckedChangeListener { compoundButton, b ->
            if (mul.isChecked)
            {
                btn1.setOnClickListener {
                    txt1.setText("${edt1.text.toString().toInt()*edt2.text.toString().toInt()}")
                }
            }
        }
        div.setOnCheckedChangeListener { compoundButton, b ->
            if (div.isChecked)
            {
                btn1.setOnClickListener {
                    txt1.setText("${edt1.text.toString().toDouble()/edt2.text.toString().toDouble()}")
                }
            }
        }

    }
}