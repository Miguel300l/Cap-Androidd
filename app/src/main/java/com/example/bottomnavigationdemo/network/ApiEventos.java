package com.example.bottomnavigationdemo.network;
import com.example.bottomnavigationdemo.model.Eventos;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface ApiEventos {
        @GET("verEventos")
        Call<List<Eventos>> getverEventosCronograma();
}
