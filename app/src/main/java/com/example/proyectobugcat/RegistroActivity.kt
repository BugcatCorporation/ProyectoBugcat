package com.example.proyectobugcat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val db = BDHelper(this,null)
            db.CrearRegistro(correo,usuario,contra)
            Toast.makeText(this,"Se registro el usuario de manera exitosa", Toast.LENGTH_LONG)
            inputCorreo.text.clear()
            inputUsuario.text.clear()
            inputContra.text.clear()
        }

        val btnVer : Button = findViewById(R.id.btnver)
        btnVer.setOnClickListener{
            val db = BDHelper(this,null)
            val cursor = db.ListarTodosRegistros()

            cursor!!.moveToLast()
            val indexCorreo = cursor.getColumnIndex("CORREO")
            val correo = cursor!!.getString(indexCorreo)

            Toast.makeText(this, "Ultimo correo registrado es " + correo, Toast.LENGTH_SHORT).show()
        }
    }
}