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


class MantenimientoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento)

        val btnMantProductos =findViewById<Button>(R.id.mant_btnListarProducto)
        val btnRegProductos =findViewById<Button>(R.id.mant_btnregistroproducto)

        val btnCerrarSesion = findViewById<Button>(R.id.mant_btnCerrarSesion)

        btnMantProductos.setOnClickListener{
            var MantProductosScreen = Intent(this, ListadoProducto::class.java)
            startActivity(MantProductosScreen)
        }

        btnCerrarSesion.setOnClickListener{
            val titleMsg:String = "Confirmacion"
            val bodyMsg:String = "¿Estas seguro que desea Cerrar Sesion?"
            showModalConfirmExit(titleMsg,bodyMsg);
        }

        btnRegProductos.setOnClickListener{
            var RegProdcutoScreen = Intent(this,RegistroProducto::class.java)
            startActivity(RegProdcutoScreen)
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