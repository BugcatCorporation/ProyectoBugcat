package com.example.proyectobugcat

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.material3.ListItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectobugcat.Entidad.CustomAdapterRopa
import com.example.proyectobugcat.Entidad.Producto
import com.example.proyectobugcat.Entidad.Ropa
import com.google.firebase.firestore.FirebaseFirestore

class MantenimientoRopa : AppCompatActivity() {

    private lateinit var ropaAdapter: CustomAdapterRopa
    private lateinit var ListViewRopa: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento_ropa)

        ropaAdapter= CustomAdapterRopa( this, emptyList())
        ListViewRopa = findViewById(R.id.mantr_rvListRopa)
        ListViewRopa.adapter = ropaAdapter

        val btnAtras: Button = findViewById(R.id.Lista_btnAtras)
        val btnCerrarSesion: Button = findViewById(R.id.mantr_btnCerrarSesion)


        btnCerrarSesion.setOnClickListener{
            val titleMsg:String = "Confirmacion"
            val bodyMsg:String = "¿Estas seguro que desea cerrar sesion?"

            showModalConfirmExit(titleMsg,bodyMsg);
        }
        btnAtras.setOnClickListener{
            val mantenimientoScreen = Intent(this,MantenimientoActivity::class.java)
            startActivity(mantenimientoScreen)
        }

        cargarRopaDesdeFirestore()

    }

    private fun cargarRopaDesdeFirestore() {
        val db = FirebaseFirestore.getInstance()
        val ropaRef = db.collection("Ropa")

        ropaRef.get()
            .addOnSuccessListener { result ->
                val ropas = mutableListOf<Ropa>()

                for (document in result) {
                    val id = document.id
                    val imagen = document.getString("imagen") ?: ""
                    val nombre = document.getString("nombre") ?: ""
                    val precio = document.getDouble("precio") ?: 0.0
                    val descripcion = document.getString("descripcion") ?: ""
                    val tallas = document.getString("tallas")?:""

                    val ropa = Ropa (id, imagen, nombre, descripcion,precio, tallas)

                    ropas.add(ropa)
                }

                ropaAdapter.actualizarProductos(ropas)
            }
            .addOnFailureListener { exception ->

            }
    }

    fun registrar(view: View){
        startActivity(Intent(this, RegistroRopa::class.java))
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
        val btnAceptar: Button = dialogConfirm.findViewById(R.id.btnModalAceptar)
        val btnCancelar: Button = dialogConfirm.findViewById(R.id.btnModalCancelar)

        titulo.text=titleMsg
        mensaje.text=bodyMsg

        btnAceptar.setOnClickListener{
            val PantallaScreen = Intent(this,PantallaPrincipal::class.java)
            startActivity(PantallaScreen)
        }
        btnCancelar.setOnClickListener{
            Toast.makeText(this,"Accion cancelada", Toast.LENGTH_LONG).show()
            dialogConfirm.dismiss()
        }
        dialogConfirm.show()
    }
}