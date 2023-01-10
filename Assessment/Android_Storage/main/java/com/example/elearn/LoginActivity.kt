package com.example.elearn

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var login : Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var registernow : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        registernow = findViewById(R.id.registernow)


        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("User_Session", false) )
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        login.setOnClickListener {

            var emailid = email.text.toString()
            var pass = password.text.toString()


            if (emailid==sharedPreferences.getString("email","") &&  pass==sharedPreferences.getString("pass","") ) {
                var edit1: SharedPreferences.Editor = sharedPreferences.edit()
                edit1.putBoolean("User_Session", true)
                edit1.commit()
                var i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
            else
            {
                Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
            }
        }

        registernow.setOnClickListener {
            var i = Intent(this, RegistrationActivity::class.java)
            startActivity(i)
        }

    }

}