package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigationdemo.model.Programa;
import com.example.bottomnavigationdemo.network.ApiAprendiz;
import com.example.bottomnavigationdemo.network.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registro extends AppCompatActivity {

    Spinner spnProfesionales;
    ArrayAdapter<String> adapter;
    ArrayList<String> profesionales = new ArrayList<String>();
    UserService userService;

    EditText signupPassword, signupNombres, signupApellidos,
            signupNumeroDocumento, signupCorreo, signupNumTelefono;

    Spinner signupTipo, signupGenero, signupPrograma;
    TextView loginRedirectText;
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registro);

        spnProfesionales = findViewById(R.id.signupPrograma);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, profesionales);
        spnProfesionales.setAdapter(adapter);

        userService = ApiAprendiz.getAprendiz().create(UserService.class);
        cargaData();

        signupPassword = findViewById(R.id.signup_password);
        signupNombres = findViewById(R.id.signup_nombres);
        signupApellidos = findViewById(R.id.signup_apellidos);
        signupNumeroDocumento = findViewById(R.id.signup_numero_documento);
        signupCorreo = findViewById(R.id.signup_correo);
        signupNumTelefono = findViewById(R.id.signup_num_telefono);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupTipo = findViewById(R.id.signup_tipo);
        signupGenero = findViewById(R.id.signup_genero);
        signupPrograma = findViewById(R.id.signupPrograma);


        // Configurar los adaptadores para los spinners
        ArrayAdapter<CharSequence> tipoAdapter = ArrayAdapter.createFromResource(this, R.array.tipos_array, android.R.layout.simple_spinner_item);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupTipo.setAdapter(tipoAdapter);

        ArrayAdapter<CharSequence> generoAdapter = ArrayAdapter.createFromResource(this, R.array.generos_array, android.R.layout.simple_spinner_item);
        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupGenero.setAdapter(generoAdapter);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los datos ingresados por el usuario
                String contrasena = signupPassword.getText().toString();
                String nombres = signupNombres.getText().toString();
                String apellidos = signupApellidos.getText().toString();
                String tipo = signupTipo.getSelectedItem().toString();
                String numeroDocumento = signupNumeroDocumento.getText().toString();
                String genero = signupGenero.getSelectedItem().toString();
                String correo = signupCorreo.getText().toString();
                String numTelefono = signupNumTelefono.getText().toString();
                String programa = signupPrograma.getSelectedItem().toString();

                // Verificar si el correo ingresado es válido
                if (!isValidEmail(correo)) {
                    Toast.makeText(Registro.this, "Correo inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar la longitud mínima de la contraseña y su formato
                if (contrasena.length() < 8 || !isValidPassword(contrasena)) {
                    Toast.makeText(Registro.this, "La contraseña debe tener al menos 8 caracteres y contener una combinación de letras, números y caracteres especiales", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crea el cuerpo de la solicitud POST como formulario multipart
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("contrasena", contrasena)
                        .addFormDataPart("nombres", nombres)
                        .addFormDataPart("apellidos", apellidos)
                        .addFormDataPart("tipo", tipo)
                        .addFormDataPart("numeroDocumento", numeroDocumento)
                        .addFormDataPart("genero", genero)
                        .addFormDataPart("correo", correo)
                        .addFormDataPart("numTelefono", numTelefono)
                        .addFormDataPart("programa", programa)
                        .build();

                // Crea la solicitud POST a la URL de Vercel
                Request request = new Request.Builder()
                        .url("https://backend-cap-273v.vercel.app/registrarAprendiz")
                        .post(requestBody)
                        .build();

                // Crea el cliente HTTP
                OkHttpClient client = new OkHttpClient();

                // Envía la solicitud asíncronamente
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Manejo del error en caso de fallo de la solicitud
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Registro.this, "Error al enviar la solicitud", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // Manejo de la respuesta de la solicitud
                        final String responseBody = response.body().string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (response.isSuccessful()) {
                                    Toast.makeText(Registro.this, "Te has registrado correctamente", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Registro.this, com.example.bottomnavigationdemo.Login2.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Registro.this, "Error al registrar: " + responseBody, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, com.example.bottomnavigationdemo.Login2.class);
                startActivity(intent);
            }
        });
    }


    public void cargaData(){
        retrofit2.Call<List<Programa>> call = userService.listaProfesionales();
        call.enqueue(new retrofit2.Callback<List<Programa>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Programa>> call, retrofit2.Response<List<Programa>> response) {
                mensajeToast("Acceso exitoso al servicio REST");
                if (response.isSuccessful()){
                    mensajeToast("Acceso exitoso al servicio REST");
                    List<Programa> lstProfesionales = response.body();
                    for (Programa programa:lstProfesionales){
                        String nombreCompleto = programa.getNombre() + " " + programa.getFicha();
                        profesionales.add(nombreCompleto);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    mensajeToast("Error de acceso al servicio REST");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Programa>> call, Throwable t) {
                mensajeToast("Error de acceso al servicio REST");
            }
        });
    }


    public void mensajeAlert(String titulo, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setTitle(titulo);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    void mensajeToast(String mensaje){
        Toast toast1 = Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    // Verificar si el correo es válido utilizando una expresión regular
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    // Verificar si la contraseña cumple con los requisitos mínimos utilizando una expresión regular
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@.#$%¿)><(*´}{°|~`^&+=!?¡]).*$";
        return password.matches(passwordRegex);
    }
}