// CarritoActivity.kt
package com.example.proyectobugcat

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectobugcat.Entidad.Carrito
import com.example.proyectobugcat.Entidad.CustomAdapterCarrito
import com.google.firebase.firestore.FirebaseFirestore

class CarritoActivity : AppCompatActivity() {

    // Instancia de Firestore
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val btnComprar: Button = findViewById(R.id.CC_btnComprar)
        val btnBorrar: Button = findViewById(R.id.CC_btnBorrarTodo)
        val btnVolver: Button = findViewById(R.id.CC_btnVolver)

        btnComprar.setOnClickListener {
            val titleMsg: String = "Confirmacion"
            val bodyMsg: String = "¿Estás seguro que desea comprar los artículos seleccionados?"

            // Lógica personalizada al hacer clic en "Aceptar"
            val onAccept = {
                // Mostrar Toast
                Toast.makeText(this, "Compra Realizada", Toast.LENGTH_SHORT).show()

                // Eliminar el elemento de la lista
                // (Aquí necesitas agregar la lógica específica para tu adaptador y datos)
                // Por ejemplo, si tu adaptador es carritoAdapter:
                // carritoAdapter.eliminarElemento(posicion)

                // Actualizar el RecyclerView
                obtenerDatosFirestore()
            }

            showModalConfirmExit(titleMsg, bodyMsg, onAccept)
        }

        btnBorrar.setOnClickListener {
            // Llamada al método para borrar todos los documentos
            borrarTodoCarrito()
        }

        btnVolver.setOnClickListener {
            val productosScreen = Intent(this, CatalogoActivity::class.java)
            startActivity(productosScreen)
        }

        // Configurar el RecyclerView y su adaptador
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview_carrito)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtener datos de Firestore
        obtenerDatosFirestore()
    }

    private fun showModalConfirmExit(titleMsg: String, bodyMsg: String, onAccept: () -> Unit) {
        val dialogConfirm = Dialog(this)
        dialogConfirm.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogConfirm.setCancelable(false)
        dialogConfirm.setContentView(R.layout.custom_modal_dialog)
        dialogConfirm.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Obtener referencias a los botones del diálogo personalizado
        val btnAceptar = dialogConfirm.findViewById<Button>(R.id.btnModalAceptar)
        val btnCancelar = dialogConfirm.findViewById<Button>(R.id.btnModalCancelar)

        // Agregar clicListeners a los botones
        btnAceptar.setOnClickListener {
            // Lógica cuando se hace clic en "Aceptar"
            onAccept.invoke()  // Invocar la función de retorno
            dialogConfirm.dismiss()
            CompraCarrito()
        }

        btnCancelar.setOnClickListener {
            // Lógica cuando se hace clic en "Cancelar"
            // Por ejemplo, cerrar el diálogo sin realizar ninguna acción
            dialogConfirm.dismiss()
        }

        dialogConfirm.show()
    }

    private fun obtenerDatosFirestore() {
        // Referencia a la colección "carrito" en Firestore
        val carritoRef = db.collection("carrito")

        // Obtener los datos de la colección "carrito"
        carritoRef.get()
            .addOnSuccessListener { result ->
                val listaCarrito = mutableListOf<Carrito>()

                for (document in result) {
                    // Obtener campos del documento
                    val nombre = document.getString("nombre") ?: ""
                    val descripcion = document.getString("descripcion") ?: ""
                    val precio = document.getDouble("precio") ?: 0.0
                    val imagen = document.getString("imagen") ?: ""

                    // Crear objeto Carrito y agregarlo a la lista
                    val carrito = Carrito(nombre, descripcion, precio, imagen)
                    listaCarrito.add(carrito)
                }

                // Crear el adaptador y establecerlo en el RecyclerView
                val carritoAdapter = CustomAdapterCarrito(this, listaCarrito)
                val recyclerView: RecyclerView = findViewById(R.id.recyclerview_carrito)
                recyclerView.adapter = carritoAdapter
            }
            .addOnFailureListener { exception ->
                // Manejar errores si es necesario
                Toast.makeText(this, "Error al obtener datos de Firestore", Toast.LENGTH_SHORT).show()
            }
    }

    private fun borrarTodoCarrito() {
        // Referencia a la colección "carrito" en Firestore
        val carritoRef = db.collection("carrito")

        // Obtener todos los documentos de la colección
        carritoRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    // Borrar cada documento
                    carritoRef.document(document.id).delete()
                }

                // Notificar al usuario que se borraron todos los documentos
                Toast.makeText(this, "Se borraron todos los elementos del carrito", Toast.LENGTH_SHORT).show()

                // Actualizar el RecyclerView
                obtenerDatosFirestore()
            }
            .addOnFailureListener { exception ->
                // Manejar errores si es necesario
                Toast.makeText(this, "Error al borrar elementos del carrito", Toast.LENGTH_SHORT).show()
            }
    }



    private fun CompraCarrito() {
        // Referencia a la colección "carrito" en Firestore
        val carritoRef = db.collection("carrito")

        // Obtener todos los documentos de la colección
        carritoRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    // Borrar cada documento
                    carritoRef.document(document.id).delete()
                }


                obtenerDatosFirestore()
            }
            .addOnFailureListener { exception ->

            }
    }












}
