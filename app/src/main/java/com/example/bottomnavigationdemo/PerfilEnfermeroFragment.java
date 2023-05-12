package com.example.bottomnavigationdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PerfilEnfermeroFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_enfermero, container, false);

        Button btnSolicitarcharla = (Button) view.findViewById(R.id.btnSolicitarcharla);
        btnSolicitarcharla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_charlass perfilenfermero = new Fragment_charlass();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, perfilenfermero)
                        .commit();
            }
        });

        return view;
    }

}