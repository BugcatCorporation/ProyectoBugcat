package com.example.proyectobugcat
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnGrabar: Button = findViewById(R.id.btnGrabarUsuario)
        btnGrabar.setOnClickListener {
            val inputCorreo: EditText = findViewById(R.id.txtCorreo)
            val inputUsuario: EditText = findViewById(R.id.txtUsuario)
            val inputContra: EditText = findViewById(R.id.txtContrasena)
            val correo = inputCorreo.text.toString()
            val usuario = inputUsuario.text.toString()
            val contra = inputContra.text.toString()

            if (validarCampos(usuario, correo, contra)) {
                registrarUsuarioEnFirebaseAuth(correo, contra, usuario)
            }
        }

        val btnVolver: Button = findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener {
            val pantallaPrincipal = Intent(this, PantallaPrincipal::class.java)
            startActivity(pantallaPrincipal)
        }
    }

    private fun validarCampos(usuario: String, correo: String, contra: String): Boolean {
        if (usuario.isEmpty() || correo.isEmpty() || contra.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_LONG).show()
            return false
        } else if (usuario.length < 5 || contra.length < 5) {
            Toast.makeText(this, "El usuario y la contraseña deben tener al menos 5 caracteres", Toast.LENGTH_LONG).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Por favor, introduce un correo válido ", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun registrarUsuarioEnFirebaseAuth(correo: String, contra: String, usuario: String) {
        // Registro en Firebase Authentication
        auth.createUserWithEmailAndPassword(correo, contra)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    // Guardar información adicional en Firestore
                    guardarInformacionAdicionalEnFirestore(uid, correo, usuario)
                } else {
                    Toast.makeText(
                        this, "Error al registrar el usuario en Firebase Authentication",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun guardarInformacionAdicionalEnFirestore(uid: String?, correo: String, usuario: String) {
        // Crear un mapa con la información adicional del usuario
        val datosUsuario = hashMapOf(
            "uid" to uid,
            "correo" to correo,
            "usuario" to usuario
        )

        // Agregar un documento a la colección "usuarios" en Firestore con los datos
        db.collection("usuario")
            .document(uid!!)
            .set(datosUsuario)
            .addOnSuccessListener {
                // Éxito al guardar en Firestore
                Toast.makeText(
                    this, "Usuario registrado exitosamente",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener { exception ->
                // Manejar errores si es necesario
                Toast.makeText(
                    this, "Error al guardar la información adicional en Firestore: $exception",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}
