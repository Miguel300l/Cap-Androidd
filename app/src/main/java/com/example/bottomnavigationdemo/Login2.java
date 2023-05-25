package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Login2 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        Button btnConfiguracion = findViewById(R.id.loginButton2);
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener referencias a los campos de texto
                EditText usernameEditText = findViewById(R.id.usernameEditText);
                EditText passwordEditText = findViewById(R.id.passwordEditText);

                // Obtener los valores ingresados en los campos de texto
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Verificar si los campos están vacíos
                if (username.isEmpty()) {
                    usernameEditText.setError("Ingrese un nombre de usuario");
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Ingrese una contraseña");
                    return;
                }

                // Si los campos no están vacíos, continuar con el inicio de sesión
                Intent intent = new Intent(Login2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



}
