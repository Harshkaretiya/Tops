package com.example.e_commerce2.activity

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.Extra.ModelUser
import com.example.e_commerce2.R
import com.example.e_commerce2.databinding.ActivityProfileEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import net.gotev.uploadservice.MultipartUploadRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEditBinding
    lateinit var auth: FirebaseAuth
    private var filepath: Uri? = null
    private lateinit var  bitmap: Bitmap
    private lateinit var apiInterface: ApiInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        var uid = auth.currentUser?.uid

        binding.nameProfileEditActivity.setText(auth.currentUser!!.displayName)

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)

        Picasso.get().load(auth.currentUser!!.photoUrl).into(binding.imageProfileEditActivity)

        var progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        val call: Call<ModelUser> = apiInterface.getiduser(uid!!)
        call.enqueue(object : Callback<ModelUser> {
            override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                if (this!=null) {
                    if (response.isSuccessful) {
                        binding.phoneNumberProfileEditActivity.setText(response.body()!!.number)
                    }
                }

            }
            override fun onFailure(call: Call<ModelUser>, t: Throwable) {
                Toast.makeText(applicationContext,"some error occurs",Toast.LENGTH_LONG).show()
            }
        })


        binding.imageLayoutProfileEditActivity.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1) //calling onActivityResult function
        }
        requestpermission()

        binding.saveButtonProfileEditActivity.setOnClickListener {
            progressDialog.show()
            var name = binding.nameProfileEditActivity.text
            var number = binding.phoneNumberProfileEditActivity.text

            val multipartUploadRequest = MultipartUploadRequest(this, "https://hk123234345.000webhostapp.com/ProjectTest/upload1.php")
                .addParameter("uid", auth.currentUser!!.uid)
                .addParameter("name", name.toString())
                .addParameter("number", number.toString())
                .setMaxRetries(2)
            multipartUploadRequest.startUpload()


            if(filepath !=null){
                var profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(name.toString())
                    .build()
                auth.currentUser!!.updateProfile(profileUpdate)
                uploadImageToFirebaseStorage(filepath)
                progressDialog.dismiss()

            }
            else {

                var profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(name.toString())
                    .build()
                auth.currentUser!!.updateProfile(profileUpdate).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        progressDialog.dismiss()
                    }
                }

            }
        }

        binding.backButtonProfileActivity.setOnClickListener {
            onBackPressed()
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestpermission()
    {
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),100)
        }
    }
    //get path of image from local storage function
    private fun uploadImageToFirebaseStorage(imageUri: Uri?) {

        var progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)
        progressDialog.show()
        if (imageUri != null) {
            // Get the current user's ID
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            // Create a reference to the user's profile image in Firebase Storage
            val storageRef = FirebaseStorage.getInstance().getReference("profile_images/$userId")

            // Upload the image to Firebase Storage
            storageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    // Get the download URL of the image
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Set the user's profile image in Firebase Auth
                        val user = FirebaseAuth.getInstance().currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setPhotoUri(uri)
                            .build()
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task ->
                                progressDialog.dismiss()
                                if (task.isSuccessful) {

                                    // Profile image updated successfully
                                    Toast.makeText(this, "completed", Toast.LENGTH_SHORT).show()

                                } else {
                                    // Error updating profile image
                                }
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    // Error uploading image to Firebase Storage
                }
        }
    }

    //when image is selected from getting image code the image will be set to image view
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if(requestCode==1 && resultCode == RESULT_OK && data != null) {
            filepath = data.data!!
            bitmap= MediaStore.Images.Media.getBitmap(contentResolver, filepath)
            binding.imageProfileEditActivity.setImageBitmap(bitmap)


        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}