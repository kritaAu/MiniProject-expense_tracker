package com.example.krittaphat

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "Transactions"
        private const val COLUMN_ID = "id"
        private const val COLUMN_LABEL = "label"
        private const val COLUMN_AMOUNT = "amount"
        private const val COLUMN_DESC = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_LABEL TEXT, $COLUMN_AMOUNT REAL, $COLUMN_DESC TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insert(transaction: Transaction): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_LABEL, transaction.label)
            put(COLUMN_AMOUNT, transaction.amount)
            put(COLUMN_DESC, transaction.description)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }


    fun getAll(): List<Transaction> {
        val LabelList = mutableListOf<Transaction>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val label = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LABEL))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))
                LabelList.add(Transaction(id,label, amount, description))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return LabelList
    }

    fun update(id: Int,label: String, amount: Double, description: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_LABEL, label)
            put(COLUMN_AMOUNT, amount)
            put(COLUMN_DESC, description)
        }
        return db.update(TABLE_NAME, contentValues, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun delete(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }
}
