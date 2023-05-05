package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
                    replaceFragment(new SubscriptionFragment());
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