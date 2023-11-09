package com.example.proyectobugcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class PantallaPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        val btnAcceder = findViewById<Button>(R.id.btnIngresar);
        val btnSalir = findViewById<Button>(R.id.btnSalir);

        val btnRegistrar : Button = findViewById(R.id.btnRegistrar);



        btnAcceder.setOnClickListener{
            var productScreen = Intent(this, ProductosActivity::class.java)
            startActivity(productScreen)
        }

        btnSalir.setOnClickListener{
            Toast.makeText(applicationContext,"Esta intentando salir de la app", Toast.LENGTH_LONG).show()
        }

        btnRegistrar.setOnClickListener{
            val registroScreen = Intent(this ,RegistroActivity::class.java)
            startActivity(registroScreen)
        }
    }
}