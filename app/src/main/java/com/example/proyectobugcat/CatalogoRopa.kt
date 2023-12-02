package com.example.proyectobugcat

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectobugcat.Entidad.CustomAdapterRopa
import com.example.proyectobugcat.Entidad.Ropa
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class CatalogoRopa : AppCompatActivity() {
    lateinit var navegacion: BottomNavigationView
    lateinit var btnArticulos: Button
    lateinit var btnCarrito: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo_ropa)

        navegacion = findViewById(R.id.bottomNavigation)
        navegacion.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.btnlsArticulos -> {
                    startActivity(Intent(this, CatalogoActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.btnlsRopa -> {
                    startActivity(Intent(this, CatalogoRopa::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.btnlsCarrito -> {
                    startActivity(Intent(this, CarritoActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false
        }

        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        navegacion = findViewById(R.id.bottomNavigation)
        btnArticulos = findViewById(R.id.btnArticulos)
        btnCarrito = findViewById(R.id.btnCarrito)

        btnCerrarSesion.setOnClickListener{
            val titleMsg:String = "Confirmacion"
            val bodyMsg:String = "¿Estas seguro que desea Cerrar Sesion?"
            showModalConfirmExit(titleMsg,bodyMsg);
        }
        btnArticulos.setOnClickListener {
            startActivity(Intent(this, CatalogoActivity::class.java))
        }

        btnCarrito.setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }

        cargarRopaDesdeFirestore()
    }

    private fun cargarRopaDesdeFirestore() {
        val db = FirebaseFirestore.getInstance()
        val ropaRef = db.collection("Ropa")

        ropaRef.get()
            .addOnSuccessListener { result ->
                val ropaList = mutableListOf<Ropa>()

                for (document in result) {
                    val id = document.id
                    val imagen = document.getString("imagen") ?: ""
                    val nombre = document.getString("nombre") ?: ""
                    val precio = document.getDouble("precio") ?: 0.0
                    val descripcion = document.getString("descripcion") ?: ""

                    val ropa = Ropa(id, descripcion, imagen, nombre, precio)
                    ropaList.add(ropa)
                }

                val listViewRopa = findViewById<ListView>(R.id.mant_rvListRopa)
                val ropaAdapter = CustomAdapterRopa(this, ropaList)
                listViewRopa.adapter = ropaAdapter
            }
            .addOnFailureListener { exception ->
                // Maneja errores si es necesario
            }
    }
    private fun showModalConfirmExit(titleMsg: String, bodyMsg: String) {
        val dialogConfirm = Dialog(this)
        dialogConfirm.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogConfirm.setCancelable(false)
        dialogConfirm.setContentView(R.layout.custom_modal_dialog)
        dialogConfirm.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //Inicio

        val titulo: TextView = dialogConfirm.findViewById(R.id.modalTittle)
        val mensaje: TextView = dialogConfirm.findViewById(R.id.modalMessage)
        val btnAceptar:Button = dialogConfirm.findViewById(R.id.btnModalAceptar)
        val btnCancelar:Button = dialogConfirm.findViewById(R.id.btnModalCancelar)

        titulo.text=titleMsg
        mensaje.text=bodyMsg

        btnAceptar.setOnClickListener{
            val PantallaScreen = Intent(this,PantallaPrincipal::class.java)
            startActivity(PantallaScreen)
        }
        btnCancelar.setOnClickListener{
            Toast.makeText(this,"Accion cancelada", Toast.LENGTH_SHORT).show()
            dialogConfirm.dismiss()
        }
        dialogConfirm.show()
    }
}