package com.example.proyectobugcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegistroRopa : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_ropa)

        val btnAtras: Button = findViewById(R.id.btnVolver)

        btnAtras.setOnClickListener{
            val MantenimientoRopaScreen = Intent(this,MantenimientoRopa::class.java)
            startActivity(MantenimientoRopaScreen)
        }

    }




}