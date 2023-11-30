package com.example.proyectobugcat

import CustomAdapterProducto
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectobugcat.Entidad.Producto
import com.google.firebase.firestore.FirebaseFirestore


class MantenimientoProducto : AppCompatActivity() {

    lateinit var ProductoRecycler: ListView
    lateinit var btnAtras: Button
    lateinit var btnCerrarSesion: Button
    lateinit var btnRegProducto: Button
    private lateinit var customAdapterProducto: CustomAdapterProducto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento_producto)

        // Mueve estas inicializaciones aquí después de setContentView
        ProductoRecycler = findViewById(R.id.mant_rvListProductos)
        btnAtras = findViewById(R.id.List_btnAtras)
        btnCerrarSesion = findViewById(R.id.mantp_btnCerrarSesion)
        btnRegProducto = findViewById(R.id.mantp_btnRegistrar)

        btnRegProducto.setOnClickListener {
            var MantRegProductoScreen = Intent(this, RegistroProducto::class.java)
            startActivity(MantRegProductoScreen)
        }
        btnCerrarSesion.setOnClickListener {
            val titleMsg: String = "Confirmacion"
            val bodyMsg: String = "¿Estás seguro que deseas cerrar sesión?"
            showModalConfirmExit(titleMsg, bodyMsg)
        }
        btnAtras.setOnClickListener {
            val mantenimientoScreen = Intent(this, MantenimientoActivity::class.java)
            startActivity(mantenimientoScreen)
        }



        customAdapterProducto = CustomAdapterProducto(this, ArrayList())


        obtenerProductos()

        ProductoRecycler.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent (this, EditarProducto::class.java)
            startActivity(intent)


    }
}

    private fun obtenerProductos() {
        val productosList = mutableListOf<Producto>()

        val db = FirebaseFirestore.getInstance()
        val productosRef = db.collection("Producto")

        productosRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val id = document.getString("id") ?: ""
                    val imagen = document.getString("imagen") ?: ""
                    val nombre = document.getString("nombre") ?: ""
                    val precio = document.getDouble("precio") ?: 0.0
                    val descripcion = document.getString("descripcion") ?: ""

                    val producto = Producto(id, imagen, nombre, precio, descripcion)
                    productosList.add(producto)
                }

                customAdapterProducto = CustomAdapterProducto(this, productosList)
                ProductoRecycler.adapter = customAdapterProducto
            }
    }

    private fun showModalConfirmExit(titleMsg: String, bodyMsg: String) {
        val dialogConfirm = Dialog(this)
        dialogConfirm.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogConfirm.setCancelable(false)
        dialogConfirm.setContentView(R.layout.custom_modal_dialog)
        dialogConfirm.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val titulo: TextView = dialogConfirm.findViewById(R.id.modalTittle)
        val mensaje: TextView = dialogConfirm.findViewById(R.id.modalMessage)
        val btnAceptar: Button = dialogConfirm.findViewById(R.id.btnModalAceptar)
        val btnCancelar: Button = dialogConfirm.findViewById(R.id.btnModalCancelar)

        titulo.text = titleMsg
        mensaje.text = bodyMsg

        btnAceptar.setOnClickListener {
            val PantallaScreen = Intent(this, PantallaPrincipal::class.java)
            startActivity(PantallaScreen)
        }
        btnCancelar.setOnClickListener {
            Toast.makeText(this, "Acción cancelada", Toast.LENGTH_LONG).show()
            dialogConfirm.dismiss()
        }
        dialogConfirm.show()
    }
}
