package com.example.e_commerce2.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.*
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.Extra.ImageSlider
import com.example.e_commerce2.Extra.Model
import com.example.e_commerce2.Extra.ModelUserAddress
import com.example.e_commerce2.databinding.ActivityProductViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductViewBinding
    lateinit var apiInterface : ApiInterface
    lateinit var sliderView: SliderView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //--------------------------------------------------------------------------------//
        //regular code

        var m = MainActivity()
        m.setStatusBarColor(this, "#FFFFFF",true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)
//        val uid = sharedPreferences.getInt("uid",101)
        auth = Firebase.auth
        val uid = auth.currentUser!!.uid
        var isFavourite = false

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)

        val i = intent
        val pid = i.getIntExtra("pid",9999999)

        var price = 0

        var sid = ""

        //---------------------------------------------------------------------------
        //regular back or intent code

        binding.backButtonProductView.setOnClickListener {
            onBackPressed()
        }
        binding.cartButtonProductView.setOnClickListener {
            var i = Intent(this,CartActivity::class.java)
            startActivity(i)
        }
        val progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)
        progressDialog.show()

        var qty = binding.quantityProductView.text.toString().toInt()

        //---------------------------------------------------------------------------
        //set data to product view page

        val call: Call<Model> = apiInterface.getiddata(pid)
        call.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {

                if (response.isSuccessful && pid == response.body()?.pid)
                {
                    price = response.body()!!.price.toInt()

                    binding.productNameProductView.text = response.body()!!.name
                    binding.productDescProductView.text = response.body()!!.desc
                    binding.weightProductView.text = response.body()!!.weight
                    binding.productPriceProductView.text = price.toString()
                    binding.productPriceBottomProductView.text = price.toString()

                    //-------------------------------------------------------------------------------------
                    //check if added to cart or not

                    progressDialog.show()

                    var call5: Call<Model> = apiInterface.cartcheck(pid,uid)
                    call5.enqueue(object : Callback<Model> {
                        override fun onResponse(call: Call<Model>, response: Response<Model>) {

                            qty = response.body()!!.qty.toString().toInt()
//                totalPrice = response.body()!!.totalprice.toString().toInt()


                            binding.quantityProductView.text = qty.toString()
                            binding.productPriceBottomProductView.text = (price*qty).toString()
                            binding.addToCart.visibility = View.GONE
                            binding.GoToCart.visibility = View.VISIBLE
                            binding.quantitySubtract.visibility = View.GONE
                            binding.quantityAdd.visibility = View.GONE
                            progressDialog.dismiss()

                        }
                        override fun onFailure(call: Call<Model>, t: Throwable) {
                            progressDialog.dismiss()
                            //update the number on display only not no database
                        }
                    })

                    var img1 = response.body()!!.image
                    var img2 = response.body()!!.image2
                    var img3 = response.body()!!.image3
                    var img4 = response.body()!!.image4
                    var img5 = response.body()!!.image5

                    sid = response.body()!!.sid

                    var images = ArrayList<String>()
                    if (img1 != "") images.add(img1)
                    if (img2 != "") images.add(img2)
                    if (img3 != "") images.add(img3)
                    if (img4 != "") images.add(img4)
                    if (img5 != "") images.add(img5)
                    sliderView = findViewById(R.id.product_view_slider)
                    val imageSlider = ImageSlider(images)
                    sliderView.setSliderAdapter(imageSlider)
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)

                    if (img2 == "" && img3 =="" && img4 =="" && img5=="")
                    {
                        sliderView.setInfiniteAdapterEnabled(false)
                    }


                }
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"Something went wrong", Toast.LENGTH_LONG).show()
            }

        })

        //---------------------------------------------------------------------------
        //check if product is favourite or not and if then set clickonevent


        var call2: Call<Model> = apiInterface.getcheckfav(pid,uid)
        call2.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                binding.favourite.setColorFilter(Color.parseColor("#ff0000"))
                isFavourite = true
