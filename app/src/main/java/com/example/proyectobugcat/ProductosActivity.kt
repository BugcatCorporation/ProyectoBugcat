package com.example.proyectobugcat

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectobugcat.SQLite.CustomAdapter
import com.example.proyectobugcat.SQLite.ItemViewModel

class ProductosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        //Codigo para boton cerrar sesion
        val btnCerrarSesion:Button = findViewById(R.id.btnCerrarSesion)

        btnCerrarSesion.setOnClickListener{
            val titleMsg:String = "Confirmacion"
            val bodyMsg:String = "¿Estas seguro que desea salir de la App?"

            showModalConfirmExit(titleMsg,bodyMsg);
        }
        //FIN

        // INICIO CARGAR PLATOS EN REPEATERVIEW
        //Codigo para cargar productos en ReapeaterView
        val productosRecycler : RecyclerView = findViewById(R.id.lista_productos)
        productosRecycler.layoutManager = LinearLayoutManager(this)



    }


    private fun showModalConfirmExit(titleMsg: String, bodyMsg: String) {
        val dialogConfirm = Dialog(this)
        dialogConfirm.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogConfirm.setCancelable(false)
        dialogConfirm.setContentView(R.layout.custom_modal_dialog)
        dialogConfirm.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //Inicio

        val titulo:TextView = dialogConfirm.findViewById(R.id.modalTittle)
        val mensaje:TextView = dialogConfirm.findViewById(R.id.modalMessage)
        val btnAceptar:Button = dialogConfirm.findViewById(R.id.btnModalAceptar)
        val btnCancelar:Button = dialogConfirm.findViewById(R.id.btnModalCancelar)

        titulo.text=titleMsg
        mensaje.text=bodyMsg

        btnAceptar.setOnClickListener{
            val PantallaScreen = Intent(this,PantallaPrincipal::class.java)
            startActivity(PantallaScreen)
        }
        btnCancelar.setOnClickListener{
            Toast.makeText(this,"Mas te vale >:v", Toast.LENGTH_LONG).show()
            dialogConfirm.dismiss()
        }
        dialogConfirm.show()
    }
}