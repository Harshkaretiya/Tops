package com.example.e_commerce2.activity

import  android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.Extra.ModelUser
import com.example.e_commerce2.R
import com.example.e_commerce2.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var client: GoogleSignInClient
    private val RC_SIGN_IN = 10001
    lateinit var apiInterface: ApiInterface
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        auth = Firebase.auth



        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)

        binding.registerButton.setOnClickListener {
            m.customProgressBar(this,true)
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            var email = binding.email.text.toString()
            var pass = binding.password.text.toString()
            binding.passwordLayout.helperText = null
            binding.emailLayout.helperText = null

            var errorOccurred = false

            if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
                binding.emailLayout.helperText = "Email Address is Incorrect"
                errorOccurred = true
            }

            if (pass.isEmpty() || pass.length < 8) {
                binding.passwordLayout.helperText = "Password is Incorrect"
                errorOccurred = true
            }

            if (!errorOccurred) {
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    m.customProgressBar(this, false)
                    if (it.isSuccessful) {

                        var call: Call<ModelUser> = apiInterface.getiduser(auth.currentUser!!.uid)
                        call.enqueue(object : Callback<ModelUser> {
                            override fun onResponse(
                                call: Call<ModelUser>,
                                response: Response<ModelUser>
                            ) {
//                                var edit1: SharedPreferences.Editor = sharedPreferences.edit()

//                                edit1.putString("userid", auth.currentUser!!.uid)
//                                edit1.putString("name", response.body()!!.username)
//                                edit1.putString("email", response.body()!!.email)
//                                edit1.putString("image", response.body()!!.image)
//                                edit1.putString("number", response.body()!!.number)
//                                edit1.apply()
                                if (response.isSuccessful && auth.currentUser!!.uid == response.body()?.userid) {
//                                    Toast.makeText(this@LoginActivity, "available", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                }
                                else
                                {

                                }
                            }

                            override fun onFailure(call: Call<ModelUser>, t: Throwable) {

                                val call: Call<Void> = apiInterface.insertuser(
                                    auth.currentUser!!.uid,
                                    auth.currentUser!!.displayName!!,
                                    auth.currentUser!!.email!!,
                                    auth.currentUser!!.photoUrl.toString(),
                                    "")
                                call.enqueue(object : Callback<Void> {
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        Toast.makeText(applicationContext, "registered", Toast.LENGTH_LONG).show()
                                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                    }
                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Toast.makeText(applicationContext,"Fail",Toast.LENGTH_LONG).show()
                                    }
                                })
                            }
                        })

                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val  options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            // dont worry about this error
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this,options)

        binding.signinButton.setOnClickListener {
            m.customProgressBar(this,true)
            val intent = client.signInIntent
            startActivityForResult(intent,RC_SIGN_IN)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val m = MainActivity()
        if(requestCode==RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener {
                        m.customProgressBar(this, false)
                        if (it.isSuccessful) {
                            var call: Call<ModelUser> = apiInterface.getiduser(auth.currentUser!!.uid)
                            call.enqueue(object : Callback<ModelUser> {
                                override fun onResponse(
                                    call: Call<ModelUser>,
                                    response: Response<ModelUser>
                                ) {
                                    if (response.isSuccessful && auth.currentUser!!.uid == response.body()?.userid) {
//                                        Toast.makeText(this@LoginActivity, "avaialble", Toast.LENGTH_SHORT).show()
                                        val i = Intent(this@LoginActivity, MainActivity::class.java)
                                        startActivity(i)
                                    }
                                }

                                override fun onFailure(call: Call<ModelUser>, t: Throwable) {
                                    val call2: Call<Void> = apiInterface.insertuser(
                                        auth.currentUser!!.uid,
                                        auth.currentUser!!.displayName!!,
                                        auth.currentUser!!.email!!,
                                        auth.currentUser!!.photoUrl.toString(),
                                        ""
                                    )
                                    call2.enqueue(object : Callback<Void> {
                                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                            Toast.makeText(applicationContext, "registered", Toast.LENGTH_LONG).show()
                                            val i = Intent(this@LoginActivity, MainActivity::class.java)
                                            startActivity(i)
                                        }
                                        override fun onFailure(call: Call<Void>, t: Throwable) {
                                            Toast.makeText(applicationContext,"Fail",Toast.LENGTH_LONG).show()
                                        }
                                    })
                                }
                            })

                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }

                    }
            }
            catch (e : Exception){

            }
        }
    }

    override fun onBackPressed() {

        finishAffinity()
    }
}