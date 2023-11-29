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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectobugcat.SQLite.BDHelperProducto
import com.example.proyectobugcat.SQLite.CustomAdapter
import com.example.proyectobugcat.SQLite.ItemViewModel

class MantenimientoRopa : AppCompatActivity() {
    private lateinit var data: ArrayList<ItemViewModel>
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento_ropa)

        data = ArrayList()

        val btnAtras: Button = findViewById(R.id.Lista_btnAtras)
        val btnCerrarSesion: Button = findViewById(R.id.mantr_btnCerrarSesion)
        val RopaRecycler: RecyclerView = findViewById(R.id.mantr_rvListRopa)
        val btnRegRopa: Button =findViewById(R.id.mantr_btnRegistrar)

        btnRegRopa.setOnClickListener{
            var MantRopaScreen = Intent(this, RegistroRopa::class.java)
            startActivity(MantRopaScreen)
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

        RopaRecycler.layoutManager = LinearLayoutManager(this)

        val db = BDHelperProducto(this, null)
        val cursor = db.ListarTodosRegistros()

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(BDHelperProducto.COLUMN_NOMBRE))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(BDHelperProducto.COLUMN_DESCRIPCION))
                val precio = cursor.getString(cursor.getColumnIndexOrThrow(BDHelperProducto.COLUMN_PRECIO))
                val imagenPath = cursor.getString(cursor.getColumnIndexOrThrow(BDHelperProducto.COLUMN_IMAGEN))
                val imagenUri = Uri.parse(imagenPath)

                data.add(ItemViewModel(imagenUri.toString(), nombre, descripcion, precio))


            }
            cursor.close()
        }

        adapter = CustomAdapter(data)  // Inicializa el adaptador antes de utilizarlo
        RopaRecycler.adapter = adapter

        adapter.notifyDataSetChanged()  // Notifica que los datos han cambiado después de inicializar el adaptador
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