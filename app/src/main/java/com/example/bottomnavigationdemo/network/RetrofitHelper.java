package com.example.bottomnavigationdemo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static final String BASE_URL = "https://backend-cap-273v.vercel.app/";

    private static final class RetrofitHolder {
        static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        return RetrofitHolder.retrofit;
    }
}
