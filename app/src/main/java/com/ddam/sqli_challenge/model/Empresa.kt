package com.ddam.sqli_challenge.model

data class Empresa(
    val id: Int,
    val nombre: String,
    val url: String,
    val telefono: String,
    val email: String,
    val productosServicios: String,
    val clasificacion: String
)