package com.example.proyectobugcat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectobugcat.Entidad.CustomAdapterRopa
import com.example.proyectobugcat.Entidad.Ropa
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class CatalogoRopa : AppCompatActivity() {
    lateinit var navegacion: BottomNavigationView
    lateinit var btnArticulos: Button
    lateinit var btnCarrito: Button
    lateinit var btnOtros: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo_ropa)

        navegacion = findViewById(R.id.bottomNavigation)
        navegacion.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.btnlsArticulos -> {
                    startActivity(Intent(this, CatalogoActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.btnlsCarrito -> {
                    startActivity(Intent(this, CarritoActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false
        }

        navegacion = findViewById(R.id.bottomNavigation)
        btnArticulos = findViewById(R.id.btnArticulos)
        btnCarrito = findViewById(R.id.btnCarrito)
        btnOtros = findViewById(R.id.btnOtros)

        btnArticulos.setOnClickListener {
            startActivity(Intent(this, CatalogoActivity::class.java))
        }

        btnCarrito.setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }

        btnOtros.setOnClickListener {
            // Agrega aquí el código para la acción del botón "Otros"
        }

        cargarRopaDesdeFirestore()
    }

    private fun cargarRopaDesdeFirestore() {
        val db = FirebaseFirestore.getInstance()
        val ropaRef = db.collection("Ropa")

        ropaRef.get()
            .addOnSuccessListener { result ->
                val ropaList = mutableListOf<Ropa>()

                for (document in result) {
                    val id = document.id
                    val imagen = document.getString("imagen") ?: ""
                    val nombre = document.getString("nombre") ?: ""
                    val precio = document.getDouble("precio") ?: 0.0
                    val descripcion = document.getString("descripcion") ?: ""

                    val ropa = Ropa(id, descripcion, imagen, nombre, precio)
                    ropaList.add(ropa)
                }

                val listViewRopa = findViewById<ListView>(R.id.mant_rvListRopa)
                val ropaAdapter = CustomAdapterRopa(this, ropaList)
                listViewRopa.adapter = ropaAdapter
            }
            .addOnFailureListener { exception ->
                // Maneja errores si es necesario
            }
    }
}
