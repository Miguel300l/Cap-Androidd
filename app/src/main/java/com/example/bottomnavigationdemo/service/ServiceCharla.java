package com.example.bottomnavigationdemo.service;

import com.example.bottomnavigationdemo.entity.UserCharla;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceCharla {

    @GET("verUsuariosProfesionales")
    public abstract Call<List<UserCharla>> listaProfesionales();

}
