package com.example.e_commerce2client

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
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
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2client.databinding.ActivityAddNewProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import net.gotev.uploadservice.MultipartUploadRequest

class AddNewProductActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddNewProductBinding
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
        binding = ActivityAddNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        var m = MainActivity()
        m.setStatusBarColor(this,"#F8F9FA",true)
        requestpermission()

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

        binding.addProductButton.setOnClickListener {
            //getting the text from edittext
            val pname = binding.productName.text.toString()
            val pdesc = binding.productDesc.text.toString()
            val pprice = binding.productPrice.text.toString()
            val pweight = binding.productWeight.text.toString()

            //check if the image want to be updated or not
            //if yes the this code will run
            if(filepath1 !=null){
                val path1 = getPath(filepath1)
                val multipartUploadRequest = MultipartUploadRequest(this, "https://hk123234345.000webhostapp.com/ProjectTest/productinsert.php")
                    .addParameter("sid", auth.currentUser!!.uid)
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
//
                multipartUploadRequest.startUpload()
            } else {
                Toast.makeText(this, "please select at least 1 image", Toast.LENGTH_SHORT).show()
            }
        }

    }
//    private fun requestpermission() {
//        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),100)
//        }
//    }

    private fun requestpermission() {
        val permission = Manifest.permission.READ_MEDIA_IMAGES
        val requestCode = 225

        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission),
            requestCode
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 225) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==1 && resultCode == Activity.RESULT_OK && data != null) {
            filepath1 = data.data!!
            bitmap= MediaStore.Images.Media.getBitmap(contentResolver, filepath1)
            binding.image1.setImageBitmap(bitmap)
        }
        if(requestCode==2 && resultCode == Activity.RESULT_OK && data != null) {
            filepath2 = data.data!!
            bitmap= MediaStore.Images.Media.getBitmap(contentResolver, filepath2)
            binding.image2.setImageBitmap(bitmap)
        }
        if(requestCode==3 && resultCode == Activity.RESULT_OK && data != null) {
            filepath3 = data.data!!
            bitmap= MediaStore.Images.Media.getBitmap(contentResolver, filepath3)
            binding.image3.setImageBitmap(bitmap)
        }
        if(requestCode==4 && resultCode == Activity.RESULT_OK && data != null) {
            filepath4 = data.data!!
            bitmap= MediaStore.Images.Media.getBitmap(contentResolver, filepath4)
            binding.image4.setImageBitmap(bitmap)
        }
        if(requestCode==5 && resultCode == Activity.RESULT_OK && data != null) {
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



//    @SuppressLint("Range")
//    fun getPath(uri: Uri?): String {
//        var cursor = contentResolver.query(uri!!, null, null, null, null)
//        if (cursor != null && cursor.moveToFirst()) {
//            var document_id = cursor.getString(0)
//            document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
//            cursor.close()
//            cursor = contentResolver.query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null)
//            if (cursor != null && cursor.moveToFirst()) {
//                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
//                cursor.close()
//                return path
//            }
//        }
//        return ""
//    }


}
