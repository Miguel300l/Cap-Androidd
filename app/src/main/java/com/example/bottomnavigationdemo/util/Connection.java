package com.example.bottomnavigationdemo.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {

    public static final String URL = "https://backend-cap-273v.vercel.app/";
    public static Retrofit retrofif = null;
    public static Retrofit getConnecion(){
        if (retrofif == null) {
            retrofif = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofif;
    }

}
