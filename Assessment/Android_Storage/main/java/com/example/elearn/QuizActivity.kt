package com.example.elearn

import android.annotation.SuppressLint
import android.content.Intent
import android.content.LocusId
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class QuizActivity : AppCompatActivity() {

    lateinit var dbHelper: DbHelper
    lateinit var list :MutableList<Model2>
    lateinit var que : TextView
    lateinit var op1 : RadioButton
    lateinit var op2 : RadioButton
    lateinit var op3 : RadioButton
    lateinit var op4 : RadioButton
    lateinit var currentque : TextView
    lateinit var next : Button
    lateinit var optionselect : RadioGroup
    lateinit var submit : Button
    lateinit var marks : TextView
    lateinit var done : Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        dbHelper = DbHelper(applicationContext)

        list = dbHelper.viewdata()

        que = findViewById(R.id.que)
        op1 = findViewById(R.id.op1)
        op2 = findViewById(R.id.op2)
        op3 = findViewById(R.id.op3)
        op4 = findViewById(R.id.op4)
        currentque = findViewById(R.id.currentque)
        next = findViewById(R.id.next)
        optionselect = findViewById(R.id.optionselect)
        submit = findViewById(R.id.submit)
        marks = findViewById(R.id.currentmarks)
        done = findViewById(R.id.done)

        var grey = "#D3D3D3"
        var red = "#FF0000"
        var green = "#00FF00"

        done.visibility = View.GONE

        var data = QueData()


        var m = Model2()
        m.marks = 5

        var id = 0

        var ans = list.get(id).ans
        que.setText(list.get(id).que)
        op1.setText(list.get(id).op1)
        op2.setText(list.get(id).op2)
        op3.setText(list.get(id).op3)
        op4.setText(list.get(id).op4)
        currentque.text = list.get(id).id.toString()

        submit.setOnClickListener {
            if (op1.isChecked || op2.isChecked || op3.isChecked || op4.isChecked)
            {
                check(ans, m, id)
                submit.visibility = View.GONE
                next.visibility = View.VISIBLE
            }
            else
            {
                Toast.makeText(this, "please select the answer", Toast.LENGTH_SHORT).show()
            }

        }


        next.setOnClickListener {

            submit.visibility = View.VISIBLE
            next.visibility = View.GONE

            optionselect.clearCheck()

            changebg()

            id++

            ans = list.get(id).ans
            que.setText(list.get(id).que)
            op1.setText(list.get(id).op1)
            op2.setText(list.get(id).op2)
            op3.setText(list.get(id).op3)
            op4.setText(list.get(id).op4)
            currentque.text = list.get(id).id.toString()

            submit.setOnClickListener {
                if (op1.isChecked || op2.isChecked || op3.isChecked || op4.isChecked) {
                    check(ans, m, id)

                    if (id == 9) {
                        submit.visibility = View.GONE
                        next.visibility = View.GONE
                        done.visibility = View.VISIBLE
                    } else {
                        submit.visibility = View.GONE
                        next.visibility = View.VISIBLE
                    }
                }
                else
                {
                    Toast.makeText(this, "please select the answer", Toast.LENGTH_SHORT).show()
                }
            }

        }

        done.setOnClickListener {
            var i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

    }

    fun changebg(color1: String = "#D3D3D3",color2: String= "#D3D3D3",color3: String= "#D3D3D3",color4: String= "#D3D3D3")
    {
        op1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color1)))
        op2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color2)))
        op3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color3)))
        op4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color4)))
    }
    fun check(ans: String,m:Model2,id: Int)
    {
        var grey = "#D3D3D3"
        var red = "#FF0000"
        var green = "#00FF00"

        if (op1.isChecked) {
            if (op1.text.toString()==ans.toString()) {
                changebg(green,grey,grey,grey)
                dbHelper.updatemarks(m,list.get(id).id)
                marks.setText(dbHelper.viewmarks().toString())
            }
            else
            {
                changebg(red)
            }
        }
        else if (op2.isChecked) {
            if (op2.text.toString()==ans.toString()) {
                changebg(grey,green)
                dbHelper.updatemarks(m,list.get(id).id)
                marks.setText(dbHelper.viewmarks().toString())

            }
            else {
                changebg(grey,red)
            }
        }
        else if (op3.isChecked) {
            if (op3.text.toString()==ans.toString()) {
                changebg(grey,grey,green)
                dbHelper.updatemarks(m,list.get(id).id)
                marks.setText(dbHelper.viewmarks().toString())
            }
            else {
                changebg(grey,grey,red)
            }
        }
        else if (op4.isChecked) {
            if (op4.text.toString()==ans.toString()) {
                changebg(grey,grey,grey,green)
                dbHelper.updatemarks(m,list.get(id).id)
                marks.setText(dbHelper.viewmarks().toString())
            }
            else {
                changebg(grey,grey,grey,red)
            }
        }
    }
}