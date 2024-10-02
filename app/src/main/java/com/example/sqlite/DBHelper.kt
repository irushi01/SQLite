package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 1
        const val NAME_COL = "name"
        const val AGE_COL = "age"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE mytable ($NAME_COL TEXT, $AGE_COL INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS mytable")
        onCreate(db)
    }

    fun addName(name: String, age: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(NAME_COL, name)
            put(AGE_COL, age.toInt())
        }
        db.insert("mytable", null, contentValues)
        db.close()
    }

    fun getName(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM mytable", null)
    }
}