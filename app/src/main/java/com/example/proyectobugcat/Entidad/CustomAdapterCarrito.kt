// CustomAdapterCarrito.kt
package com.example.proyectobugcat.Entidad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectobugcat.R
import com.squareup.picasso.Picasso

class CustomAdapterCarrito(private val context: Context, private var carritos: List<Carrito>) :
    RecyclerView.Adapter<CustomAdapterCarrito.CarritoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return carritos.size
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val carrito = carritos[position]
        holder.bind(carrito)
    }

    // ViewHolder para mantener las referencias de las vistas
    inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.txt_nombre_producto_carrito)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.txt_descripcion_producto_carrito)
        private val precioTextView: TextView = itemView.findViewById(R.id.txt_precio_producto_carrito)
        private val imagenImageView: ImageView = itemView.findViewById(R.id.img_imagen_producto_carrito)

        fun bind(carrito: Carrito) {
            nombreTextView.text = carrito.nombre
            descripcionTextView.text = carrito.descripcion
            precioTextView.text = "S/. ${carrito.precio}"

            // Cargar la imagen utilizando Picasso
            Picasso.get().load(carrito.imagen).into(imagenImageView)
        }
    }

    // Método para actualizar la lista de carritos
    fun actualizarCarritos(nuevaLista: List<Carrito>) {
        carritos = nuevaLista
        notifyDataSetChanged()
    }


}
