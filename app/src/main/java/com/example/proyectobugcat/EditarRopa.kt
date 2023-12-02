package com.example.proyectobugcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.proyectobugcat.Entidad.Producto
import com.example.proyectobugcat.Entidad.Ropa
import com.google.firebase.firestore.FirebaseFirestore

class EditarRopa : AppCompatActivity() {

    private lateinit var txtNomRopa: EditText
    private lateinit var txtImaRopa: EditText
    private lateinit var txtDesRopa: EditText
    private lateinit var txtPrecioRopa: EditText
    private lateinit var txtTalla: EditText

    // Botones
    private lateinit var btnActualizarR: Button
    private lateinit var btnRegresarR: Button
    private lateinit var btnEliminar: Button

    private lateinit var ropa: Ropa
    private lateinit var txtIdRopa: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_ropa)
        txtNomRopa = findViewById(R.id.txtNomR)
        txtImaRopa = findViewById(R.id.txtImaR)
        txtDesRopa = findViewById(R.id.txtDesR)
        txtPrecioRopa = findViewById(R.id.txtPrecioR)
        txtTalla = findViewById(R.id.txtTallasR)
        btnActualizarR = findViewById(R.id.btnActualizarR)
        btnRegresarR = findViewById(R.id.btnRegresarR)
        btnEliminar = findViewById(R.id.btnEliminarR)
        txtIdRopa = findViewById(R.id.txt_id_ropa)

        ropa = intent.getSerializableExtra("ropa") as Ropa
        if (ropa != null) {
            txtNomRopa.setText(ropa.nombre)
            txtImaRopa.setText(ropa.imagen)
            txtDesRopa.setText(ropa.descripcion)
            txtPrecioRopa.setText(ropa.precio.toString())
            txtTalla.setText(ropa.tallas)
            txtIdRopa.text = "ID: ${ropa.id}"
        } else {
            Toast.makeText(this, "Error al obtener datos de la ropa", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnActualizarR.setOnClickListener {
            val nombre = txtNomRopa.text.toString()
            val descripcion = txtDesRopa.text.toString()
            val imagen = txtImaRopa.text.toString()
            val precio = txtPrecioRopa.text.toString().toDoubleOrNull()
            val tallas = txtTalla.text.toString()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imagen.isNotEmpty() && precio != null&& tallas.isNotEmpty()) {
                ropa.nombre = nombre
                ropa.descripcion = descripcion
                ropa.imagen = imagen
                ropa.precio = precio
                ropa.tallas = tallas
                // Actualizar los datos en Firestore
                actualizarRopa(ropa)
            } else {
                Toast.makeText(this, "Complete todos los campos correctamente", Toast.LENGTH_LONG).show()
            }
        }
        btnEliminar.setOnClickListener {
            eliminarRopa(ropa.id)
        }
        btnRegresarR.setOnClickListener {
            val intent = Intent(this, MantenimientoRopa::class.java)
            startActivity(intent)
            finish()
        }


    }
    private fun actualizarRopa(ropa: Ropa) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Ropa").document(ropa.id).set(ropa).addOnSuccessListener {
            Toast.makeText(this, "Ropa actualizada", Toast.LENGTH_LONG).show()
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error al actualizar la Ropa", Toast.LENGTH_LONG).show()
        }
    }
    private fun eliminarRopa(id: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Ropa").document(id).delete().addOnSuccessListener {
                Toast.makeText(this, "Ropa eliminado", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MantenimientoRopa::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
        }.addOnFailureListener { exception ->
                Toast.makeText(this, "Error al eliminar la ropa", Toast.LENGTH_LONG).show()
        }
    }
}