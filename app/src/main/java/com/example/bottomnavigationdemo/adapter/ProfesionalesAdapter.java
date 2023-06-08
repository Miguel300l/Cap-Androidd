package com.example.bottomnavigationdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bottomnavigationdemo.R;
import com.example.bottomnavigationdemo.model.Profesionales;

import java.util.List;


public class ProfesionalesAdapter extends RecyclerView.Adapter<ProfesionalesAdapter.ViewHolder> {


    private List<Profesionales> verProfesionales;
    private Context context;

    public ProfesionalesAdapter(List<Profesionales> verProfesionales, Context context) {
        this.verProfesionales = verProfesionales;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recycler_profesionales, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_titulo.setText(verProfesionales.get(position).getNombres());

        holder.tv_profesion.setText(verProfesionales.get(position).getProfesion());
       // Glide.with(context).load(verEventos.get(position).getUrlImg()).into(holder.iv_portada)
        Glide.with(context).load(verProfesionales.get(position).getPerfil().getUrlImg()).into(holder.iv_portada);

        
    }

    @Override
    public int getItemCount() {

        return verProfesionales.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_portada;
        private TextView tv_titulo;
        private TextView tv_profesion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_portada = itemView.findViewById(R.id.iv_portada);
            tv_titulo = itemView.findViewById(R.id.tv_titulo);
            tv_profesion = itemView.findViewById(R.id.tv_profesion);
        }
    }
}
