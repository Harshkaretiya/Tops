package com.example.taskmanager

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ListView
import androidx.cardview.widget.CardView
import com.example.tasktodo.DbHelper
import com.example.tasktodo.Model
import com.example.tasktodo.MyAdapter

class MainActivity : AppCompatActivity() {

    lateinit var list: MutableList<Model>
    lateinit var list_view: ListView
    lateinit var dbHelper: DbHelper
    lateinit var insert : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DbHelper(applicationContext)

        list = dbHelper.viewdata()

        setStatusBarColor(Color.parseColor("#99FFF0"))

        list_view = findViewById(R.id.list)
        insert = findViewById(R.id.insert)

        var adapter = MyAdapter(applicationContext,list)
        list_view.adapter = adapter
        insert.setOnClickListener {
            var i = Intent(this,InsertActivity::class.java)
            startActivity(i)

        }

        list_view.setOnItemClickListener { adapterView, view, i, l ->
            var a = adapterView.getItemAtPosition(i).toString()

            var i = Intent(this,ViewTaskActivity::class.java)
            i.putExtra("id",a)
            startActivity(i)
        }

    }
    fun setStatusBarColor(color: Int) {
        var window = this.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }

    }
}