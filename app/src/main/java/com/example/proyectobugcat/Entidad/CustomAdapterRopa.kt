package com.example.proyectobugcat.Entidad

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.proyectobugcat.DetalleRopaActivity
import com.example.proyectobugcat.EditarProducto
import com.example.proyectobugcat.EditarRopa
import com.example.proyectobugcat.R
import com.squareup.picasso.Picasso

class CustomAdapterRopa(private val context: Context, private var ropa: List<Ropa> ) : BaseAdapter () {
     override fun getCount(): Int {
         return ropa.size
     }

     override fun getItem(position: Int): Any {
         return ropa[position]
     }

     override fun getItemId(position: Int): Long {
         return position.toLong()
     }

     override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
         val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_bugcat, parent, false)

         val nombreTextView: TextView = view.findViewById(R.id.nombre_producto)
         val descripcionTextView: TextView = view.findViewById(R.id.descripcion_producto)
         val precioTextView: TextView = view.findViewById(R.id.precio_producto)
         val imagenImageView: ImageView = view.findViewById(R.id.img_producto)

         // Modificación aquí para manejar txt_id_ropa
         val idTextView: TextView? = view.findViewById(R.id.txt_id_ropa)

         val ropa = getItem(position) as Ropa

         nombreTextView.text = ropa.nombre
         descripcionTextView.text = ropa.descripcion
         precioTextView.text = "S/.${ropa.precio}"
         idTextView?.text = "ID: ${ropa.id}"

         Picasso.get().load(ropa.imagen).into(imagenImageView)

         view.setOnClickListener {
             val intent = Intent(context, DetalleRopaActivity::class.java)
             intent.putExtra("ropa", ropa)
             intent.putExtra("ropaId", ropa.id) // Pasa la ID
             context.startActivity(intent)
         }

         return view
     }


     fun actualizarProductos(nuevaLista: List<Ropa>) {
         ropa = nuevaLista
         notifyDataSetChanged()
     }


 }
