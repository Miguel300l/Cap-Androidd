package com.example.bottomnavigationdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bottomnavigationdemo.databinding.ActivityMainBinding;


public class Login extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = (Button) view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventosFragment eventoo = new EventosFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, eventoo)
                        .commit();
            }
        });

        return view;
    }
}