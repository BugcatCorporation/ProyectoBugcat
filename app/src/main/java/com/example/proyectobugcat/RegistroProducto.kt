package com.example.proyectobugcat

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectobugcat.R
import com.example.proyectobugcat.SQLite.BDHelperProducto
import com.example.proyectobugcat.SQLite.CustomAdapter
import com.example.proyectobugcat.SQLite.ItemViewModel

class RegistroProducto : AppCompatActivity() {

    private lateinit var data: ArrayList<ItemViewModel>
    private lateinit var adapter: CustomAdapter
    private lateinit var dbProducto: BDHelperProducto
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_producto)

        data = ArrayList()

        // Verifica y solicita permisos
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
        }

        val Nombre: EditText = findViewById(R.id.editTextNombre)
        val Descripcion: EditText = findViewById(R.id.editTextDescripcion)
        val Precio: EditText = findViewById(R.id.editTextPrecio)
        val btnGuardar: Button = findViewById(R.id.btnRegistrarproducto)
        val btnCargarImagen: Button = findViewById(R.id.btnCargarImagen)
        val imageViewProducto: ImageView = findViewById(R.id.imageViewProducto)

        dbProducto = BDHelperProducto(this, null)

        btnCargarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        btnGuardar.setOnClickListener {
            val nombre = Nombre.text.toString().trim()
            val descripcion = Descripcion.text.toString().trim()
            val precio = Precio.text.toString().trim()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty() && selectedImageUri != null) {
                val imagenPath = selectedImageUri.toString()

                // Guarda el registro en la base de datos
                dbProducto.CrearRegistro(nombre, descripcion, precio, imagenPath)
                Toast.makeText(this, "Producto guardado correctamente", Toast.LENGTH_SHORT).show()

                // Agrega el nuevo registro a la lista y notifica al adaptador
                data.add(ItemViewModel(imagenPath, nombre, descripcion, precio))
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(
                    this,
                    "Completa todos los campos y selecciona una imagen",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    // Manejo del resultado de la selección de la imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data

            if (selectedImageUri != null) {
                // Muestra la imagen seleccionada en el ImageView
                val imageViewProducto: ImageView = findViewById(R.id.imageViewProducto)
                imageViewProducto.setImageURI(selectedImageUri)
            } else {
                // Maneja el caso donde la URI es nula
                Toast.makeText(this, "Error al obtener la URI de la imagen", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}