package com.example.proyectobugcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
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

        if(validarCampos(email,password)){
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.email == "admin@bugcat.gg") {
                        startActivity(Intent(this, MantenimientoActivity::class.java))
                    } else {
                        startActivity(Intent(this, CatalogoActivity::class.java))
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

    private fun validarCampos(correo: String, contra: String) : Boolean{
        if( correo.isEmpty() || contra.isEmpty()){
            Toast.makeText(this,"Por favor, rellena todos los campos", Toast.LENGTH_LONG).show()
            return false
        } else if (contra.length < 5) {
            Toast.makeText(this,"contraseña deben tener al menos 5 caracteres", Toast.LENGTH_LONG).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this,"Por favor, introduce un correo válido ", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }



}