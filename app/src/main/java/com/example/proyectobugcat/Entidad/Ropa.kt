package com.example.proyectobugcat.Entidad

import java.io.Serializable

data class Ropa(
    var id: String = "",
    var descripcion: String = "",
    var imagen: String = "",
    var nombre: String = "",
    var precio: Double,
    var tallas: String =""


)  : Serializable{

    constructor(imagen: String, nombre: String, precio: Double) : this("", "", imagen, nombre, precio, "")

    constructor(imagen: String, nombre: String, precio: Double, descripcion: String) : this("", descripcion, imagen, nombre, precio, "")

    constructor(imagen: String, nombre: String, precio: Double, descripcion: String, tallas: String) : this("", descripcion, imagen, nombre, precio, tallas)
}


