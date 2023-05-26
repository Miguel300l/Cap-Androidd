package com.example.bottomnavigationdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ConfiguracionFlotante extends Fragment {

    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracion_flotante, container, false);

        // Obtener referencia al botón flotante
        floatingActionButton = view.findViewById(R.id.flotantRRR);

        // Agregar opciones al botón flotante
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsMenu();
            }
        });

        return view;
    }

    private void showOptionsMenu() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), floatingActionButton);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.options_menu, popupMenu.getMenu());

        // Agregar acciones a las opciones del menú
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.option1:
                        // Acción para la opción 1
                        return true;
                    case R.id.option2:
                        // Acción para la opción 2
                        return true;
                    case R.id.option3:
                        // Acción para la opción 3
                        return true;
                    default:
                        return false;
                }
            }
        });

        // Mostrar el menú
        popupMenu.show();
    }
}
