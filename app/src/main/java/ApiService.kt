package com.example.vistasistemas

// ApiService.kt
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("usuario")
    fun obtenerUsuarios(): Call<List<Usuario>>

    @POST("usuario")
    fun crearUsuario(@Body usuario: Usuario): Call<Usuario>
}
