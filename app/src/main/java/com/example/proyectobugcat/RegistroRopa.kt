package com.example.proyectobugcat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectobugcat.Entidad.Producto
import com.example.proyectobugcat.Entidad.Ropa
import com.google.firebase.firestore.FirebaseFirestore

class RegistroRopa : AppCompatActivity() {

    private lateinit var btnRegistrarR: Button
    private lateinit var btnRegresar: Button
    private lateinit var txtNombre: EditText
    private lateinit var txtImagen: EditText
    private lateinit var txtPrecio: EditText
    private lateinit var txtDescrpcion: EditText
    private lateinit var txtTallas: EditText
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_ropa)

        btnRegistrarR = findViewById(R.id.btnGrabarRopa)
        btnRegresar = findViewById(R.id.btnRegresarR)
        txtNombre = findViewById(R.id.txtNomRopa)
        txtImagen = findViewById(R.id.txtImaRopa)
        txtPrecio = findViewById(R.id.txtPrecioRopa)
        txtDescrpcion = findViewById(R.id.txtDesRopa)
        txtTallas = findViewById(R.id.txtTallasRopa)

        btnRegresar.setOnClickListener {
            val MantenimientoRopaScreen = Intent(this, MantenimientoRopa::class.java)
            startActivity(MantenimientoRopaScreen)
        }
        btnRegistrarR.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val descripcion = txtDescrpcion.text.toString()
            val imagen = txtImagen.text.toString()
            val precio = txtPrecio.text.toString().toDoubleOrNull()
            val tallas = txtTallas.text.toString()
            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imagen.isNotEmpty() && precio != null && tallas.isNotEmpty()){
                val ropa = Ropa(imagen, nombre, precio, descripcion, tallas)
                db.collection("Ropa").add(ropa).addOnSuccessListener {
                    documentReference -> val nuevoId = documentReference.id
                    ropa.id = nuevoId
                    db.collection("Ropa").document(nuevoId).set(ropa).addOnSuccessListener {
                        Toast.makeText(this, "Ropa Registrada", Toast.LENGTH_LONG)
                            .show()
                        var MantenimientoRopaScreen = Intent(this, MantenimientoRopa::class.java)
                        startActivity(MantenimientoRopaScreen)

                    }
                }
                btnRegresar.setOnClickListener {

                    Toast.makeText(this, "Acción cancelada", Toast.LENGTH_LONG).show()
                    var MantenimientoRopaScreen =
                        Intent(this, MantenimientoRopa::class.java)
                    startActivity(MantenimientoRopaScreen)

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