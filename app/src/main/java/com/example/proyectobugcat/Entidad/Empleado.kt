package com.example.proyectobugcat.Entidad

import java.io.Serializable

data class Empleado(
    var id: String = "",
    var imagen: String="",
    var nombre: String="",
    var cargo: String = ""
): Serializable {
    constructor(imagen: String, nombre: String) : this("", imagen, nombre,"")
    constructor(imagen: String, nombre: String,cargo: String) : this("", imagen, nombre, cargo)
}

