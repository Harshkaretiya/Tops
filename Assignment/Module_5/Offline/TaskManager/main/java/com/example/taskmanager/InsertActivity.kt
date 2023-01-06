package com.example.taskmanager

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktodo.DbHelper
import com.example.tasktodo.Model
import java.util.*


class InsertActivity : AppCompatActivity() {

    lateinit var create_task_name : EditText
    lateinit var create_task_description : EditText
    lateinit var create_button : Button
    lateinit var cancle_button : Button
    lateinit var dbHelper: DbHelper
    lateinit var date : EditText
    lateinit var time : EditText
    lateinit var high : RadioButton
    lateinit var avg : RadioButton
    lateinit var low : RadioButton
    lateinit var priority : String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        create_task_name = findViewById(R.id.create_task_name)
        create_task_description = findViewById(R.id.create_task_description)
        create_button = findViewById(R.id.create_button)
        cancle_button = findViewById(R.id.cancel_button)
        date = findViewById(R.id.date)
        time = findViewById(R.id.time)
        high = findViewById(R.id.high)
        avg = findViewById(R.id.avg)
        low = findViewById(R.id.low)

        dbHelper = DbHelper(applicationContext)

        high.setOnCheckedChangeListener { compoundButton, b ->
            if (high.isChecked)
            {
               priority = "high"
            }
        }
        avg.setOnCheckedChangeListener { compoundButton, b ->
            if (avg.isChecked)
            {
                priority = "avg"
            }
        }
        low.setOnCheckedChangeListener { compoundButton, b ->
            if (low.isChecked)
            {
              priority = "low"
            }
        }

        create_button.setOnClickListener {
            var name = create_task_name.text.toString()
            var description = create_task_description.text.toString()
            var date = date.text.toString()
            var time = time.text.toString()

            var m = Model()
            m.name = name
            m.description = description
            m.status = "A"
            m.priority = priority
            m.date = date
            m.time = time


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

        date.setOnClickListener {

            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    date.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }
        time.setOnClickListener {
            val c = Calendar.getInstance()

            // on below line we are getting our hour, minute.
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            // on below line we are initializing
            // our Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                this,
                { view, hourOfDay, minute ->
                    // on below line we are setting selected
                    // time in our text view.
                    time.setText("$hourOfDay:$minute")
                },
                hour,
                minute,
                false
            )
            // at last we are calling show to
            // display our time picker dialog.
            timePickerDialog.show()
        }

    }
}