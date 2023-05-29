package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registro extends AppCompatActivity {

    EditText  signupPassword, signupNombres, signupApellidos,
            signupTipo, signupNumeroDocumento, signupGenero, signupCorreo, signupNumTelefono,
            signupProfesion;
    TextView loginRedirectText;
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registro);

        signupPassword = findViewById(R.id.signup_password);
        signupNombres = findViewById(R.id.signup_nombres);
        signupApellidos = findViewById(R.id.signup_apellidos);
        signupTipo = findViewById(R.id.signup_tipo);
        signupNumeroDocumento = findViewById(R.id.signup_numero_documento);
        signupGenero = findViewById(R.id.signup_genero);
        signupCorreo = findViewById(R.id.signup_correo);
        signupNumTelefono = findViewById(R.id.signup_num_telefono);
        signupProfesion = findViewById(R.id.signup_profesion);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contrasena = signupPassword.getText().toString();
                String nombres = signupNombres.getText().toString();
                String apellidos = signupApellidos.getText().toString();
                String tipo = signupTipo.getText().toString();
                String numeroDocumento = signupNumeroDocumento.getText().toString();
                String genero = signupGenero.getText().toString();
                String correo = signupCorreo.getText().toString();
                String numTelefono = signupNumTelefono.getText().toString();
                String programa = signupProfesion.getText().toString();

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
}