package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigationdemo.adapter.EventosAdapter;
import com.example.bottomnavigationdemo.databinding.ActivityMainBinding;
import com.example.bottomnavigationdemo.model.Eventos;
import com.example.bottomnavigationdemo.network.ApiAprendiz;
import com.example.bottomnavigationdemo.network.ApiEventos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    private FloatingActionButton floatingActionButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new EventosFragment());



        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.eventos:
                    replaceFragment(new EventosFragment());
                    break;
                case R.id.psicologoss:
                    replaceFragment(new Profesionales());
                    break;
                case R.id.charla:
                    replaceFragment(new Charla());
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
                        // Acción para la opción
                        mostrarNotificacionesPro();
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
    private void mostrarNotificaciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.notificaciones_visuales, null);

        LinearLayout notificacionesLayout = view.findViewById(R.id.notificaciones_layout);
        ScrollView scrollView = view.findViewById(R.id.scroll_view);

        // Obtener el ID de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");

        // Ejecuta la tarea asincrónica para realizar la solicitud GET
        new HttpGetTask(notificacionesLayout).execute("https://backend-cap-273v.vercel.app/notificaciones/" + id);

        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class HttpGetTask extends AsyncTask<String, Void, String> {
        private LinearLayout notificacionesLayout;

        public HttpGetTask(LinearLayout notificacionesLayout) {
            this.notificacionesLayout = notificacionesLayout;
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Lee la respuesta de la solicitud
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            // Muestra la respuesta en el LinearLayout
            convertirAResultado(response);
        }

        private void convertirAResultado(String response) {
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String titulo = jsonObject.getString("titulo");
                    String contenido = jsonObject.getString("contenido");

                    // Crear TextViews para el título y el contenido
                    TextView tituloTextView = new TextView(MainActivity.this);
                    tituloTextView.setText(titulo);
                    tituloTextView.setTextColor(getResources().getColor(R.color.black));
                    tituloTextView.setTextSize(20);

                    TextView contenidoTextView = new TextView(MainActivity.this);
                    contenidoTextView.setText(contenido);
                    contenidoTextView.setTextSize(20);

                    // Agregar los TextViews al LinearLayout
                    notificacionesLayout.addView(tituloTextView);
                    notificacionesLayout.addView(contenidoTextView);

                    // Agregar separador entre cada par título-contenido
                    View separator = new View(MainActivity.this);
                    separator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
                    separator.setBackgroundColor(getResources().getColor(R.color.black));
                    notificacionesLayout.addView(separator);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }





    private void mostrarNotificacionesPro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.notificaciones_visuales, null);

        LinearLayout notificacionesLayout = view.findViewById(R.id.notificaciones_layout);

        // Obtener el ID de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String idProfesional = sharedPreferences.getString("idProfesional", "");

        // Ejecuta la tarea asincrónica para realizar la solicitud GET
        new HttpGetTaskPro(notificacionesLayout).execute("https://backend-cap-273v.vercel.app/notificaciones/" + idProfesional);

        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class HttpGetTaskPro extends AsyncTask<String, Void, String> {
        private LinearLayout notificacionesLayout;

        public HttpGetTaskPro(LinearLayout notificacionesLayout) {
            this.notificacionesLayout = notificacionesLayout;
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Lee la respuesta de la solicitud
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            // Muestra la respuesta en el LinearLayout
            convertirAResultado(response);
        }

        private void convertirAResultado(String response) {
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String titulo = jsonObject.getString("titulo");
                    String contenido = jsonObject.getString("contenido");

                    // Crear TextViews para el título y el contenido
                    TextView tituloTextView = new TextView(MainActivity.this);
                    tituloTextView.setText(titulo);
                    tituloTextView.setTextColor(getResources().getColor(R.color.black));
                    tituloTextView.setTextSize(20);

                    TextView contenidoTextView = new TextView(MainActivity.this);
                    contenidoTextView.setText(contenido);
                    contenidoTextView.setTextSize(20);

                    // Agregar los TextViews al LinearLayout
                    notificacionesLayout.addView(tituloTextView);
                    notificacionesLayout.addView(contenidoTextView);

                    // Agregar separador entre cada par título-contenido
                    View separator = new View(MainActivity.this);
                    separator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
                    separator.setBackgroundColor(getResources().getColor(R.color.black));
                    notificacionesLayout.addView(separator);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




    public class Notificacioness {
        private String contenido;
        private String titulo;

        public Notificacioness(String contenido, String titulo) {
            this.contenido = contenido;
            this.titulo = titulo;
        }

        public String getContenido() {
            return contenido;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }
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