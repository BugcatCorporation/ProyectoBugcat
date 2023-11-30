package com.example.proyectobugcat.Entidad

data class Producto(
    var id: String = "",
    val imagen: String,
    val nombre: String,
    val precio: Double,
    val descripcion: String = ""
) {

    constructor(imagen: String, nombre: String, precio: Double) : this("", imagen, nombre, precio, "")

    constructor(imagen: String, nombre: String, precio: Double, descripcion: String) : this("", imagen, nombre, precio, descripcion)

    constructor(imagen: String, nombre: String, precio: Double, descripcion: String, categoria: String) : this("", imagen, nombre, precio, descripcion)
}