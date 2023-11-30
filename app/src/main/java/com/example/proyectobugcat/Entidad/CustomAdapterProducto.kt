import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectobugcat.Entidad.Producto
import com.example.proyectobugcat.R
import com.squareup.picasso.Picasso

class CustomAdapterProducto(private val context: Context, private var productos: List<Producto>) :
    RecyclerView.Adapter<CustomAdapterProducto.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_productos, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    fun actualizarProductos(nuevaLista: List<Producto>) {
        productos = nuevaLista
        notifyDataSetChanged()
    }

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.nombre_producto)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.descripcion_producto)
        private val precioTextView: TextView = itemView.findViewById(R.id.precio_producto)
        private val imagenImageView: ImageView = itemView.findViewById(R.id.img_producto)

        fun bind(producto: Producto) {
            nombreTextView.text = producto.nombre
            descripcionTextView.text = producto.descripcion
            precioTextView.text = "S/.${producto.precio}"

            Picasso.get().load(producto.imagen).into(imagenImageView)
        }
    }
}
