package com.example.proyectobugcat.SQLite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectobugcat.R

class CustomAdapter (private val  mList: List<ItemViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView){

        val imageView: ImageView = itemView.findViewById(R.id.img_producto)
        val tituloPrincipal: TextView = itemView.findViewById(R.id.nombre_producto)
        val descripcion : TextView = itemView.findViewById(R.id.descripcion_producto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_productos,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Si deseo alguna conexion podria ser aqui (BD, Servicio Web, etc)
        val itemViewModel = mList[position]

        holder.imageView.setImageResource(itemViewModel.image)
        holder.tituloPrincipal.text = itemViewModel.titulo
        holder.descripcion.text = itemViewModel.descripcion
    }
}