package com.example.bottomnavigationdemo.service;

import com.example.bottomnavigationdemo.entity.UserCharla;
import com.example.bottomnavigationdemo.model.charla.DataRequestModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ServiceCharla {

    @GET("verUsuariosProfesionales")
    Call<List<UserCharla>> listaProfesionales();


    /*NEW METHOD POST TO CHARLA*/
    @POST("crearSolicitud")
    @Headers("Content-Type: application/json")
    Call<String> createNewRequest(@Body DataRequestModel dataRequest, @Header("acceso-token") String token);
}
