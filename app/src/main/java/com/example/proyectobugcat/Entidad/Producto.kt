package com.example.proyectobugcat.Entidad

import java.io.Serializable

data class Producto(
    var id: String = "",
    var imagen: String,
    var nombre: String,
    var precio: Double,
    var descripcion: String = ""
) : Serializable {

    constructor(imagen: String, nombre: String, precio: Double) : this("", imagen, nombre, precio, "")

    constructor(imagen: String, nombre: String, precio: Double, descripcion: String) : this("", imagen, nombre, precio, descripcion)

    constructor(imagen: String, nombre: String, precio: Double, descripcion: String, categoria: String) : this("", imagen, nombre, precio, descripcion)
}
