package com.example.bottomnavigationdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Obtiene una referencia del botón en el layout
        Button btn = (Button) view.findViewById(R.id.registro);

        // Agrega un escuchador de clics al botón
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea una instancia del nuevo fragmento
               Registro registro2 = new Registro();

                // Obtiene una instancia del FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Reemplaza el fragmento actual con el nuevo fragmento
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, registro2)
                        .commit();
            }
        });

        return view;
    }
}
