package com.example.bottomnavigationdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bottomnavigationdemo.model.Perfil_Aprendiz;
import com.example.bottomnavigationdemo.network.RetrofitApiService;
import com.example.bottomnavigationdemo.network.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigPerfil extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    private TextView Nombre;
    private TextView Apellido;
    private TextView correo;

    private TextView numTelefono;
    private RetrofitApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_perfil, container, false);

        imageView = view.findViewById(R.id.ivUserPerfil);

        initViews(view);
        initValues();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        String idProfesional = sharedPreferences.getString("idProfesional", "");
        getPerfil(id);
        getPerfilPro(idProfesional);

        return view;
    }

    private void initViews(View view) {
        Nombre = view.findViewById(R.id.txtNombre);
        Apellido = view.findViewById(R.id.editarApellido);
        correo = view.findViewById(R.id.editarCorreo);
        numTelefono = view.findViewById(R.id.editarTelefono);
    }

    private void initValues() {
        apiService = RetrofitClient.getApiService();
    }

    private void getPerfil(String id ) {
        apiService.getArticuloId(id).enqueue(new Callback<Perfil_Aprendiz>() {
            @Override
            public void onResponse(Call<Perfil_Aprendiz> call, Response<Perfil_Aprendiz> response) {
                if (response.isSuccessful()) {
                    Perfil_Aprendiz perfil = response.body();

                    if (perfil != null) {
                        Nombre.setText(perfil.getNombres());
                        Apellido.setText(perfil.getApellidos());
                        correo.setText(perfil.getCorreo());
                        numTelefono.setText(perfil.getNumTelefono());

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<Perfil_Aprendiz> call, Throwable t) {
                Nombre.setText(t.getMessage());
                Apellido.setText(t.getMessage());
            }
        });
    }

    private void getPerfilPro(String idProfesional ) {
        apiService.getArticuloId(idProfesional).enqueue(new Callback<Perfil_Aprendiz>() {
            @Override
            public void onResponse(Call<Perfil_Aprendiz> call, Response<Perfil_Aprendiz> response) {
                if (response.isSuccessful()) {
                    Perfil_Aprendiz perfil = response.body();
                    if (perfil != null) {
                        Nombre.setText(perfil.getNombres());
                        Apellido.setText(perfil.getApellidos());
                        correo.setText(perfil.getCorreo());
                        numTelefono.setText(perfil.getNumTelefono());


                    }
                } else {
                    Nombre.setText("Error en la respuesta del servidor.");
                    Apellido.setText("Error en la respuesta del servidor.");
                }
            }

            @Override
            public void onFailure(Call<Perfil_Aprendiz> call, Throwable t) {
                Nombre.setText(t.getMessage());
                Apellido.setText(t.getMessage());
            }
        });
    }

}