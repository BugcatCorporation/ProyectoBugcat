package com.example.proyectobugcat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectobugcat.Entidad.Empleado
import com.google.firebase.firestore.FirebaseFirestore

class RegistroEmpleado : AppCompatActivity() {

    private lateinit var btnRegistrar: Button
    private lateinit var btnRegresar: Button
    private lateinit var txtNombre: EditText
    private lateinit var txtImagen: EditText
    private lateinit var txtCargo: EditText
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_empleado)

        btnRegistrar = findViewById(R.id.btnRegistrarE)
        btnRegresar = findViewById(R.id.btnRegresarE)
        txtNombre = findViewById(R.id.txtNomEmp)
        txtImagen = findViewById(R.id.txtImaEmp)
        txtCargo = findViewById(R.id.txtCarEmp)

        btnRegresar.setOnClickListener {
            val MantenimientoEmpleadoScreen = Intent(this, MantenimientoEmpleado::class.java)
            startActivity(MantenimientoEmpleadoScreen)
        }
        btnRegistrar.setOnClickListener{
            val nombre = txtNombre.text.toString()
            val imagen = txtImagen.text.toString()
            val cargo = txtCargo.text.toString()
            if (nombre.isNotEmpty() && imagen.isNotEmpty() && cargo.isNotEmpty()){
                val empleado = Empleado(imagen, nombre, cargo)
                db.collection("Empleado").add(empleado).addOnSuccessListener {
                        documentReference -> val nuevoId = documentReference.id
                    empleado.id = nuevoId
                    db.collection("Empleado").document(nuevoId).set(empleado).addOnSuccessListener {
                        Toast.makeText(this, "Nuevo empleado registrado", Toast.LENGTH_LONG).show()
                        var MantenimientoEmpleadoScreen = Intent(this, MantenimientoEmpleado::class.java)
                        startActivity(MantenimientoEmpleadoScreen)

                    }
                }
                btnRegresar.setOnClickListener {
                    Toast.makeText(this, "Acción cancelada", Toast.LENGTH_LONG).show()
                    var MantenimientoEmpleadoScreen =  Intent(this, MantenimientoEmpleado::class.java)
                    startActivity(MantenimientoEmpleadoScreen)
                }
                val permission = Manifest.permission.READ_EXTERNAL_STORAGE
                if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
                }
            }
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