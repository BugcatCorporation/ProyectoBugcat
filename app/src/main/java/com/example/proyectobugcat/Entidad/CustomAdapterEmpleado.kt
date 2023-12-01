package com.example.proyectobugcat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.proyectobugcat.Entidad.Empleado
import com.example.proyectobugcat.Entidad.Producto
import com.squareup.picasso.Picasso

class CustomAdapterEmpleado(private val context: Context, private var empleado: List<Empleado>) : BaseAdapter() {

    override fun getCount(): Int {
        return empleado.size
    }
    override fun getItem(position: Int): Any {
        return empleado[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_bugcat_empleado, parent, false)

        val nombreTextView: TextView = view.findViewById(R.id.nombre_empleado)
        val descripcionTextView: TextView = view.findViewById(R.id.cargo_empleado)
        val imagenImageView: ImageView = view.findViewById(R.id.img_empleado)

        // Modificación aquí para manejar txt_id_producto
        val idTextView: TextView? = view.findViewById(R.id.txt_id_empleado)
        val empleado = getItem(position) as Empleado
        nombreTextView.text = empleado.nombre
        descripcionTextView.text = empleado.cargo
        idTextView?.text = "ID: ${empleado.id}"
        Picasso.get().load(empleado.imagen).into(imagenImageView)

        view.setOnClickListener {
            val intent = Intent(context, EditarEmpleado::class.java)
            intent.putExtra("empleado", empleado)
            intent.putExtra("empleadoId", empleado.id)
            context.startActivity(intent)
        }
        return view
    }
    fun actualizarEmpleados(nuevaLista: List<Empleado>) {
        empleado = nuevaLista
        notifyDataSetChanged()
    }
}
