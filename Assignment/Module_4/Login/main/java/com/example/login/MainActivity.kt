package com.example.login

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var login : TextView
    lateinit var register : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login = findViewById(R.id.login)
        register = findViewById(R.id.register)

        replacefragment(LoginFragment())
        login.setBackgroundColor(Color.parseColor("#E6E6E6"))

        login.setOnClickListener {
            replacefragment(LoginFragment())
            register.setBackgroundColor(Color.parseColor("#FFFFFF"))
            login.setBackgroundColor(Color.parseColor("#E6E6E6"))
        }

        register.setOnClickListener {
            replacefragment(RegisterFragment())
            login.setBackgroundColor(Color.parseColor("#FFFFFF"))
            register.setBackgroundColor(Color.parseColor("#E6E6E6"))
        }

    }

    private fun replacefragment(fragment: Fragment) {

        var fragmentManager = supportFragmentManager
        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment,fragment)
        fragmentTransaction.commit()
    }
}