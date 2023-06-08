package com.example.bottomnavigationdemo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bottomnavigationdemo.adapter.ProfesionalesAdapter;
import com.example.bottomnavigationdemo.network.ApiAprendiz;
import com.example.bottomnavigationdemo.network.ApiProfesionales;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profesionales extends Fragment {

    private RecyclerView rvProfesionales;
    private ProfesionalesAdapter profesionalesAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profesionales, container, false);

        rvProfesionales = view.findViewById(R.id.rv_profesionales);
        rvProfesionales.setLayoutManager(new GridLayoutManager(getContext(), 1));
        showProfesionales();
        return view;
    }


    public void showProfesionales() {
        Call<List<com.example.bottomnavigationdemo.model.Profesionales>> call = ApiAprendiz.getAprendiz().create(ApiProfesionales.class).getverUsuariosProfesionales();
        call.enqueue(new Callback<List<com.example.bottomnavigationdemo.model.Profesionales>>() {
            @Override
            public void onResponse(Call<List<com.example.bottomnavigationdemo.model.Profesionales>> call, Response<List<com.example.bottomnavigationdemo.model.Profesionales>> response) {
                if (response.isSuccessful()) {
                    List<com.example.bottomnavigationdemo.model.Profesionales> profesionalesList = response.body();
                    profesionalesAdapter = new ProfesionalesAdapter(profesionalesList, getContext());
                    rvProfesionales.setAdapter(profesionalesAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<com.example.bottomnavigationdemo.model.Profesionales>> call, Throwable t) {
                Toast.makeText(getActivity(), "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
