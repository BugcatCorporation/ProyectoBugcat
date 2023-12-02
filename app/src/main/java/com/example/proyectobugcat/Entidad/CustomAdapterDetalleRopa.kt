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
import com.example.proyectobugcat.Entidad.Ropa
import com.example.proyectobugcat.R
import com.squareup.picasso.Picasso

class CustomAdapterDetalleRopa(private val context: Context, private var ropas: List<Ropa>) : BaseAdapter() {

    override fun getCount(): Int {
        return ropas.size
    }

    override fun getItem(position: Int): Any {
        return ropas[position]
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

        val ropa = getItem(position) as Ropa

        nombreTextView.text = ropa.nombre
        descripcionTextView.text = ropa.descripcion
        precioTextView.text = "S/.${ropa.precio}"

        // Puedes mantener el código de Picasso para cargar la imagen
        Picasso.get().load(ropa.imagen).into(imagenImageView)

        view.setOnClickListener {
            val intent = Intent(context, DetalleRopaActivity::class.java)
            intent.putExtra("ropa", ropa)
            intent.putExtra("ropaId", ropa.id)
            context.startActivity(intent)
        }

        return view
    }

    fun actualizarRopas(nuevaLista: List<Ropa>) {
        ropas = nuevaLista
        notifyDataSetChanged()
    }
}
