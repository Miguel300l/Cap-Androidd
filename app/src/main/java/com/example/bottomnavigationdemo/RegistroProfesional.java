package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class RegistroProfesional extends AppCompatActivity {

    EditText signupPassword, signupNombres, signupApellidos,
            signupNumeroDocumento, signupCorreo, signupNumTelefono, signupProfesion;

    Spinner signupTipo, signupGenero, signupRol;
    TextView loginRedirectText;
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_profesional);

        signupPassword = findViewById(R.id.signup_passwordPro);
        signupNombres = findViewById(R.id.signup_nombresPro);
        signupApellidos = findViewById(R.id.signup_apellidosPro);
        signupNumeroDocumento = findViewById(R.id.signup_numero_documento);
        signupCorreo = findViewById(R.id.signup_correo);
        signupNumTelefono = findViewById(R.id.signup_num_telefono);
        signupButton = findViewById(R.id.buttonregistrarPro);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupTipo = findViewById(R.id.signup_tipo);
        signupGenero = findViewById(R.id.signup_genero);
        signupRol = findViewById(R.id.signupRol);
        signupProfesion = findViewById(R.id.signupProfesion);

        // Configurar los adaptadores para los spinners
        ArrayAdapter<CharSequence> tipoAdapter = ArrayAdapter.createFromResource(this, R.array.tipos_array, android.R.layout.simple_spinner_item);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupTipo.setAdapter(tipoAdapter);

        ArrayAdapter<CharSequence> generoAdapter = ArrayAdapter.createFromResource(this, R.array.generos_array, android.R.layout.simple_spinner_item);
        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupGenero.setAdapter(generoAdapter);

        ArrayAdapter<CharSequence> rolAdapter = ArrayAdapter.createFromResource(this, R.array.profesion_array, android.R.layout.simple_spinner_item);
        rolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupRol.setAdapter(rolAdapter);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValidEmail(signupCorreo.getText().toString())) {
                    Toast.makeText(RegistroProfesional.this, "Correo inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidPassword(signupPassword.getText().toString())) {
                    Toast.makeText(RegistroProfesional.this, "Contraseña inválida. Debe contener al menos una letra, un número y un carácter especial", Toast.LENGTH_SHORT).show();
                    return;
                }

                String contrasena = signupPassword.getText().toString();
                String nombres = signupNombres.getText().toString();
                String apellidos = signupApellidos.getText().toString();
                String tipo = signupTipo.getSelectedItem().toString();
                String numeroDocumento = signupNumeroDocumento.getText().toString();
                String genero = signupGenero.getSelectedItem().toString();
                String correo = signupCorreo.getText().toString();
                String profesion = signupProfesion.getText().toString();
                String numTelefono = signupNumTelefono.getText().toString();
                String rol = signupRol.getSelectedItem().toString();

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
                        .addFormDataPart("rol", rol)
                        .addFormDataPart("profesion", profesion)
                        .build();

                // Crea la solicitud POST a la URL de Vercel
                Request request = new Request.Builder()
                        .url("https://backend-cap-273v.vercel.app/registrarProfesional")
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
                                Toast.makeText(RegistroProfesional.this, "Error al enviar la solicitud", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(RegistroProfesional.this, "Te has registrado correctamente", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistroProfesional.this, com.example.bottomnavigationdemo.LoginProfesional.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegistroProfesional.this, "Error al registrar: " + responseBody, Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(RegistroProfesional.this, com.example.bottomnavigationdemo.LoginProfesional.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Verifica que la contraseña tenga al menos una letra, un número y un carácter especial
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@.#$%^&+=]).{8,}$";
        return password.matches(passwordPattern);
    }
}