package com.example.proyectobugcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.proyectobugcat.Entidad.Empleado
import com.example.proyectobugcat.Entidad.Ropa
import com.google.firebase.firestore.FirebaseFirestore

class EditarEmpleado : AppCompatActivity() {

    private lateinit var txtNomEmp: EditText
    private lateinit var txtImaEmp: EditText
    private lateinit var txtCargo: EditText

    // Botones
    private lateinit var btnActualizarE: Button
    private lateinit var btnRegresarE: Button
    private lateinit var btnEliminarE: Button

    private lateinit var empleado: Empleado
    private lateinit var txtIdEmpleado: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_empleado)

        txtNomEmp = findViewById(R.id.txtNomEmpleado)
        txtImaEmp = findViewById(R.id.txtImaEmp)
        txtCargo = findViewById(R.id.txtCarEmp)
        btnActualizarE = findViewById(R.id.btnActualizarE)
        btnRegresarE = findViewById(R.id.btnRegresarE)
        btnEliminarE = findViewById(R.id.btnEliminarE)
        txtIdEmpleado = findViewById(R.id.txt_id_empleado)

        empleado = intent.getSerializableExtra("empleado") as Empleado
        if (empleado != null) {
            txtNomEmp.setText(empleado.nombre)
            txtImaEmp.setText(empleado.imagen)
            txtCargo.setText(empleado.cargo)
            txtIdEmpleado.text = "ID: ${empleado.id}"
        } else {
            Toast.makeText(this, "Error al obtener datos de la ropa", Toast.LENGTH_SHORT).show()
            finish()
        }
        btnActualizarE.setOnClickListener {
            val nombre = txtNomEmp.text.toString()
            val cargo = txtCargo.text.toString()
            val imagen = txtImaEmp.text.toString()

            if (nombre.isNotEmpty() && cargo.isNotEmpty() && imagen.isNotEmpty()) {
                empleado.nombre = nombre
                empleado.cargo = cargo
                empleado.imagen = imagen
                actualizarEmpleado(empleado)
            } else {
                Toast.makeText(this, "Complete todos los campos correctamente", Toast.LENGTH_LONG).show()
            }
        }
        btnEliminarE.setOnClickListener{
            eliminarEmpleado(empleado.id)
        }
    }
    private fun actualizarEmpleado(empleado: Empleado) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Empleado").document(empleado.id).set(empleado).addOnSuccessListener {
            Toast.makeText(this, "Empleado actualizado", Toast.LENGTH_LONG).show()
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error al actualizar al Empleado", Toast.LENGTH_LONG).show()
        }
    }
    private fun eliminarEmpleado(id: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Empleado").document(id).delete().addOnSuccessListener {
            Toast.makeText(this, "Empleado eliminado", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MantenimientoEmpleado::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error al eliminar al empleado", Toast.LENGTH_LONG).show()
        }
    }
}