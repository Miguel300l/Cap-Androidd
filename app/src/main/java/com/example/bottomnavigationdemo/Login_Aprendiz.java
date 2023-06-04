package com.example.bottomnavigationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login_Aprendiz extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;
    TextView TextoRePro;
    TextView TextoRePro2;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_aprendiz);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        TextoRePro = findViewById(R.id.TextoRePro);
        TextoRePro2 = findViewById(R.id.TextoRePro2);
        loginButton = findViewById(R.id.login_button);

        sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Verificar si el inicio de sesión ya está activo
        boolean estadoInicioSesion = sp.getBoolean("estado_inicio_sesion", false);
        if (estadoInicioSesion) {
            // Redirigir a la actividad principal
            Intent intent = new Intent(Login_Aprendiz.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()){
                    // Validación fallida
                } else {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Aprendiz.this, Registro_Aprendiz.class);
                startActivity(intent);
            }
        });

        TextoRePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Aprendiz.this, RegistroProfesional.class);
                startActivity(intent);
            }
        });

        TextoRePro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Aprendiz.this, LoginProfesional.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateUsername(){
        String val = loginUsername.getText().toString();
        if (val.isEmpty()){
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()){
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String correo = loginUsername.getText().toString().trim();
        String contrasena = loginPassword.getText().toString().trim();

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("correo", correo)
                .add("contrasena", contrasena)
                .build();

        Request request = new Request.Builder()
                .url("https://backend-cap-273v.vercel.app/loginAprendiz")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Login_Aprendiz.this, "Error al enviar la solicitud", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject responseJson = new JSONObject(responseBody);
                                String token = responseJson.getString("token");

                                // Guardar el token y el estado de inicio de sesión en SharedPreferences
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("token", token);
                                editor.putString("correo", correo);
                                editor.putBoolean("estado_inicio_sesion", true);
                                editor.apply();

                                // Verificar si el token y el correo se guardaron correctamente
                                String savedToken = sp.getString("token", "");
                                String savedCorreo = sp.getString("correo", "");
                                if (!savedToken.isEmpty() && !savedCorreo.isEmpty()) {
                                    Toast.makeText(Login_Aprendiz.this, "Token guardado correctamente: " + savedToken, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(Login_Aprendiz.this, "Correo guardado correctamente: " + savedCorreo, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login_Aprendiz.this, "Error al guardar el token o el correo", Toast.LENGTH_SHORT).show();
                                }
                                // Iniciar sesión exitoso
                                Toast.makeText(Login_Aprendiz.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                // Redirigir a la siguiente actividad
                                Intent intent = new Intent(Login_Aprendiz.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Login_Aprendiz.this, "Error al parsear la respuesta", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login_Aprendiz.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}