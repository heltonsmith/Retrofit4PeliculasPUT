package com.heltonbustos.retrofit3peliculas.retrofit


class Pelicula : ArrayList<PeliculaItem>()

data class PeliculaItem(
    val avaluo: String,
    val fechalanzamiento: String,
    val lugarestreno: String,
    val nombre: String
)