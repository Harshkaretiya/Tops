package com.example.registerandlogin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity2 : AppCompatActivity() {

    lateinit var emailid : EditText
    lateinit var password : EditText
    lateinit var login : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        emailid = findViewById(R.id.email)
        password = findViewById(R.id.pass)
        login = findViewById(R.id.login)

        login.setOnClickListener {
            var email = emailid.text.toString()
            var pass = password.text.toString()

            var stringRequest: StringRequest = object :StringRequest(Method.POST,"https://hk123234345.000webhostapp.com/registration1/login.php",Response.Listener {
                    response->
                if(response.trim{ it <= ' ' } == "0")
                {
                    Toast.makeText(applicationContext, "Login Fail", Toast.LENGTH_LONG).show()

                }
                else
                {
                    Toast.makeText(applicationContext, "Login Done", Toast.LENGTH_LONG).show()


                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "No Internet", Toast.LENGTH_LONG).show()
            })
            {
                override fun getParams(): MutableMap<String, String>? {
                    var map = HashMap<String,String>()
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