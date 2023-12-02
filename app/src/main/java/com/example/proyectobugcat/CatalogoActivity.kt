package com.example.proyectobugcat

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectobugcat.Entidad.CustomAdapterDetalle
import com.example.proyectobugcat.Entidad.CustomAdapterRopa
import com.example.proyectobugcat.Entidad.Producto
import com.example.proyectobugcat.Entidad.Ropa
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class CatalogoActivity : AppCompatActivity() {
    lateinit var navegacion: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        navegacion = findViewById(R.id.bottomNavigation)
        navegacion.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.btnlsArticulos -> {
                    startActivity(Intent(this, RegistroActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.btnlsCarrito -> {
                    startActivity(Intent(this, CarritoActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false
        }

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

                val listViewProductos = findViewById<ListView>(R.id.mant_rvListProductos)
                val productoAdapter = CustomAdapterDetalle(this, productos)
                listViewProductos.adapter = productoAdapter
            }
            .addOnFailureListener { exception ->
                // Maneja errores si es necesario
            }
    }
}
