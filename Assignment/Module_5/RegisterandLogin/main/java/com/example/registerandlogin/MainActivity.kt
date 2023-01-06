package com.example.registerandlogin

import android.annotation.SuppressLint
import android.app.DownloadManager.Request
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    lateinit var firstname : EditText
    lateinit var lastname : EditText
    lateinit var emailid : EditText
    lateinit var password : EditText
    lateinit var btn1 : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstname = findViewById(R.id.fname)
        lastname = findViewById(R.id.lname)
        emailid = findViewById(R.id.email)
        password = findViewById(R.id.pass)
        btn1 = findViewById(R.id.btn1)

        btn1.setOnClickListener {
            var fname = firstname.text.toString()
            var lname = lastname.text.toString()
            var email = emailid.text.toString()
            var pass = password.text.toString()

            var stringRequest : StringRequest = object :StringRequest(Method.POST,"https://hk123234345.000webhostapp.com/registration1/registration.php",Response.Listener {
                Toast.makeText(applicationContext, "Registration done", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext,MainActivity2::class.java))

            },Response.ErrorListener {
                Toast.makeText(applicationContext, "No Internet", Toast.LENGTH_SHORT).show()
            }
            
            ){

                override fun getParams(): MutableMap<String, String>? {
                    var map = HashMap<String,String>()
                    map["firstname"] = fname
                    map["lastname"] = lname
                    map["email"] = email
                    map["password"] = pass

                    return map
                }

            }
            var queue : RequestQueue = Volley.newRequestQueue(this)
            queue.add(stringRequest)

        }

    }
}