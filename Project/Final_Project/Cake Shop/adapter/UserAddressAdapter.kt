package com.example.e_commerce2.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce2.API.ApiClient
import com.example.e_commerce2.API.ApiInterface
import com.example.e_commerce2.Extra.ModelUserAddress
import com.example.e_commerce2.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAddressAdapter(var context: Context, var list: MutableList<ModelUserAddress>) : RecyclerView.Adapter<MyView5>()
{

    lateinit var apiInterface : ApiInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView5 {
        var data = LayoutInflater.from(context)
        var view = data.inflate(R.layout.address_design,parent,false)
        return MyView5(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyView5, @SuppressLint("RecyclerView") position: Int) {

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)

        holder.street.text = list[position].street
        holder.city.text = list[position].city
        holder.state.text = list[position].state
        holder.zipcode.text = list[position].zipcode
        holder.country.text = list[position].country

        val uid = list[position].uid
        val aid = list[position].aid

        var call2: Call<List<ModelUserAddress>> = apiInterface.primaryaddressview(uid)
        call2.enqueue(object: Callback<List<ModelUserAddress>>
        {
            override fun onResponse(call: Call<List<ModelUserAddress>>, response: Response<List<ModelUserAddress>>) {
                if (this != null) {
                    var list2 = response.body() as MutableList<ModelUserAddress>
                    if (list2.isEmpty())
                    { }
                    else
                    {
                        if (list2[0].aid == aid) {
                            holder.primary.visibility = View.VISIBLE
                        }
                        else
                        {
                            holder.primary.visibility = View.GONE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<ModelUserAddress>>, t: Throwable) {
                Toast.makeText(context,"No Internet", Toast.LENGTH_LONG).show()
                holder.primary.visibility = View.GONE
            }
        })

        holder.itemView.setOnClickListener {
            var call : Call<ResponseBody> = apiInterface.setprimary(uid,aid)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    notifyDataSetChanged()

                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context,"Fail", Toast.LENGTH_LONG).show()
                }
            })
        }

    }
}
class MyView5(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    var street : TextView = itemView.findViewById(R.id.street)
    var city : TextView =itemView.findViewById(R.id.city)
    var state : TextView =itemView.findViewById(R.id.state)
    var zipcode : TextView =itemView.findViewById(R.id.zipcode)
    var country : TextView =itemView.findViewById(R.id.country)
    var primary : TextView = itemView.findViewById(R.id.primary)
}