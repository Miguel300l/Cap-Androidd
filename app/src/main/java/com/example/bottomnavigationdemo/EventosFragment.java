package com.example.bottomnavigationdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class EventosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        Button btneventoo = (Button) view.findViewById(R.id.btneventoo);
        btneventoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_charlass Eventos_Charlas = new Fragment_charlass();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, Eventos_Charlas)
                        .commit();
            }
        });

        return view;
    }
}