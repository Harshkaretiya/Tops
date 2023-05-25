package com.example.e_commerce2.Fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.adapter.FavouriteRecyclerGridAdapter
import com.example.e_commerce2.Extra.Model
import com.example.e_commerce2.R
import com.example.e_commerce2.databinding.FragmentFavouriteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding
    private lateinit var auth: FirebaseAuth
    lateinit var list : MutableList<Model>
    lateinit var apiInterface: ApiInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val view = binding!!.root


        var manager : RecyclerView.LayoutManager = GridLayoutManager(requireActivity(), 2)
        binding!!.favouriteGridList.layoutManager=manager

        auth = Firebase.auth
        val uid = auth.currentUser!!.uid

        list = ArrayList()

        val progressDialog = Dialog(requireActivity())
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)
        progressDialog.show()

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        val call: Call<List<Model>> = apiInterface.getidfav(uid)
        call.enqueue(object: Callback<List<Model>>
        {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {

                if (response.body() == null || response.body()!!.isEmpty())
                {
                    progressDialog.dismiss()
                    binding!!.emptyList.visibility = View.VISIBLE
                }
                if (context != null) {
                    list = response.body() as MutableList<Model>
//                    binding!!.progressBar.visibility = View.GONE
                    val adapter = FavouriteRecyclerGridAdapter(requireActivity(), list)
                    binding!!.favouriteGridList.adapter = adapter
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(requireActivity(),"No Internet", Toast.LENGTH_LONG).show()
            }
        })

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        auth = Firebase.auth
        var uid = auth.currentUser!!.uid

        val progressDialog = Dialog(requireActivity())
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)
        progressDialog.show()

        val call: Call<List<Model>> = apiInterface.getidfav(uid)
        call.enqueue(object: Callback<List<Model>>
        {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                if (response.body() == null || response.body()!!.isEmpty())
                {
                    progressDialog.dismiss()
                    binding!!.emptyList.visibility = View.VISIBLE
                }
                if (context != null) {
                    list = response.body() as MutableList<Model>
//                    binding!!.progressBar.visibility = View.GONE
                    val adapter = FavouriteRecyclerGridAdapter(requireActivity(), list)
                    binding!!.favouriteGridList.adapter = adapter
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(requireActivity(),"No Internet", Toast.LENGTH_LONG).show()
            }
        })
    }

}