//                progressDialog.dismiss()
            }
            override fun onFailure(call: Call<Model>, t: Throwable) {
//                progressDialog.dismiss()
            }
        })

        binding.favouriteLayout.setOnClickListener {
            progressDialog.show()
            if (isFavourite) {
                var call: Call<Void> = apiInterface.getfavdelete(pid,uid)
                call!!.enqueue(object: Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        Toast.makeText(this@ProductViewActivity, "Removed from favourite", Toast.LENGTH_SHORT).show()
                        isFavourite = false
                        binding.favourite.setColorFilter(Color.parseColor("#707B81"))
                        progressDialog.dismiss()
                    }
                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        progressDialog.dismiss()
                        Toast.makeText(this@ProductViewActivity,"Error", Toast.LENGTH_LONG).show()
                    }
                })
            }
            else if(!isFavourite) {
                val call: Call<Void> = apiInterface.insertfav(pid, uid)
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        binding.favourite.setColorFilter(Color.parseColor("#ff0000"))
                        isFavourite = true
                        progressDialog.dismiss()
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        progressDialog.dismiss()
                        Toast.makeText(this@ProductViewActivity, "Fail", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        //------------------------------------------------------------------------------------------
        //set grid view item

        var manager : RecyclerView.LayoutManager = GridLayoutManager(this,10)
        binding!!.gridItemProductView.layoutManager=manager

        var call3: Call<List<Model>> = apiInterface.getdata()
        call3.enqueue(object: Callback<List<Model>>
        {
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {

                if (this != null) {
                    var list = response.body() as MutableList<Model>

                    val adapter3 = RecyclerGridAdapter(this@ProductViewActivity, list)
                    binding.gridItemProductView.adapter = adapter3
                }
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                Toast.makeText(this@ProductViewActivity,"No Internet", Toast.LENGTH_LONG).show()
            }
        })

        //-------------------------------------------------------------------------------------
        //set address

        var call5: Call<List<ModelUserAddress>> = apiInterface.primaryaddressview(uid!!)
        call5.enqueue(object: Callback<List<ModelUserAddress>>
        {
            override fun onResponse(call: Call<List<ModelUserAddress>>, response: Response<List<ModelUserAddress>>) {
                if (this != null) {
                    var list2 = response.body() as MutableList<ModelUserAddress>
                    if (list2.isEmpty())
                    {

                    }
                    else {
                        binding!!.locationArea.text = list2[0].street
                        binding!!.locationCity.text = list2[0].city
                    }
                }
            }

            override fun onFailure(call: Call<List<ModelUserAddress>>, t: Throwable) {
                Toast.makeText(this@ProductViewActivity,"No Internet", Toast.LENGTH_LONG).show()
            }
        })




        //--------------------------------------------------------------------------------------
        //Increment or decrement of quantity




        binding.quantitySubtract.setOnClickListener {
            if (qty==1)
            {
                Toast.makeText(this, "min quantity reached", Toast.LENGTH_SHORT).show()
            }
            else if(qty>1)
            {
                qty -= 1
                binding.quantityProductView.setText(qty.toString())
                binding.productPriceBottomProductView.text = (price * qty).toString()
            }
        }
        binding.quantityAdd.setOnClickListener {
            if (qty==10)
            {
                Toast.makeText(this, "max quantity reached", Toast.LENGTH_SHORT).show()
            }
            else if(qty<10)
            {
                qty += 1
                binding.quantityProductView.setText(qty.toString())
                binding.productPriceBottomProductView.text = (price * qty).toString()
            }
        }

        //-------------------------------------------------------------------------------------
        //insert product to cart

        binding.addToCart.visibility = View.VISIBLE
        binding.GoToCart.visibility = View.GONE

        binding.addToCart.setOnClickListener {
            progressDialog.show()
            var call4 : Call<ResponseBody> = apiInterface.cartinsert(pid,uid,qty,sid)
            call4.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful) {
                        val result = response.body()?.string()
                        if(result == "1") {
                            Toast.makeText(applicationContext, "Added to cart", Toast.LENGTH_LONG).show()
                            binding.addToCart.visibility = View.GONE
                            binding.GoToCart.visibility = View.VISIBLE
                            binding.quantitySubtract.visibility = View.GONE
                            binding.quantityAdd.visibility = View.GONE
                        } else {
                            Toast.makeText(applicationContext, "Failed to add to cart 1", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Failed to add to cart", Toast.LENGTH_LONG).show()
                    }
                    progressDialog.dismiss()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext,"Fail", Toast.LENGTH_LONG).show()
                }
            })
        }

        //---------------------------------------------------------------------------------------
        //after change of addToCart to GoToCart

        binding.GoToCart.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }

    }

    //----------------------------------------------------------------------
    //on back press function

    override fun onBackPressed() {
            super.onBackPressed()
    }

    //-------------------------------------------------------------------------------------
    //on back back pressed the list will be refreshed
    override fun onRestart() {
        super.onRestart()
        recreate()

    }
}