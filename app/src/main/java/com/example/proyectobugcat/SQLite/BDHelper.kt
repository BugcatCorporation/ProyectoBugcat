package com.example.proyectobugcat.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BDHelper (context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object {
        private  val DATABASE_NAME = "BUGCAT"
        private  val DATABASE_VERSION = 1
        private  val TABLA_USUARIO  = "USUARIO"
        private  val COLUMN_ID  = "IDUSER"
        private  val COLUMN_CORREO  = "CORREO"
        private  val COLUMN_USUARIO  = "USUARIO"
        private  val COLUMN_CONTRA  = "CONTRA"

    }

    override fun onCreate(db: SQLiteDatabase) {
        var queryCreateTable = ("Create table " + TABLA_USUARIO + "(" +
                COLUMN_ID +" INT PRIMARY KEY," +
                COLUMN_USUARIO +" TEXT, " +
                COLUMN_CORREO +" TEXT, " +
                COLUMN_CONTRA +" TEXT"+")"
                )
        db.execSQL(queryCreateTable)
    }

    fun CrearRegistro(correo: String, usuario: String, contrasena:String){
        val values = ContentValues();
        values.put(COLUMN_USUARIO,usuario)
        values.put(COLUMN_CORREO,correo)
        values.put(COLUMN_CONTRA,contrasena)
        val db = this.writableDatabase
        db.insert(TABLA_USUARIO,null,values)
        db.close()
    }

    fun ListarTodosRegistros(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("select * from "+ TABLA_USUARIO, null)
    }

    fun Acceder(usuario: String, contrasena: String): Cursor? {
        val db = this.readableDatabase
        val sql = "SELECT * FROM " + TABLA_USUARIO + " WHERE "+ COLUMN_USUARIO + " = '" +usuario + "' AND " +
                COLUMN_CONTRA + " = '" +contrasena + "' "
        return db.rawQuery(sql,null)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}