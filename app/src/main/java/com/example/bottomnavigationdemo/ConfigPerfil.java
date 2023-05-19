package com.example.bottomnavigationdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class ConfigPerfil extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_perfil, container, false);

        // Obtiene una referencia del botón en el layout
        ImageButton ImgEdit = (ImageButton) view.findViewById(R.id.ImgEdit);
        ImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea una instancia del nuevo fragmento
                Config_general config_general = new Config_general();

                // Obtiene una instancia del FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Reemplaza el fragmento actual con el nuevo fragmento
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, config_general)
                        .commit();
            }
        });

        return view;
    }
}