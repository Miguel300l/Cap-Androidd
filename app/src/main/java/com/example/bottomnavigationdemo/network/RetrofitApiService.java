package com.example.bottomnavigationdemo.network;

import com.example.bottomnavigationdemo.model.Perfil_Aprendiz;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApiService {
    @GET("usuario/{id}")
    Call<Perfil_Aprendiz> getArticuloId(@Path("id") String id);

}
