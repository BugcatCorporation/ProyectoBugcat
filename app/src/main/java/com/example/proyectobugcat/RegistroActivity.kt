package com.example.proyectobugcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.proyectobugcat.SQLite.BDHelper

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnGrabar : Button = findViewById(R.id.btnGrabarUsuario)
        btnGrabar.setOnClickListener{
            val inputCorreo : EditText = findViewById(R.id.txtCorreo)
            val inputUsuario : EditText = findViewById(R.id.txtUsuario)
            val inputContra : EditText = findViewById(R.id.txtContrasena)
            val correo = inputCorreo.text.toString()
            val usuario = inputUsuario.text.toString()
            val contra = inputContra.text.toString()

            if(validarCampos(usuario, correo, contra)){
                val db = BDHelper(this,null)
                db.CrearRegistro(correo,usuario,contra)
                Toast.makeText(this,"Se registro el usuario de manera exitosa", Toast.LENGTH_LONG).show()
                inputCorreo.text.clear()
                inputUsuario.text.clear()
                inputContra.text.clear()
            }

        }

        val btnVer : Button = findViewById(R.id.btnver)
        btnVer.setOnClickListener{
            val db = BDHelper(this,null)
            val cursor = db.ListarTodosRegistros()

            cursor!!.moveToLast()
            val indexCorreo = cursor.getColumnIndex("CORREO")
            val correo = cursor!!.getString(indexCorreo)

            Toast.makeText(this, "Ultimo correo registrado es" + correo, Toast.LENGTH_SHORT).show()
        }

        val btnVolver: Button = findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener{
            val pantallaPrincipal = Intent(this, PantallaPrincipal::class.java)
            startActivity(pantallaPrincipal)
        }
    }

    private fun validarCampos(usuario: String, correo: String, contra: String) : Boolean{
        if(usuario.isEmpty() || correo.isEmpty() || contra.isEmpty()){
            Toast.makeText(this,"Por favor, rellena todos los campos", Toast.LENGTH_LONG).show()
            return false
        } else if (usuario.length < 5 || contra.length < 5) {
            Toast.makeText(this,"El usuario y la contraseña deben tener al menos 5 caracteres", Toast.LENGTH_LONG).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this,"Por favor, introduce un correo válido ", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }


}