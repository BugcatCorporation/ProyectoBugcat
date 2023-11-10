package com.example.proyectobugcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MantenimientoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento)

        val btnMantProductos =findViewById<Button>(R.id.mant_btnListProducto)
        val btnMantCategorias =findViewById<Button>(R.id.mant_btnListProducto)
        val btnCerrarSesion = findViewById<Button>(R.id.mant_btnCerrarSesion)

        btnMantProductos.setOnClickListener{
            var MantProductosScreen = Intent(this, MantProductoActivity::class.java)
            startActivity(MantProductosScreen)
        }

        btnCerrarSesion.setOnClickListener {
            var PantallaPrincipalScreen = Intent(this,PantallaPrincipal::class.java)
            startActivity(PantallaPrincipalScreen)
        }


    }
}