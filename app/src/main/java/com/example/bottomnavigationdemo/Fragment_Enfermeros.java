package com.example.bottomnavigationdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Fragment_Enfermeros extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enfermeros, container, false);

        ImageView imgEnfermero1 = (ImageView) view.findViewById(R.id.imgEnfermero1);
        imgEnfermero1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilEnfermeroFragment perfilenfermero = new PerfilEnfermeroFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, perfilenfermero)
                        .commit();
            }
        });


        ImageView imgEnfermero2 = (ImageView) view.findViewById(R.id.imgEnfermero2);
        imgEnfermero2 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilEnfermeroFragment perfilenfermero = new PerfilEnfermeroFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, perfilenfermero)
                        .commit();
            }
        });

        ImageView imgEnfermero3 = (ImageView) view.findViewById(R.id.imgEnfermero3);
        imgEnfermero3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilEnfermeroFragment perfilenfermero = new PerfilEnfermeroFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, perfilenfermero)
                        .commit();
            }
        });


        ImageView imgEnfermero4 = (ImageView) view.findViewById(R.id.imgEnfermero4);
        imgEnfermero4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilEnfermeroFragment perfilenfermero = new PerfilEnfermeroFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, perfilenfermero)
                        .commit();
            }
        });


        return view;
    }
}