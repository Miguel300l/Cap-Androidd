package com.example.bottomnavigationdemo.info

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {
    @GET("usuarios/id") // Asegúrate de reemplazar esto con la ruta correcta de tu API en Vercel
    fun obtenerIdUsuario(@Path("id") id: String): Call<UserResponse>
}
