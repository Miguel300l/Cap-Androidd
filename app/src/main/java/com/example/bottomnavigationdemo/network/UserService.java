package com.example.bottomnavigationdemo.network;


import com.example.bottomnavigationdemo.model.Programa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("programas")
    public abstract Call<List<Programa>> listaProfesionales();

}
