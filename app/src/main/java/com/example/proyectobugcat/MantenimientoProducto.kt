package com.example.proyectobugcat


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectobugcat.Entidad.Producto
import com.google.firebase.firestore.FirebaseFirestore

class MantenimientoProducto : AppCompatActivity() {

    private lateinit var productoAdapter: CustomAdapterProducto
    private lateinit var listViewProductos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento_producto)

        productoAdapter = CustomAdapterProducto(this, emptyList())
        listViewProductos = findViewById(R.id.mant_rvListProductos)
        listViewProductos.adapter = productoAdapter

        val btnCerrarSesion = findViewById<Button>(R.id.mantp_btnCerrarSesion)
        val btnAtras: Button = findViewById(R.id.List_btnAtras)

        btnCerrarSesion.setOnClickListener{
            val titleMsg:String = "Confirmacion"
            val bodyMsg:String = "¿Estas seguro que desea Cerrar Sesion?"
            showModalConfirmExit(titleMsg,bodyMsg);
        }
        btnAtras.setOnClickListener{
            val mantenimientoScreen = Intent(this,MantenimientoActivity::class.java)
            startActivity(mantenimientoScreen)
        }

        cargarProductosDesdeFirestore()
    }

    private fun cargarProductosDesdeFirestore() {
        val db = FirebaseFirestore.getInstance()
        val productosRef = db.collection("Producto")

        productosRef.get()
            .addOnSuccessListener { result ->
                val productos = mutableListOf<Producto>()

                for (document in result) {
                    val id = document.id
                    val imagen = document.getString("imagen") ?: ""
                    val nombre = document.getString("nombre") ?: ""
                    val precio = document.getDouble("precio") ?: 0.0
                    val descripcion = document.getString("descripcion") ?: ""

                    val producto = Producto(id, imagen, nombre, precio, descripcion)
                    productos.add(producto)
                }

                productoAdapter.actualizarProductos(productos)
            }
            .addOnFailureListener { exception ->

            }
    }

    fun registrar(view: View){
        startActivity(Intent(this,RegistroProducto::class.java))
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
