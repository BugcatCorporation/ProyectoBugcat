package com.example.proyectobugcat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectobugcat.Entidad.Producto
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import android.widget.Toast

class DetalleProductoActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        // Obtener datos del intent
        val producto = intent.getSerializableExtra("producto") as? Producto
        if (producto != null) {
            // Actualizar la interfaz con los datos del producto
            actualizarInterfaz(producto)

            // Configurar el botón "Agregar al carrito"
            val btnAgregarCarrito: Button = findViewById(R.id.btn_agregar_carrito)
            btnAgregarCarrito.setOnClickListener {
                agregarAlCarrito(producto)
            }

            val btnVolver: Button = findViewById(R.id.btn_volver_carrito)
            btnVolver.setOnClickListener {
                volverACatalogo()
            }




        }



    }

    private fun volverACatalogo() {
        val intent = Intent(this, CatalogoActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun actualizarInterfaz(producto: Producto) {
        // Referencias a las vistas en el layout
        val imagenImageView: ImageView = findViewById(R.id.img_imagen_producto)
        val nombreTextView: TextView = findViewById(R.id.txt_nombre_producto)
        val precioTextView: TextView = findViewById(R.id.txt_precio_producto)
        val descripcionTextView: TextView = findViewById(R.id.txt_descripcion_producto)

        // Cargar la imagen utilizando Picasso
        Picasso.get().load(producto.imagen).into(imagenImageView)

        // Actualizar los TextView con los datos del producto
        nombreTextView.text = "Nombre: ${producto.nombre}"
        precioTextView.text = "Precio: S/.${producto.precio}"
        descripcionTextView.text = "Descripción: ${producto.descripcion}"
    }

    private fun agregarAlCarrito(producto: Producto) {
        // Crear un mapa con los datos del producto
        val datosCarrito = hashMapOf(
            "nombre" to producto.nombre,
            "precio" to producto.precio,
            "descripcion" to producto.descripcion,
            "imagen" to producto.imagen
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
