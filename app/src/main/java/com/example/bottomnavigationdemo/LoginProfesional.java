package com.example.bottomnavigationdemo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

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
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class LoginProfesional extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;
    TextView TextoRePro;
    TextView TextoRePro3;
    SharedPreferences sp;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_profesional);

        linearLayout = (LinearLayout) findViewById(R.id.LoginPro);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        TextoRePro = findViewById(R.id.TextoRePro);
        TextoRePro3 = findViewById(R.id.TextoRePro3);
        loginButton = findViewById(R.id.login_button);

        sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);

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
                Intent intent = new Intent(LoginProfesional.this, Registro_Aprendiz.class);
                startActivity(intent);
            }
        });

        TextoRePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginProfesional.this, RegistroProfesional.class);
                startActivity(intent);
            }
        });
        TextoRePro3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginProfesional.this, Login_Aprendiz.class);
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
                .url("https://backend-cap-273v.vercel.app/loginProfesional")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginProfesional.this, "Error al enviar la solicitud", Toast.LENGTH_SHORT).show();
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
                                String idProfesional = extractIdFromToken(token);

                                if (idProfesional != null) {
                                    // Guardar el token y el id en SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("token", token);
                                    editor.putBoolean("estado_inicio_sesion", true);
                                    editor.putString("idProfesional", idProfesional);
                                    editor.apply();

                                    // Mostrar mensaje de éxito
                                    Snackbar snackbar = Snackbar.make(linearLayout, "Inicio de sesión exitoso", Snackbar.LENGTH_SHORT);
                                    snackbar.show();


                                    generateAndSendToken();

                                    // Redirigir a la siguiente actividad
                                    Intent intent = new Intent(LoginProfesional.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginProfesional.this, "Error al extraer el ID del token", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(LoginProfesional.this, "Error al parsear la respuesta", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar snackbar = Snackbar.make(linearLayout, "Credenciales invalidas", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }
                });
            }
        });
    }

    private String extractIdFromToken(String token) {
        String[] chunks = token.split("\\.");
        String payload = new String(android.util.Base64.decode(chunks[1], android.util.Base64.DEFAULT));
        try {
            JSONObject payloadJson = new JSONObject(payload);
            return payloadJson.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    //Token Notifiacaciones
    private void generateAndSendToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                   //     Toast.makeText(LoginProfesional.this, msg, Toast.LENGTH_SHORT).show();

                        // Envía el token al backend
                        sendTokenToServer(token);
                    }
                });
    }

    private void sendTokenToServer(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String id = sharedPreferences.getString("idProfesional", "");

       // Toast.makeText(LoginProfesional.this, "ID guardado: " + id, Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://backend-cap-273v.vercel.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        TokenRequestBody tokenRequestBody = new TokenRequestBody(token);

        apiService.sendToken(id, tokenRequestBody).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                  //  Toast.makeText(LoginProfesional.this, "Token enviado correctamente al servidor", Toast.LENGTH_SHORT).show();
                } else {
                   // Toast.makeText(LoginProfesional.this, "Error al enviar el token al servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Error en la solicitud al servidor: " + t.getMessage());
            }
        });
    }

    public interface ApiService {
        @PUT("/actualizarToken/{id}")
        retrofit2.Call<ResponseBody> sendToken(@Path("id") String id, @Body TokenRequestBody requestBody);
    }

    public class TokenRequestBody {
        private String token;

        public TokenRequestBody(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}