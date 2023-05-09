package com.example.bottomnavigationdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Fragment_Psicologos extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psicologos, container, false);

        ImageView imgPsicologo1 = (ImageView) view.findViewById(R.id.imgPsicologo1);
        imgPsicologo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilPsicologoFragment Psicologos = new PerfilPsicologoFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, Psicologos)
                        .commit();
            }
        });


        ImageView imgPsicologo2 = (ImageView) view.findViewById(R.id.imgPsicologo2);
        imgPsicologo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilPsicologoFragment Psicologos = new PerfilPsicologoFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, Psicologos)
                        .commit();
            }
        });

        ImageView imgPsicologo3 = (ImageView) view.findViewById(R.id.imgPsicologo3);
        imgPsicologo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilPsicologoFragment Psicologos = new PerfilPsicologoFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, Psicologos)
                        .commit();
            }
        });

        ImageView imgPsicologo4 = (ImageView) view.findViewById(R.id.imgPsicologo4);
        imgPsicologo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilPsicologoFragment Psicologos = new PerfilPsicologoFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, Psicologos)
                        .commit();
            }
        });


        Button btnenfermero = (Button) view.findViewById(R.id.btnenfermero);
        btnenfermero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Fragment_Enfermeros enfermeros = new Fragment_Enfermeros();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, enfermeros)
                        .commit();
            }
        });
        return view;
    }
}