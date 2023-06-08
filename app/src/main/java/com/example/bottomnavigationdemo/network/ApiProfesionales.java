package com.example.bottomnavigationdemo.network;
import com.example.bottomnavigationdemo.model.Profesionales;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiProfesionales {
        @GET("verUsuariosProfesionales")
        Call<List<Profesionales>> getverUsuariosProfesionales();

}
