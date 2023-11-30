package com.example.proyectobugcat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegistroEmpleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_empleado)

        val btnGrabarEmpleados : Button = findViewById(R.id.btnGrabarEmpleados)
        btnGrabarEmpleados.setOnClickListener {

            val inputEmpleado : EditText = findViewById(R.id.txtUsuarioEmpleado)
            val inputCorreoEmpleado : EditText = findViewById(R.id.txtCorreoEmpleado)
            val inputContra : EditText = findViewById(R.id.txtContrasenaEmpleado)

            val usuarioEmpleado = inputEmpleado.text.toString()
            val correo = inputCorreoEmpleado.text.toString()
            val contra = inputContra.text.toString()



        }

    }

    private fun validarCampos(correo: String, usuario: String, contra: String): Boolean {
        if(correo.isEmpty() || usuario.isEmpty() || contra.isEmpty()) {
            Toast.makeText(this,"Por favor, rellena todos los campos", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}