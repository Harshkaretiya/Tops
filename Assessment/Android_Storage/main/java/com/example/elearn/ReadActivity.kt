package com.example.elearn

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class ReadActivity : AppCompatActivity() {

    lateinit var list : ListView
    lateinit var dbHelper : DbHelper
    lateinit var list_view : MutableList<Model2>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        list = findViewById(R.id.list)

        dbHelper = DbHelper(applicationContext)


        list_view = dbHelper.viewdata()

        var adapter = MyAdapter2(this,list_view)
        list.adapter = adapter

    }
}