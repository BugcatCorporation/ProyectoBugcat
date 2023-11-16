package com.example.proyectobugcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class loginEmpleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_empleado)

        val MantenimientoActivity = findViewById<Button>(R.id.btnAccederEmpleado);
        MantenimientoActivity.setOnClickListener {
            var MantenimientoActivityScreem = Intent(this, MantenimientoActivity::class.java)
            startActivity(MantenimientoActivityScreem)
        }

        val Empleado = findViewById<Button>(R.id.btnRegistrarEmpleado);
                 Empleado.setOnClickListener {
            var RegistroEmpleadoScreem = Intent(this, RegistroEmpleado::class.java)
            startActivity(RegistroEmpleadoScreem)
        }

        val inputUsuarioEmpleado: EditText = findViewById(R.id.txtUsuario)
        val inputcontraEmpleado: EditText = findViewById(R.id.txtContrasena)

        }
    }

