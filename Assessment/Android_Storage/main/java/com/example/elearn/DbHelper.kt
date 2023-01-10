package com.example.elearn

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement
import android.view.Display.Mode
import android.widget.Toast

class DbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION)
{
    companion object
    {
        var DB_NAME="user.db"
        var TABLE_NAME="info"
        var ID="id"
        var QUE="que"
        var OP1="op1"
        var OP2="op2"
        var OP3="op3"
        var OP4="op4"
        var ANS="ans"
        var MARKS="marks"

        var COUNTER="counter"
        var CID="cid"
        var COUNT="count"
        var DB_VERSION=1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        var query="CREATE TABLE " + TABLE_NAME + "("+ ID + " INTEGER PRIMARY KEY," + QUE + " TEXT,"+ OP1 + " TEXT,"  + OP2 + " TEXT," +OP3 + " TEXT," +OP4 + " TEXT," + ANS + " TEXT,"+ MARKS + " TEXT" +")"
        var query1="CREATE TABLE " + COUNTER + "("+ CID + " INTEGER PRIMARY KEY," + COUNT + " TEXT" + ")"
        p0!!.execSQL(query)
        p0.execSQL(query1)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertdata(list: MutableList<Model3>,position: Int) : Long
    {
        var db:SQLiteDatabase = this.writableDatabase
        var values = ContentValues()

        values.put(QUE,list.get(position).que)
        values.put(OP1,list.get(position).op1)
        values.put(OP2,list.get(position).op2)
        values.put(OP3,list.get(position).op3)
        values.put(OP4,list.get(position).op4)
        values.put(ANS,list.get(position).ans)
        values.put(MARKS,0)

//        var c = ContentValues()
//        c.put(COUNT,0)
//        db.insert(COUNTER, CID,c)

        var id = db.insert(TABLE_NAME,ID,values)

        return id
    }


//    fun insertcount() :Long
//    {
//        var db:SQLiteDatabase = this.writableDatabase
//        var c = ContentValues()
//
//        c.put(COUNT,0)
//
//        var id = db.insert(COUNTER,CID,c)
//        return id
//    }

//    fun updatecounter()
//    {
//        var db:SQLiteDatabase = this.writableDatabase
//        var c = ContentValues()
//        var a = COUNT.toInt()
//        c.put(COUNT,a++)
//        db.update(COUNTER,c,"cid = 1",null)
//    }

    fun viewdata():MutableList<Model2>
    {
        var list :MutableList<Model2> = ArrayList()
        var db:SQLiteDatabase = this.readableDatabase
//        var data = arrayOf(ID,NAME,DESC)
//        var field = arrayOf("_id")
//        var cursor:Cursor=db.query(TABLE_NAME, field, data.toString(),null,null,null,null,null)
        var cursor:Cursor=db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext())
        {
            var id =cursor.getInt(0)
            var que=cursor.getString(1)
            var op1 = cursor.getString(2)
            var op2 = cursor.getString(3)
            var op3 = cursor.getString(4)
            var op4 = cursor.getString(5)
            var ans = cursor.getString(6)
            var marks = cursor.getInt(7)

            var m = Model2()
            m.id=id
            m.que= que
            m.op1= op1
            m.op2= op2
            m.op3= op3
            m.op4= op4
            m.ans = ans
            m.marks = marks
            list.add(m)
        }
        return list
    }


    fun getid(position : Int) : Int
    {
        var db:SQLiteDatabase = this.readableDatabase
        var cursor:Cursor=db.rawQuery("SELECT * FROM $TABLE_NAME",null)
        cursor.move(position)
        var id = cursor.getInt(0)
        return id
    }
//    fun getcount() : Int
//    {
//        var db:SQLiteDatabase = this.readableDatabase
//        var cursor:Cursor=db.rawQuery("SELECT * FROM $COUNTER",null)
////        cursor.move(position)
//        var id = cursor.getInt(0)
//        return id
//    }


//    fun startquiz() : Int
//    {
//        return (ID).toInt()
//    }

    fun deleteall()
    {
        var db:SQLiteDatabase = this.writableDatabase
        var deletequery = "DELETE FROM "+ TABLE_NAME
        db.execSQL(deletequery)
//        db.delete(TABLE_NAME,"id = "+ id,null)
    }

    fun deletedata(id: Int)
    {
        var db:SQLiteDatabase = this.writableDatabase
        db.delete(TABLE_NAME,"id = "+id,null)
    }

    fun updatemarks(m:Model2,id: Int)
    {
        var db:SQLiteDatabase = this.writableDatabase
        var values = ContentValues()
        values.put(MARKS,m.marks)
//        var id = db.insert(TABLE_NAME,ID,values)
        db.update(TABLE_NAME, values,"id = "+id, null)
    }
    fun viewmarks(): String? {
        var db:SQLiteDatabase = this.readableDatabase
        var values = ContentValues()
//        values.put(DESC,m.description)
//        var id = db.insert(TABLE_NAME,ID,values)
        var cursor  : Cursor? = db.rawQuery("SELECT SUM(marks) FROM info", null)
        cursor!!.moveToFirst()
//        var total = cursor!!.getString(0)
//        var t : String
//        while (cursor!!.moveToNext())
//        {

            var total = cursor!!.getString(0)
//            var total = "5"
//            return total
//            t = total
//        }
        return total
    }
//    fun updatestatus(m:Model,id: Int)
//    {
//        var db:SQLiteDatabase = this.writableDatabase
//        var values = ContentValues()
//        values.put(STATUS,m.status)
////        var id = db.insert(TABLE_NAME,ID,values)
//        db.update(TABLE_NAME, values,"id = "+id, null)
//    }
}