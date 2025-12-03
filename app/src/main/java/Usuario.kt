package com.example.vistasistemas

data class Usuario(
    val id: Int? = null,
    val nombre: String,
    val email: String,
    val puesto: String,
    val rol: String,
    val password: String? = null
)
