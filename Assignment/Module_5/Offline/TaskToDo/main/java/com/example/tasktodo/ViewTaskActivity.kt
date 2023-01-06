package com.example.tasktodo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ViewTaskActivity : AppCompatActivity() {

    lateinit var view_task_name : TextView
    lateinit var view_task_description : TextView
    lateinit var completed_button : Button
    lateinit var delete_button : Button
    lateinit var dbHelper: DbHelper
    lateinit var list : MutableList<Model>

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)

        view_task_name = findViewById(R.id.view_task_name)
        view_task_description = findViewById(R.id.view_task_description)
        completed_button = findViewById(R.id.completed_button)
        delete_button = findViewById(R.id.delete_button)

        var i = intent
        var p = i.getStringExtra("id")!!.toInt()
        var m = Model()
        dbHelper = DbHelper(applicationContext)

        list = dbHelper.viewdata()

        view_task_name.setText(list.get(p).name)
        view_task_description.setText(list.get(p).description)

        p++

        var id = dbHelper.getid(p)

        completed_button.setOnClickListener {

            m.status="B"

            dbHelper.updatestatus(m,id)
//            Toast.makeText(this, "$p", Toast.LENGTH_SHORT).show()
            var i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        delete_button.setOnClickListener {
            dbHelper.deletedata(id)
            var i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
    }
}