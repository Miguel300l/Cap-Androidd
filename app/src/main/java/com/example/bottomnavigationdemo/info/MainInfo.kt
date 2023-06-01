package com.example.bottomnavigationdemo.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomnavigationdemo.databinding.FragmentConfigPerfilBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainInfo : AppCompatActivity() {

    private lateinit var binding: FragmentConfigPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConfigPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun obtenerIdUsuario(token: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tu-api-vercel.com/") // Asegúrate de reemplazar esto con la URL base de tu API en Vercel
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
    }}
//        val call = apiService.obtenerIdUsuario()
//        call.enqueue(object : Callback<UserResponse> {
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                if (response.isSuccessful) {
//                    val user = response.body()
//                    val userId = user?.id
//                    val nombres = user?.nombres
//                    binding.txtNameUser.text = nombres
//
//                    // Aquí puedes utilizar el ID del usuario para realizar otras operaciones o almacenarlo en tu aplicación
//
//                    // Haz algo con el ID del usuario obtenido
//                } else {
//                    // Maneja la respuesta no exitosa de la solicitud
//                }
//            }
//
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                t.printStackTrace()
//                // Maneja el error de la solicitud
//            }
//        })
//
//    }
