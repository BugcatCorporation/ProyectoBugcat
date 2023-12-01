package com.example.proyectobugcat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectobugcat.Entidad.Producto
import com.google.firebase.firestore.FirebaseFirestore


class RegistroProducto : AppCompatActivity() {

    private lateinit var btnRegistrarP: Button
    private lateinit var btnRegresar: Button
    private lateinit var txtNombre: EditText
    private lateinit var txtImagen: EditText
    private lateinit var txtPrecio: EditText
    private lateinit var txtDescrpcion: EditText
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_producto)

        btnRegistrarP = findViewById(R.id.btnRegistrarP)
        btnRegresar = findViewById(R.id.btnRegresarP)
        txtNombre = findViewById(R.id.txtNomProd)
        txtImagen = findViewById(R.id.txtImaProd)
        txtPrecio = findViewById(R.id.txtPrecioPro)
        txtDescrpcion = findViewById(R.id.txtDesProd)


        btnRegistrarP.setOnClickListener {

            val nombre = txtNombre.text.toString()
            val descripcion = txtDescrpcion.text.toString()
            val imagen = txtImagen.text.toString()
            val precio = txtPrecio.text.toString().toDoubleOrNull()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imagen.isNotEmpty() && precio != null) {
                val producto = Producto(imagen, nombre, precio, descripcion)

                db.collection("Producto")
                    .add(producto)
                    .addOnSuccessListener { documentReference ->
                        val nuevoId = documentReference.id
                        producto.id = nuevoId
                        db.collection("Producto").document(nuevoId)
                            .set(producto)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Producto Registrado", Toast.LENGTH_LONG)
                                    .show()
                                var MantenimientoProductoScreen =
                                    Intent(this, MantenimientoProducto::class.java)
                                startActivity(MantenimientoProductoScreen)

                            }
                    }

                btnRegresar.setOnClickListener {

                    Toast.makeText(this, "Acción cancelada", Toast.LENGTH_LONG).show()
                    var MantenimientoProductoScreen =
                        Intent(this, MantenimientoProducto::class.java)
                    startActivity(MantenimientoProductoScreen)

                }
                // Verifica y solicita permisos
                val permission = Manifest.permission.READ_EXTERNAL_STORAGE
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
                }
            }
        }
    }
}