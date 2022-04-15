package com.heltonbustos.retrofit3peliculas.retrofit

import retrofit2.Call
import retrofit2.http.*

interface PeliculaAPIService {

    @GET("{json}")
    fun obtenerPeliculas(@Path("json") json: String): Call<Pelicula>

    @PUT("bd/{item}.json")
    fun agregarPelicula(@Path("item") item: Int, @Body pelicula: PeliculaItem): Call<PeliculaItem>

}