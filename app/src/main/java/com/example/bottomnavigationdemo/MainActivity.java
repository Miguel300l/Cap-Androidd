package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bottomnavigationdemo.adapter.EventosAdapter;
import com.example.bottomnavigationdemo.databinding.ActivityMainBinding;
import com.example.bottomnavigationdemo.model.Eventos;
import com.example.bottomnavigationdemo.network.ApiAprendiz;
import com.example.bottomnavigationdemo.network.ApiEventos;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity {


    private List<Eventos> verEventosCronograma;
    private RecyclerView recyclerView;
    private EventosAdapter eventosAdapter;


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new EventosFragment());

        recyclerView = findViewById(R.id.rv_Eventos);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        showverEventos();



        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.eventos:
                    replaceFragment(new EventosFragment());
                    break;
                case R.id.psicologoss:
                    replaceFragment(new Psicologos());
                    break;
                case R.id.charla:
                    replaceFragment(new Fragment_charlass());
                    break;
                case R.id.pqrs:
                    replaceFragment(new SugerenciasFragment());
                    break;

                case R.id.config_generall:
                    replaceFragment(new Config_general());
                    break;
                case R.id.configuraciones:
                    replaceFragment(new Configuraciones());
                    break;

                case R.id.enfermeros:
                    replaceFragment(new Fragment_Enfermeros());
                    break;

                case R.id.vwConfigPerfil:
                    replaceFragment(new ConfigPerfil());
                    break;

            }

            return true;
        });

    }


    public void showverEventos(){
        Call<List<Eventos>> call= ApiAprendiz.getAprendiz().create(ApiEventos.class).getverEventosCronograma();
        call.enqueue(new Callback<List<Eventos>>() {
            @Override
            public void onResponse(Call<List<Eventos>> call, Response<List<Eventos>> response) {
                if (response.isSuccessful()){
                    verEventosCronograma = response.body();



                    Toast.makeText(MainActivity.this, ""+response.body(), Toast.LENGTH_LONG).show();
                    eventosAdapter = new EventosAdapter(verEventosCronograma, getApplicationContext());
                    recyclerView.setAdapter(eventosAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Eventos>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }



}