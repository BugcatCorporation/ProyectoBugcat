package com.example.proyectobugcat

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.proyectobugcat.SQLite.BDHelper
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var txtCorreo: EditText
    private lateinit var txtContra: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtCorreo = findViewById(R.id.txtCorreoLogin)
        txtContra = findViewById(R.id.txtContrasenaLogin)
        auth = Firebase.auth
    }
    fun registro(view: View){
        startActivity(Intent(this,RegistroActivity::class.java))
    }

    fun login(view: View) {
        val email = txtCorreo.text.toString()
        val password = txtContra.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user?.email == "admin@bugcat.gg") {
                    startActivity(Intent(this, MantenimientoActivity::class.java))
                } else {
                    startActivity(Intent(this, ProductosActivity::class.java))
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(baseContext,"Error en la autentificación, verifique los datos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



}