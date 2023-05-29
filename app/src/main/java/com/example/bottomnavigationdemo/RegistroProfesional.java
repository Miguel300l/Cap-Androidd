package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;


public class RegistroProfesional extends AppCompatActivity {


    Button buttonregistrarPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_profesional);

        buttonregistrarPro = findViewById(R.id.buttonregistrarPro);




    }
}