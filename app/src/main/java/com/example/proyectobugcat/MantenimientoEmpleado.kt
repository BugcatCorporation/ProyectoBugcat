package com.example.proyectobugcat

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class MantenimientoEmpleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento_empleado)

        val btnAtras:Button = findViewById(R.id.mante_btnAtras)
        val btnCerrarSesion:Button = findViewById(R.id.mante_btnCerrarSesion)
        val empleadoRecycler: RecyclerView = findViewById(R.id.mante_rvListEmpleado)
        val btnRegEmpleado:Button=findViewById(R.id.mante_btnRegistrar)

        btnRegEmpleado.setOnClickListener{
            var MantProductosScreen = Intent(this, RegistroEmpleado::class.java)
            startActivity(MantProductosScreen)
        }
        btnCerrarSesion.setOnClickListener{
            val titleMsg:String = "Confirmacion"
            val bodyMsg:String = "¿Estas seguro que desea cerrar sesion?"

            showModalConfirmExit(titleMsg,bodyMsg);
        }
        btnAtras.setOnClickListener{
            val mantenimientoScreen = Intent(this,MantenimientoActivity::class.java)
            startActivity(mantenimientoScreen)
        }

        empleadoRecycler.layoutManager = LinearLayoutManager(this)




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
            Toast.makeText(this,"Accion cancelada", Toast.LENGTH_LONG).show()
            dialogConfirm.dismiss()
        }
        dialogConfirm.show()
    }
}
