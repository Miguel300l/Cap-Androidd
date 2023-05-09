package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bottomnavigationdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.inicio:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.eventos:
                    replaceFragment(new Eventos());
                    break;
                case R.id.charla:
                    replaceFragment(new Fragment_Psicologos());
                    break;
                case R.id.pqrs:
                    replaceFragment(new LibraryFragment());
                    break;
                case R.id.registro2:
                    replaceFragment(new Registro());
                    break;
                case R.id.login_library:
                    replaceFragment(new Login());
                    break;
                case R.id.config_general:
                    replaceFragment(new Configuracion_General());
                    break;
                case R.id.configuraciones:
                    replaceFragment(new Configuraciones());
                    break;
                case R.id.Psicologos:
                    replaceFragment(new PerfilPsicologoFragment());
                    break;
                case R.id.enfermeros:
                    replaceFragment(new Fragment_Enfermeros());
                    break;

            }

            return true;
        });



    }
    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }



}