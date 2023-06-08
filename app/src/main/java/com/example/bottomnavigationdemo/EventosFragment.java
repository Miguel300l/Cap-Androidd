package com.example.bottomnavigationdemo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.bottomnavigationdemo.adapter.EventosAdapter;
import com.example.bottomnavigationdemo.model.Eventos;
import com.example.bottomnavigationdemo.network.ApiAprendiz;
import com.example.bottomnavigationdemo.network.ApiEventos;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventosFragment extends Fragment {


    private List<Eventos> verEventosCronograma;
    RecyclerView rvEventos;

    private EventosAdapter eventosAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        rvEventos = view.findViewById(R.id.rv_Eventos);
        rvEventos.setLayoutManager(new GridLayoutManager(getContext(), 1));
        showverEventos();

        return view;

    }


    public void showverEventos() {
        Call<List<Eventos>> call = ApiAprendiz.getAprendiz().create(ApiEventos.class).getverEventosCronograma();
        call.enqueue(new Callback<List<Eventos>>() {
            @Override
            public void onResponse(Call<List<Eventos>> call, Response<List<Eventos>> response) {
                if (response.isSuccessful()) {
                    verEventosCronograma = response.body();
                    eventosAdapter = new EventosAdapter(verEventosCronograma, getContext());
                    rvEventos.setAdapter(eventosAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Eventos>> call, Throwable t) {
                Toast.makeText(getActivity(), "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
