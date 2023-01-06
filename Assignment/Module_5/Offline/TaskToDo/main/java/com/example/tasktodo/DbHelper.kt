package com.example.tasktodo

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
        var NAME="name"
        var DESC="desc"
        var STATUS = "status"
        var DB_VERSION=1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        var query="CREATE TABLE " + TABLE_NAME + "("+ ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"+ DESC + " TEXT,"  + STATUS + " TEXT" + ")"
        p0!!.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertdata(m:Model) : Long
    {
        var db:SQLiteDatabase = this.writableDatabase
        var values = ContentValues()
        values.put(NAME,m.name)
        values.put(DESC,m.description)
        values.put(STATUS,m.status)
        var id = db.insert(TABLE_NAME,ID,values)
        return id
    }

    fun viewdata():MutableList<Model>
    {
        var list :MutableList<Model> = ArrayList()
        var db:SQLiteDatabase = this.readableDatabase
//        var data = arrayOf(ID,NAME,DESC)
//        var field = arrayOf("_id")
//        var cursor:Cursor=db.query(TABLE_NAME, field, data.toString(),null,null,null,null,null)
        var cursor:Cursor=db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext())
        {
            var id =cursor.getInt(0)
            var name=cursor.getString(1)
            var description = cursor.getString(2)
            var status = cursor.getString(3)

            var m = Model()
            m.id=id
            m.name= name
            m.description= description
            m.status = status
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

    fun deleteall(id:Int)
    {
        var db:SQLiteDatabase = this.writableDatabase
//        var deletequery = ID+" = "+id
        db.delete(TABLE_NAME,"id = "+ id,null)
    }

    fun deletedata(id: Int)
    {
        var db:SQLiteDatabase = this.writableDatabase
        db.delete(TABLE_NAME,"id = "+id,null)
    }

    fun updatename(m:Model,id: Int)
    {
        var db:SQLiteDatabase = this.writableDatabase
        var values = ContentValues()
        values.put(NAME,m.name)
//        var id = db.insert(TABLE_NAME,ID,values)
        db.update(TABLE_NAME, values,"id = "+id, null)
    }
    fun updatedesc(m:Model,id: Int)
    {
        var db:SQLiteDatabase = this.writableDatabase
        var values = ContentValues()
        values.put(DESC,m.description)
//        var id = db.insert(TABLE_NAME,ID,values)
        db.update(TABLE_NAME, values,"id = "+id, null)
    }
    fun updatestatus(m:Model,id: Int)
    {
        var db:SQLiteDatabase = this.writableDatabase
        var values = ContentValues()
        values.put(STATUS,m.status)
//        var id = db.insert(TABLE_NAME,ID,values)
        db.update(TABLE_NAME, values,"id = "+id, null)
    }
}