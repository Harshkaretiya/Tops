package com.example.asiacup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity4 : AppCompatActivity() {

    lateinit var b1:Button
    lateinit var e1:EditText
    lateinit var e2:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        e1=findViewById(R.id.et1)
        e2=findViewById(R.id.et2)
        b1=findViewById(R.id.b1)

        b1.setOnClickListener {
            var num = e1.text.toString()
            var pass = e2.text.toString()

            if (num.length!=10 && pass.length<6)
            {
                e1.setError("Enter valid Mobile Number")
                e2.setError("Enter valid password")
            }

            else if (num.length!=10)
            {
                e1.setError("Enter valid Mobile Number")
            }

            else if (pass.length<6)
            {
                e2.setError("Enter valid password")
            }
            else
            {
                if (num.equals("7861923988") && pass.equals("123456"))
                {
                    Toast.makeText(this,"Login Success", Toast.LENGTH_SHORT).show()
                    var i = Intent(this,MainActivity::class.java)
                    startActivity(i)
                }
                else
                {
                    Toast.makeText(this,"Login Fail",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}