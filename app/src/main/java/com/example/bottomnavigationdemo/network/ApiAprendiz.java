package com.example.bottomnavigationdemo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAprendiz {
    private static Retrofit retrofit;
    public static Retrofit getAprendiz(){
        retrofit =new Retrofit.Builder()
                .baseUrl("https://backend-cap-273v.vercel.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
