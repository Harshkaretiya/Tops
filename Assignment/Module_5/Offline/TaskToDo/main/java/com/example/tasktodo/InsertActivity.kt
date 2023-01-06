package com.example.tasktodo

import android.app.backup.BackupAgentHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class InsertActivity : AppCompatActivity() {

    lateinit var create_task_name : EditText
    lateinit var create_task_description : EditText
    lateinit var create_button : Button
    lateinit var cancle_button : Button
    lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        create_task_name = findViewById(R.id.create_task_name)
        create_task_description = findViewById(R.id.create_task_description)
        create_button = findViewById(R.id.create_button)
        cancle_button = findViewById(R.id.cancel_button)

        dbHelper = DbHelper(applicationContext)

        create_button.setOnClickListener {
            var name = create_task_name.text.toString()
            var description = create_task_description.text.toString()

            var m = Model()
            m.name = name
            m.description = description
            m.status = "A"

            var id = dbHelper.insertdata(m)
//            dbHelper.deleteall()
//            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
            var i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        cancle_button.setOnClickListener {
            var i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }



    }
}