package com.example.elearn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyAdapter2(var context: Context,var list: MutableList<Model2>) : BaseAdapter()
{
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflater : LayoutInflater = LayoutInflater.from(context)
        var view= inflater.inflate(R.layout.que_design,p2,false)

        var text : TextView =view.findViewById(R.id.text)

        text.setText(list.get(p0).que)
        return  view
    }

}