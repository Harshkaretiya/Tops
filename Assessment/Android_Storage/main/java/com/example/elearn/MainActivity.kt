package com.example.elearn

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.Toast
import androidx.core.view.get

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var gridView: GridView
    lateinit var list: MutableList<Model>
    lateinit var dbHelper: DbHelper


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.grid)

        list = ArrayList()

        list.add(Model("Play Quiz"))
        list.add(Model("Read Quetions"))

        var adapter = MyAdapter(this,list)
        gridView.adapter =adapter

        dbHelper = DbHelper(applicationContext)
//        var id = dbHelper.insertcount()

        var data = QueData()
        dbHelper.deleteall()
        var i =0
        for (i in 0..9) {
            var id = dbHelper.insertdata(data.getlist(), i)
        }
        gridView.setOnItemClickListener { adapterView, view, i, l ->
//            id = dbHelper.insertdata(data.getlist(), 1)
//            id = dbHelper.insertdata(data.getlist(), 2)
//            id = dbHelper.insertdata(data.getlist(), 3)
//            id = dbHelper.insertdata(data.getlist(), 4)
//            id = dbHelper.insertdata(data.getlist(), 5)
//            id = dbHelper.insertdata(data.getlist(), 6)
//            id = dbHelper.insertdata(data.getlist(), 7)

//            var id : Long


//            for (i in 0..9) {
//            }

//            while (i<10)
//            {
//               var id = dbHelper.insertdata(data.getlist(), i)
//            }

            var a = adapterView.getItemAtPosition(i).toString()
//            for (a in 1..9) {
//                var id = dbHelper.insertdata(data.getlist(), a)
//            }

//            var a = 0
            if (a.toInt()==0) {
                var i = Intent(this, QuizActivity::class.java)
//                i.putExtra("start", a.toString())
                startActivity(i)
            }
            else if (a.toInt()==1)
            {
                var i = Intent(this, ReadActivity::class.java)
                startActivity(i)
            }
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var edit1: SharedPreferences.Editor = sharedPreferences.edit()
        when (item.itemId){
            R.id.aboutus -> {
                var i = Intent(this,AboutUsActivity::class.java)
                startActivity(i)
            }


            R.id.contactus -> {
                var i = Intent(this,ContactUsActivity::class.java)
                startActivity(i)
            }


            R.id.logout -> {
                edit1.putBoolean("User_Session",false)
                edit1.commit()
                var i = Intent(this,LoginActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}