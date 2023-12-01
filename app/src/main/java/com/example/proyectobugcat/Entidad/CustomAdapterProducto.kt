package com.example.proyectobugcat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.proyectobugcat.Entidad.Producto
import com.squareup.picasso.Picasso

class CustomAdapterProducto(private val context: Context, private var productos: List<Producto>) : BaseAdapter() {

    override fun getCount(): Int {
        return productos.size
    }

    override fun getItem(position: Int): Any {
        return productos[position]
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

        // Modificación aquí para manejar txt_id_producto
        val idTextView: TextView? = view.findViewById(R.id.txt_id_producto)

        val producto = getItem(position) as Producto

        nombreTextView.text = producto.nombre
        descripcionTextView.text = producto.descripcion
        precioTextView.text = "S/.${producto.precio}"
        idTextView?.text = "ID: ${producto.id}"

        Picasso.get().load(producto.imagen).into(imagenImageView)

        view.setOnClickListener {
            val intent = Intent(context, EditarProducto::class.java)
            intent.putExtra("producto", producto)
            intent.putExtra("productoId", producto.id) // Pasa la ID
            context.startActivity(intent)
        }

        return view
    }


    fun actualizarProductos(nuevaLista: List<Producto>) {
        productos = nuevaLista
        notifyDataSetChanged()
    }


}
