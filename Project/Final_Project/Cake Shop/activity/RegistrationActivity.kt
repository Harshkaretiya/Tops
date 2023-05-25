package com.example.e_commerce2.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.Extra.ModelUser
import com.example.e_commerce2.R
import com.example.e_commerce2.databinding.ActivityRegistrationBinding
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity(){

    private lateinit var binding : ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var client:GoogleSignInClient
    private val RC_SIGN_IN = 10001
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        auth = Firebase.auth

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)

        binding.loginButton.setOnClickListener {
            m.customProgressBar(this,true)
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registerButton.setOnClickListener {
            var fname = binding.fname.text.toString()
            var lname = binding.lname.text.toString()
            var email = binding.email.text.toString()
            var pass = binding.password.text.toString()
            var cpass = binding.confirmPassword.text.toString()

            var errorOccurred = false

            if (fname.isEmpty()){
                binding.fnameLayout.helperText = "Please enter your first name"
                errorOccurred = true
            }
            if (lname.isEmpty()){
                binding.lnameLayout.helperText = "Please enter your last name"
                errorOccurred = true
            }
            if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())){
                binding.emailLayout.helperText = "Please enter correct email"
                errorOccurred = true
            }
            if (pass.length<8){
                binding.passwordLayout.helperText = "Password is weak"
                errorOccurred = true
            }
            if (cpass != pass || cpass.length<8){
                binding.confirmPasswordLayout.helperText = "Password doesn't match"
                errorOccurred = true
            }
            if (!errorOccurred) {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    m.customProgressBar(this,false)
                    if (it.isSuccessful) {

                        val call: Call<Void> = apiInterface.insertuser(auth.currentUser!!.uid,"$fname $lname",email,"","")
                        call.enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                var profileUpdate = UserProfileChangeRequest.Builder()
                                    .setDisplayName("$fname $lname")
                                    .build()
                                auth.currentUser!!.updateProfile(profileUpdate)
                                auth.signOut()
                                Toast.makeText(applicationContext, "registered", Toast.LENGTH_LONG).show()
                            }
                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(applicationContext,"Fail",Toast.LENGTH_LONG).show()
                            }

                        })

//                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "already registered", Toast.LENGTH_SHORT).show()
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
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener{
                    m.customProgressBar(this, false)
                    if(it.isSuccessful){
                        var call: Call<ModelUser> = apiInterface.getiduser(auth.currentUser!!.uid)
                        call.enqueue(object : Callback<ModelUser> {
                            override fun onResponse(
                                call: Call<ModelUser>,
                                response: Response<ModelUser>
                            ) {
                                if (response.isSuccessful && auth.currentUser!!.uid == response.body()?.userid) {
//                                        Toast.makeText(this@LoginActivity, "avaialble", Toast.LENGTH_SHORT).show()
                                    val i = Intent(this@RegistrationActivity, MainActivity::class.java)
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
                                        val i = Intent(this@RegistrationActivity, MainActivity::class.java)
                                        startActivity(i)
                                    }
                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Toast.makeText(applicationContext,"Fail",Toast.LENGTH_LONG).show()
                                    }
                                })
                            }
                        })

                    }else{
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                    }

                }
        }
        else if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_CANCELED) {
            return
        }
    }

}