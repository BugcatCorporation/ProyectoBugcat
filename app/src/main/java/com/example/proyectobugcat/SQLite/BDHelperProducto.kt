package com.example.proyectobugcat.SQLite
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class BDHelperProducto (context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object {
           val DATABASE_NAME = "BUGCATProducto"
           val DATABASE_VERSION = 1
           val TABLA_PRODUCTO  = "PRODUCTO"
           val COLUMN_ID  = "IDPRODUCTO"
           val COLUMN_NOMBRE  = "NOMBRE"
           val COLUMN_DESCRIPCION  = "DESCRIPCION"
           val COLUMN_PRECIO  = "PRECIO"
           val  COLUMN_IMAGEN = "IMAGEN"

    }

    override fun onCreate(db: SQLiteDatabase) {
        var queryCreateTable = ("Create table " + TABLA_PRODUCTO + "(" +
                COLUMN_ID +" INT PRIMARY KEY," +
                COLUMN_NOMBRE +" TEXT, " +
                COLUMN_DESCRIPCION +" TEXT, " +
                COLUMN_PRECIO +" TEXT, " +
                COLUMN_IMAGEN +" TEXT"+")"
                )
        db.execSQL(queryCreateTable)
    }

    fun CrearRegistro(nombre: String, descripcion: String, precio: String, imagen: String) {
        val values = ContentValues();
        values.put(COLUMN_NOMBRE, nombre)
        values.put(COLUMN_DESCRIPCION, descripcion)
        values.put(COLUMN_PRECIO, precio)
        values.put(COLUMN_IMAGEN, imagen)  // Almacena solo la ruta, no el URI completo
        val db = this.writableDatabase
        db.insert(TABLA_PRODUCTO, null, values)
        db.close()
    }

    fun ListarTodosRegistros(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("select * from "+ TABLA_PRODUCTO, null)
    }

    fun ActualizarRegistro(id: Long, nombre: String, descripcion: String, precio: String, imagen: String): Int {
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, nombre)
        values.put(COLUMN_DESCRIPCION, descripcion)
        values.put(COLUMN_PRECIO, precio)
        values.put(COLUMN_IMAGEN, imagen)

        val db = this.writableDatabase
        return db.update(TABLA_PRODUCTO, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun EliminarRegistro(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(TABLA_PRODUCTO, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}