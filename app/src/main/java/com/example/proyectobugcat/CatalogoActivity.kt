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
import com.google.android.material.bottomnavigation.BottomNavigationView

class CatalogoActivity : AppCompatActivity() {
    lateinit var navegacion: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        navegacion = findViewById(R.id.bottomNavigation)
        navegacion.setOnNavigationItemSelectedListener { item->

            when(item.itemId){
                R.id.btnlsArticulos -> {
                    startActivity(Intent(this,RegistroActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }

                /*R.id.itemFragment2 -> {
                    supportFragmentManager.commit {
                        replace<CarritoFragment>(R.id.fragmentContainer)
                        setReorderingAllowed(true)
                        addToBackStack("replacement")
                    }
                    return@setOnNavigationItemSelectedListener true
                }*/

                R.id.btnlsCarrito -> {
                    startActivity(Intent(this,CarritoActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false
        }

        /*val catalogoFragment = CatalogoFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, catalogoFragment)
            .addToBackStack(null)
            .commit()*/
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