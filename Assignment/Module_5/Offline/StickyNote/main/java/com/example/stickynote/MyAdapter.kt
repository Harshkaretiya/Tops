package com.example.stickynote

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stickynote.R

class MyAdapter(var context: Context,var list: MutableList<Model>) : BaseAdapter()
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

    @SuppressLint("MissingInflatedId")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflater : LayoutInflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.list_view_design,p2,false)

        var name : TextView = view.findViewById(R.id.note_name)

        name.setText(list.get(p0).name)

        return view
    }

}