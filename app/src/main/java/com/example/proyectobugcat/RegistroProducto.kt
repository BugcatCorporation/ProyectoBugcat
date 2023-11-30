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

class RegistroProducto : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_producto)

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



        btnCargarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }


    }

}