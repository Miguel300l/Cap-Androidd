package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigationdemo.adapter.EventosAdapter;
import com.example.bottomnavigationdemo.databinding.ActivityMainBinding;
import com.example.bottomnavigationdemo.model.Eventos;
import com.example.bottomnavigationdemo.network.ApiAprendiz;
import com.example.bottomnavigationdemo.network.ApiEventos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Eventos> verEventosCronograma;
    private RecyclerView recyclerView;
    private EventosAdapter eventosAdapter;

    ActivityMainBinding binding;

    private FloatingActionButton floatingActionButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new EventosFragment());

        recyclerView = findViewById(R.id.rv_Eventos);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
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
                    replaceFragment(new Charla());
                    break;

                case R.id.enfermeros:
                    replaceFragment(new Fragment_Enfermeros());
                    break;
                case R.id.configPerfil:
                    replaceFragment(new ConfigPerfil());
                    break;
            }
            item.setChecked(true); // Set the selected item as checked
            return true;
        });

        floatingActionButton = findViewById(R.id.flotantRRR);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsMenu();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void showverEventos() {
        Call<List<Eventos>> call = ApiAprendiz.getAprendiz().create(ApiEventos.class).getverEventosCronograma();
        call.enqueue(new Callback<List<Eventos>>() {
            @Override
            public void onResponse(Call<List<Eventos>> call, Response<List<Eventos>> response) {
                if (response.isSuccessful()) {
                    verEventosCronograma = response.body();
                    Toast.makeText(MainActivity.this, "" + response.body(), Toast.LENGTH_LONG).show();
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

    private void showOptionsMenu() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, floatingActionButton);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.options_menu, popupMenu.getMenu());

        // Agregar acciones a las opciones del menú
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.option1:
                        // Acción para la opción 1
                        mostrarNotificaciones();
                        return true;
                    case R.id.option2:
                        // Acción para la opción 2
                        replaceFragment(new ConfigPerfil()); // Reemplazar con ConfigPerfil fragment
                        return true;
                    case R.id.option3:
                        // Acción para la opción 3
                        closeSession(); // Cerrar sesión
                        return true;
                    default:
                        return false;
                }
            }
        });

        // Mostrar el menú
        popupMenu.show();
    }

    private void mostrarNotificaciones(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.notificaciones_visuales, null);

        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void closeSession() {
        // Limpiar SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirigir al login
        Intent intent = new Intent(MainActivity.this, Login_Aprendiz.class);
        startActivity(intent);
        finish();
    }
}