package com.example.proyectobugcat.SQLite

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectobugcat.R
import com.bumptech.glide.Glide

class CustomAdapter (private val  mList: List<ItemViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView){

        val imageView: ImageView = itemView.findViewById(R.id.img_producto)
        val tituloPrincipal: TextView = itemView.findViewById(R.id.nombre_producto)
        val descripcion : TextView = itemView.findViewById(R.id.descripcion_producto)
        val precio : TextView = itemView.findViewById(R.id.precio_producto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_productos,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemViewModel = mList[position]

        // Utiliza Glide para cargar la imagen desde la ruta directa
        Glide.with(holder.itemView.context)
            .load(itemViewModel.image)  // Utiliza la ruta directa, no un URI
            .into(holder.imageView)

        holder.tituloPrincipal.text = itemViewModel.titulo
        holder.descripcion.text = itemViewModel.descripcion
        holder.precio.text = itemViewModel.precio
    }


}