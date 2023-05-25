package com.example.e_commerce2client

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2client.Extra.Model
import com.example.e_commerce2client.databinding.ActivityEditProductBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.squareup.picasso.Picasso
import net.gotev.uploadservice.MultipartUploadRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProductActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditProductBinding
    private lateinit var auth: FirebaseAuth
    private var filepath1: Uri? = null
    private var filepath2: Uri? = null
    private var filepath3: Uri? = null
    private var filepath4: Uri? = null
    private var filepath5: Uri? = null
    private lateinit var  bitmap: Bitmap
    lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)
        requestpermission()

        val i = intent
        val pid = i.getIntExtra("pid",0)

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)

        val call: Call<Model> = apiInterface.getiddata(pid)
        call.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {

                if (response.isSuccessful && pid == response.body()?.pid)
                {
                    val name = response.body()!!.name.toString()
                    val desc = response.body()!!.desc.toString()
                    val price = response.body()!!.price.toString()
                    val weight = response.body()!!.weight.toString()

                    binding.productName.setText(name)
                    binding.productDesc.setText(desc)
                    binding.productPrice.setText(price)
                    binding.productWeight.setText(weight)

                    //-------------------------------------------------------------------------------------
                    //check if added to cart or not
                    val img1 = response.body()!!.image
                    val img2 = response.body()!!.image2
                    val img3 = response.body()!!.image3
                    val img4 = response.body()!!.image4
                    val img5 = response.body()!!.image5

                    if (img1.isNotEmpty())
                        Picasso.get().load(img1).placeholder(R.mipmap.ic_launcher).into(binding.image1)
                    if (img2.isNotEmpty())
                        Picasso.get().load(img2).placeholder(R.mipmap.ic_launcher).into(binding.image2)
                    if (img3.isNotEmpty())
                        Picasso.get().load(img3).placeholder(R.mipmap.ic_launcher).into(binding.image3)
                    if (img4.isNotEmpty())
                        Picasso.get().load(img4).placeholder(R.mipmap.ic_launcher).into(binding.image4)
                    if (img5.isNotEmpty())
                        Picasso.get().load(img5).placeholder(R.mipmap.ic_launcher).into(binding.image5)

                }
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                Toast.makeText(applicationContext,"Something went wrong", Toast.LENGTH_LONG).show()
            }

        })

        binding.image1Layout.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1) //calling onActivityResult function
        }
        binding.image2Layout.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2) //calling onActivityResult function
        }
        binding.image3Layout.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 3) //calling onActivityResult function
        }
        binding.image4Layout.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 4) //calling onActivityResult function
        }
        binding.image5Layout.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5) //calling onActivityResult function
        }

        binding.saveChangesButton.setOnClickListener {
            //getting the text from edittext
            val pname = binding.productName.text.toString()
            val pdesc = binding.productDesc.text.toString()
            val pprice = binding.productPrice.text.toString()
            val pweight = binding.productWeight.text.toString()

            //check if the image want to be updated or not
            //if yes the this code will run
            if(filepath1 !=null){
                val path1 = getPath(filepath1)
                val multipartUploadRequest = MultipartUploadRequest(this, "https://hk123234345.000webhostapp.com/ProjectTest/productupdate.php")
                    .addParameter("sid", auth.currentUser!!.uid)
                    .addParameter("pid",pid.toString())
                    .addParameter("pname", pname)
                    .addParameter("pdesc", pdesc)
                    .addParameter("pprice",pprice)
                    .addParameter("pweight",pweight)
                    .setMaxRetries(2)
                Toast.makeText(this, path1, Toast.LENGTH_SHORT).show()
                multipartUploadRequest.addFileToUpload(path1, "img1")
                if (filepath2 != null) {
                    val path2 = getPath(filepath2)
                    multipartUploadRequest.addFileToUpload(path2,"img2")
                }
                if (filepath3 != null) {
                    val path3 = getPath(filepath3)
                    multipartUploadRequest.addFileToUpload(path3,"img3")
                }
                if (filepath4 != null) {
                    val path4 = getPath(filepath4)
                    multipartUploadRequest.addFileToUpload(path4,"img4")
                }
                if (filepath5 != null) {
                    val path5 = getPath(filepath5)
                    multipartUploadRequest.addFileToUpload(path5,"img5")
                }

                multipartUploadRequest.startUpload()
            } else {
                val multipartUploadRequest = MultipartUploadRequest(this, "https://hk123234345.000webhostapp.com/ProjectTest/productupdate.php")
                    .addParameter("sid", auth.currentUser!!.uid)
                    .addParameter("pid",pid.toString())
                    .addParameter("pname", pname)
                    .addParameter("pdesc", pdesc)
                    .addParameter("pprice",pprice)
                    .addParameter("pweight",pweight)
                    .setMaxRetries(2)
                multipartUploadRequest.startUpload()
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestpermission() {
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==1 && resultCode == RESULT_OK && data != null) {
            filepath1 = data.data!!
            bitmap= MediaStore.Images.Media.getBitmap(contentResolver, filepath1)
            binding.image1.setImageBitmap(bitmap)
        }
        if(requestCode==2 && resultCode == RESULT_OK && data != null) {
            filepath2 = data.data!!
            bitmap= MediaStore.Images.Media.getBitmap(contentResolver, filepath2)
            binding.image2.setImageBitmap(bitmap)
        }
        if(requestCode==3 && resultCode == RESULT_OK && data != null) {
            filepath3 = data.data!!
            bitmap= MediaStore.Images.Media.getBitmap(contentResolver, filepath3)
            binding.image3.setImageBitmap(bitmap)
        }
        if(requestCode==4 && resultCode == RESULT_OK && data != null) {
            filepath4 = data.data!!
            bitmap= MediaStore.Images.Media.getBitmap(contentResolver, filepath4)
            binding.image4.setImageBitmap(bitmap)
        }
        if(requestCode==5 && resultCode == RESULT_OK && data != null) {
            filepath5 = data.data!!
            bitmap= MediaStore.Images.Media.getBitmap(contentResolver, filepath5)
            binding.image5.setImageBitmap(bitmap)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("Range")
    fun getPath(uri: Uri?): String {
        var cursor = contentResolver.query(uri!!, null, null, null, null)
        cursor!!.moveToFirst()
        var document_id = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor.close()
        cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null)
        cursor!!.moveToFirst()
        val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()
        return path
    }
}