package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login2 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        //login usuario
        Button btnLoginUsuario = findViewById(R.id.loginButton2);

        btnLoginUsuario.setOnClickListener(new View.OnClickListener() {
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

        //aqui ingresamos la funcionalidad del text
        TextView tvregistrar = findViewById(R.id.textRegistrarD);
        tvregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current activity with the Registro fragment
                Fragment registroFragment = new Registro();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(android.R.id.content, registroFragment)
                        .commit();
            }
        });
        //aqui termina la funcionalidad del text
    }







}
