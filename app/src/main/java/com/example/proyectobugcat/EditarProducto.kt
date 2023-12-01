package com.example.proyectobugcat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectobugcat.Entidad.Producto
import com.google.firebase.firestore.FirebaseFirestore

class EditarProducto : AppCompatActivity() {

    // Atributos
    private lateinit var txtNomProd: EditText
    private lateinit var txtImaProd: EditText
    private lateinit var txtDesProd: EditText
    private lateinit var txtPrecioPro: EditText

    // Botones
    private lateinit var btnActualizarP: Button
    private lateinit var btnRegresarP: Button
    private lateinit var btnEliminar: Button

    private lateinit var producto: Producto // Variable para almacenar el producto actual
    private lateinit var txtIdProducto: TextView // TextView para mostrar el ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)

        txtNomProd = findViewById(R.id.txtNomProd)
        txtImaProd = findViewById(R.id.txtImaProd)
        txtDesProd = findViewById(R.id.txtDesProd)
        txtPrecioPro = findViewById(R.id.txtPrecioPro)
        btnActualizarP = findViewById(R.id.btnActualizarP)
        btnRegresarP = findViewById(R.id.btnRegresarP)
        btnEliminar = findViewById(R.id.btnEliminar)
        txtIdProducto = findViewById(R.id.txt_id_producto) // Nuevo TextView para mostrar el ID

        producto = intent.getSerializableExtra("producto") as Producto

        if (producto != null) {
            txtNomProd.setText(producto.nombre)
            txtImaProd.setText(producto.imagen)
            txtDesProd.setText(producto.descripcion)
            txtPrecioPro.setText(producto.precio.toString())

            // Mostrar el ID en el TextView correspondiente
            txtIdProducto.text = "ID: ${producto.id}"
        } else {
            Toast.makeText(this, "Error al obtener datos del producto", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnActualizarP.setOnClickListener {
            val nombre = txtNomProd.text.toString()
            val descripcion = txtDesProd.text.toString()
            val imagen = txtImaProd.text.toString()
            val precio = txtPrecioPro.text.toString().toDoubleOrNull()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imagen.isNotEmpty() && precio != null) {
                // Actualizar el objeto producto con los nuevos valores
                producto.nombre = nombre
                producto.descripcion = descripcion
                producto.imagen = imagen
                producto.precio = precio

                // Actualizar los datos en Firestore
                actualizarProducto(producto)
            } else {
                Toast.makeText(this, "Complete todos los campos correctamente", Toast.LENGTH_LONG).show()
            }
        }

        btnEliminar.setOnClickListener {
            // Lógica para eliminar el producto en Firestore
            // Asegúrate de manejar los casos de éxito y error adecuadamente
            eliminarProducto(producto.id)
        }
    }

    // Función para actualizar el producto en Firestore
    private fun actualizarProducto(producto: Producto) {
        // Asegúrate de manejar los casos de éxito y error adecuadamente
        // En este punto, producto contiene los nuevos valores que deseas actualizar en Firestore
        // Utiliza el objeto 'db' para actualizar el documento correspondiente en Firestore

        val db = FirebaseFirestore.getInstance()
        db.collection("Producto").document(producto.id)
            .set(producto)
            .addOnSuccessListener {
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al actualizar el producto", Toast.LENGTH_LONG).show()
            }
    }

// Función para eliminar el producto en Firestore por su ID
    private fun eliminarProducto(id: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Producto").document(id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_LONG).show()

                // Después de eliminar, actualiza la lista en MantenimientoProducto
                val intent = Intent(this, MantenimientoProducto::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al eliminar el producto", Toast.LENGTH_LONG).show()
            }
    }




}
