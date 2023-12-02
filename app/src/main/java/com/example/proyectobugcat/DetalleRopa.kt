package com.example.proyectobugcat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectobugcat.Entidad.Ropa
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import android.widget.Toast

class DetalleRopa : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_ropa)

        // Obtener datos del intent
        val ropa = intent.getSerializableExtra("ropa") as? Ropa
        if (ropa != null) {
            // Actualizar la interfaz con los datos de la ropa
            actualizarInterfaz(ropa)

            // Configurar el botón "Agregar al carrito"
            val btnAgregarCarrito: Button = findViewById(R.id.btn_agregar_carrito)
            btnAgregarCarrito.setOnClickListener {
                agregarAlCarrito(ropa)
            }

            val btnVolver: Button = findViewById(R.id.btn_volver_carrito)
            btnVolver.setOnClickListener {
                volverACatalogo()
            }
        }
    }

    private fun volverACatalogo() {
        val intent = Intent(this, CatalogoRopa::class.java)
        startActivity(intent)
        finish()
    }

    private fun actualizarInterfaz(ropa: Ropa) {
        // Referencias a las vistas en el layout
        val imagenImageView: ImageView = findViewById(R.id.img_imagen_ropa)
        val nombreTextView: TextView = findViewById(R.id.txt_nombre_ropa)
        val precioTextView: TextView = findViewById(R.id.txt_precio_ropa)
        val descripcionTextView: TextView = findViewById(R.id.txt_descripcion_ropa)


        // Cargar la imagen utilizando Picasso
        Picasso.get().load(ropa.imagen).into(imagenImageView)

        // Actualizar los TextView con los datos de la ropa
        nombreTextView.text = "Nombre: ${ropa.nombre}"
        precioTextView.text = "Precio: S/.${ropa.precio}"
        descripcionTextView.text = "Descripción: ${ropa.descripcion}"

    }

    private fun agregarAlCarrito(ropa: Ropa) {
        // Crear un mapa con los datos de la ropa
        val datosCarrito = hashMapOf(
            "nombre" to ropa.nombre,
            "precio" to ropa.precio,
            "descripcion" to ropa.descripcion,
            "imagen" to ropa.imagen,
            "tallas" to ropa.tallas
        )

        // Añadir los datos a la colección "carrito" en Firestore
        db.collection("carrito")
            .add(datosCarrito)
            .addOnSuccessListener {
                mostrarMensaje("Agregado Correctamente")
            }
            .addOnFailureListener { e ->
                mostrarMensaje("Error al agregar al carrito")
            }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}