package com.example.e_commerce2.Fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.RecyclerGridAdapter
import com.example.e_commerce2.RecyclerListAdapter
//import com.example.e_commerce1.RecyclerGridAdapter
//import com.example.e_commerce1.RecyclerListAdapter
import com.example.e_commerce2.*
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.activity.CartActivity
import com.example.e_commerce2.Extra.ImageSlider
import com.example.e_commerce2.Extra.Model
import com.example.e_commerce2.Extra.ModelUserAddress
import com.example.e_commerce2.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    lateinit var list : MutableList<Model>
    lateinit var apiInterface: ApiInterface
    lateinit var sliderView: SliderView
    lateinit var auth: FirebaseAuth

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding!!.root

        var manager : RecyclerView.LayoutManager = GridLayoutManager(requireActivity(), 5)
        binding!!.gridItem.layoutManager=manager

        var manager2 : RecyclerView.LayoutManager = LinearLayoutManager(requireActivity())
        binding!!.listItem.layoutManager=manager2

        binding!!.cartHomeFragment.setOnClickListener {
            startActivity(Intent(requireActivity(),CartActivity::class.java))
        }

        auth = Firebase.auth
        var uid = auth.uid

//        val progressDialog = Dialog(requireActivity())
//
//        progressDialog.setContentView(R.layout.custom_progress_dialog)
//        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        progressDialog.setCancelable(false)
//        progressDialog.show()

        val progressDialog = CustomProgressDialogFragment()
            progressDialog.show(parentFragmentManager, "CustomProgressDialog")


        list = ArrayList()

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        var call: Call<List<Model>> = apiInterface.getdata()
        call.enqueue(object: Callback<List<Model>>
        {
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                if (context != null) {
                    list = response.body() as MutableList<Model>

                    var adapter = RecyclerGridAdapter(requireActivity(), list.take(5) as MutableList<Model>)
                    binding!!.gridItem.adapter = adapter

                    var adapter2 = RecyclerListAdapter(requireActivity(), list.take(5) as MutableList<Model>)
                    binding!!.listItem.adapter = adapter2
                }

            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                Toast.makeText(requireActivity(), "No Internet", Toast.LENGTH_LONG).show()
                progressDialog.dismiss()
            }
        })

        var images = ArrayList<String>()
        images.add("https://hk123234345.000webhostapp.com/ProjectTest/image/2.jpg")
        images.add("https://hk123234345.000webhostapp.com/ProjectTest/image/3.jpg")
        images.add("https://hk123234345.000webhostapp.com/ProjectTest/image/4.jpg")

        sliderView = view.findViewById(R.id.image_slider)
        val imageSlider = ImageSlider(images)
        sliderView.setSliderAdapter(imageSlider)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.scrollTimeInSec = 5
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        startSlider(true)



        var call2: Call<List<ModelUserAddress>> = apiInterface.primaryaddressview(uid!!)
        call2.enqueue(object: Callback<List<ModelUserAddress>>
        {
            override fun onResponse(call: Call<List<ModelUserAddress>>, response: Response<List<ModelUserAddress>>) {
                if (context != null) {
                    var list2 = response.body() as MutableList<ModelUserAddress>
                    if (list2.isEmpty())
                    {

                    }
                    else {
                        binding!!.locationArea.text = list2[0].street
                        binding!!.locationCity.text = list2[0].city
                    }
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<List<ModelUserAddress>>, t: Throwable) {
                Toast.makeText(context,"No Internet", Toast.LENGTH_LONG).show()
            }
        })


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun startSlider(run : Boolean)
    {
        if (run)
            sliderView.startAutoCycle()
        else
            sliderView.stopAutoCycle()
    }

    override fun onResume() {
        super.onResume()

        auth = Firebase.auth
        var uid = auth.uid

        var call2: Call<List<ModelUserAddress>> = apiInterface.primaryaddressview(uid!!)
        call2.enqueue(object: Callback<List<ModelUserAddress>>
        {
            override fun onResponse(call: Call<List<ModelUserAddress>>, response: Response<List<ModelUserAddress>>) {
                if (context != null) {
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
                Toast.makeText(context,"No Internet", Toast.LENGTH_LONG).show()
            }
        })
    }

}
class CustomProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val progressDialog = Dialog(requireContext())
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)
        return progressDialog
    }

}