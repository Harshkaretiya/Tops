package com.example.elearn

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegistrationActivity : AppCompatActivity() {

    lateinit var fname : EditText
    lateinit var lname : EditText
    lateinit var email: EditText
    lateinit var password : EditText
    lateinit var confirm_password : EditText
    lateinit var register : Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var alreadylogin : TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        fname = findViewById(R.id.fnameinput)
        lname = findViewById(R.id.lnameinput)
        email = findViewById(R.id.emailinput)
        password = findViewById(R.id.passwordinput)
        confirm_password = findViewById(R.id.confirmpasswordinput)
        register = findViewById(R.id.signup)
        alreadylogin = findViewById(R.id.alreadylogin)

        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)

        register.setOnClickListener {

            var firstname = fname.text.toString()
            var lastname = lname.text.toString()
            var emailid = email.text.toString()
            var pass = password.text.toString()
            var conf_pass = confirm_password.text.toString()


            var edit1: SharedPreferences.Editor = sharedPreferences.edit()

            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()

            edit1.putString("fname",firstname)
            edit1.putString("lname",lastname)
            edit1.putString("email", emailid)
            edit1.putString("pass", pass)
            edit1.putString("conf_pass", conf_pass)
            edit1.commit()

            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()


        }

        alreadylogin.setOnClickListener {
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }
}