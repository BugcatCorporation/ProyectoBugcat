package com.example.proyectobugcat

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectobugcat.SQLite.BDHelperProducto
import com.example.proyectobugcat.SQLite.CustomAdapter
import com.example.proyectobugcat.SQLite.ItemViewModel

class ListadoProducto : AppCompatActivity() {

    private lateinit var data: ArrayList<ItemViewModel>
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mant_producto)

        data = ArrayList()

        val productosRecycler: RecyclerView = findViewById(R.id.mant_rvListProductos)
        productosRecycler.layoutManager = LinearLayoutManager(this)

        val db = BDHelperProducto(this, null)
        val cursor = db.ListarTodosRegistros()

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(BDHelperProducto.COLUMN_NOMBRE))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(BDHelperProducto.COLUMN_DESCRIPCION))
                val precio = cursor.getString(cursor.getColumnIndexOrThrow(BDHelperProducto.COLUMN_PRECIO))
                val imagenPath = cursor.getString(cursor.getColumnIndexOrThrow(BDHelperProducto.COLUMN_IMAGEN))
                val imagenUri = Uri.parse(imagenPath)

                data.add(ItemViewModel(imagenUri.toString(), nombre, descripcion, precio))


            }
            cursor.close()
        }

        adapter = CustomAdapter(data)  // Inicializa el adaptador antes de utilizarlo
        productosRecycler.adapter = adapter

        adapter.notifyDataSetChanged()  // Notifica que los datos han cambiado después de inicializar el adaptador
    }
}
