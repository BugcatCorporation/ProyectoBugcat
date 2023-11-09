package com.example.proyectobugcat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputUsuario: EditText = findViewById(R.id.txtUsuariol)
        val inputcontra: EditText = findViewById(R.id.txtContrasenial)
        val btnLogin: Button = findViewById(R.id.Login_btnAcceder)
        btnLogin.setOnClickListener{
            val usuario = inputUsuario.text.toString()
            val contra = inputcontra.text.toString()
            val bd = BDHelper(this,null)
            val cursor = bd.Acceder(usuario, contra)
            if(cursor == null) {
                Toast.makeText(this,"Esta intentando salir de la app", Toast.LENGTH_LONG).show()
            } else {
                cursor!!.moveToFirst()
                val usuarindex = cursor.getColumnIndex("USUARIO")
                val usuario = cursor.getString(usuarindex)
            }
        }
    }
}