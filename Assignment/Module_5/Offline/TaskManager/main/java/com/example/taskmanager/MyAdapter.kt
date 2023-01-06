package com.example.tasktodo

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.taskmanager.R

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
        var view = inflater.inflate(R.layout.design,p2,false)

        var name : TextView = view.findViewById(R.id.task_name)
        var img : ImageView= view.findViewById(R.id.task_img)
        var rl : RelativeLayout = view.findViewById(R.id.rl)
//        var status : TextView = view.findViewById(R.id.task_status)

        name.setText(list.get(p0).name)
//        status.setText(list.get(p0).status)

        if (list.get(p0).status=="A")
        {
            img.setImageResource(R.drawable.countdown)
        }
        else if (list.get(p0).status=="B")
        {
            img.setImageResource(R.drawable.correct)
        }

        if (list.get(p0).priority=="high")
        {
            rl.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")))
        }
        else if (list.get(p0).priority=="avg")
        {
            rl.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0000FF")))
        }
        else if (list.get(p0).priority=="low")
        {
            rl.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")))
        }
        return view
    }

}