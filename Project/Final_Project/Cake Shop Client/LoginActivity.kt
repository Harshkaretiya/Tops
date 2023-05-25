package com.example.e_commerce2client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2client.Extra.ModelUser
import com.example.e_commerce2client.Extra.ModelUserAddress
import com.example.e_commerce2client.databinding.ActivityLoginBinding
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

    var email : String = ""
    var pass : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
//        auth.signOut()

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

//        if (auth.currentUser != null)
//            startActivity(Intent(this, MainActivity::class.java))
//        else
//            startActivity(Intent(this, LoginActivity::class.java))

        binding.registerButton.setOnClickListener {
            m.customProgressBar(this,true)
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            m.customProgressBar(this,true)
            var errorOccurred = verifyInputs()
            checkGoogleLogin(errorOccurred)
        }

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            // dont worry about this error
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this,options)
//        client.signOut()


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
                        m.customProgressBar(this,false)
                        if (it.isSuccessful) {
                            loginWithApi()
                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            catch (e : Exception){
                Toast.makeText(this, "${e.printStackTrace()}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkGoogleLogin(errorOccurred: Boolean) {
        val m = MainActivity()
        if (!errorOccurred) {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                m.customProgressBar(this,false)
                if (it.isSuccessful) {
                    Toast.makeText(this, "done 1", Toast.LENGTH_SHORT).show()
                    loginWithApi()
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loginWithApi() {

        auth = Firebase.auth
        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)

        var call: Call<ModelUser> = apiInterface.getsellerid(auth.currentUser!!.uid)
        call.enqueue(object : Callback<ModelUser> {
            override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                Toast.makeText(this@LoginActivity, "${auth.currentUser!!.uid}", Toast.LENGTH_SHORT).show()
                if (response.isSuccessful) {

                    var call3: Call<ModelUser> = apiInterface.getshopdetailid(auth.currentUser!!.uid)
                    call3.enqueue(object : Callback<ModelUser> {
                        override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                            if (response.isSuccessful) {

                                var call4: Call<ModelUserAddress> = apiInterface.getshopaddressid(auth.currentUser!!.uid)
                                call4.enqueue(object : Callback<ModelUserAddress> {
                                    override fun onResponse(call: Call<ModelUserAddress>, response: Response<ModelUserAddress>) {
                                        if (response.isSuccessful) {

                                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                        }
                                    }

                                    override fun onFailure(call: Call<ModelUserAddress>, t: Throwable) {
                                        startActivity(Intent(this@LoginActivity, AddressRegistrationActivity::class.java))
                                    }
                                })


                            }
                        }

                        override fun onFailure(call: Call<ModelUser>, t: Throwable) {
                            startActivity(Intent(this@LoginActivity, CompanyRegistrationActivity::class.java))
                        }
                    })


                }
            }

            override fun onFailure(call: Call<ModelUser>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "done 2", Toast.LENGTH_SHORT).show()

                val call2: Call<Void> = apiInterface.insertseller(
                    auth.currentUser!!.uid,
                    auth.currentUser!!.displayName!!,
                    auth.currentUser!!.email!!,
                    auth.currentUser!!.photoUrl.toString(),
                    "")
                call2.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Toast.makeText(applicationContext, "registered", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@LoginActivity, CompanyRegistrationActivity::class.java))
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(applicationContext,"Fail",Toast.LENGTH_LONG).show()
                    }
                })
            }
        })
    }

    private fun verifyInputs(): Boolean {
        email = binding.email.text.toString()
        pass = binding.password.text.toString()
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
        return errorOccurred
    }

    override fun onBackPressed() {
            finishAffinity()
    }
}
