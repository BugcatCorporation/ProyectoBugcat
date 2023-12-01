package com.example.proyectobugcat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectobugcat.Entidad.Producto
import com.google.firebase.firestore.FirebaseFirestore

class MantenimientoProducto : AppCompatActivity() {

    private lateinit var productoAdapter: ProductoMantenimientoAdapter
    private lateinit var listViewProductos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento_producto)

        productoAdapter = ProductoMantenimientoAdapter(this, emptyList())
        listViewProductos = findViewById(R.id.mant_rvListProductos)
        listViewProductos.adapter = productoAdapter

        cargarProductosDesdeFirestore()
    }

    private fun cargarProductosDesdeFirestore() {
        val db = FirebaseFirestore.getInstance()
        val productosRef = db.collection("Producto")

        productosRef.get()
            .addOnSuccessListener { result ->
                val productos = mutableListOf<Producto>()

                for (document in result) {
                    val id = document.id
                    val imagen = document.getString("imagen") ?: ""
                    val nombre = document.getString("nombre") ?: ""
                    val precio = document.getDouble("precio") ?: 0.0
                    val descripcion = document.getString("descripcion") ?: ""

                    val producto = Producto(id, imagen, nombre, precio, descripcion)
                    productos.add(producto)
                }

                productoAdapter.actualizarProductos(productos)
            }
            .addOnFailureListener { exception ->

            }
    }

    fun registrar(view: View){
        startActivity(Intent(this,RegistroProducto::class.java))
    }




}
