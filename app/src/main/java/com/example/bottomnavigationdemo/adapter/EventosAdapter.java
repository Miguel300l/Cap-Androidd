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
import com.example.bottomnavigationdemo.model.Eventos;


import java.util.List;


public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.ViewHolder> {

    private List<Eventos> verEventos;
    private Context context;

    public EventosAdapter(List<Eventos> verEventos, Context context) {
        this.verEventos = verEventos;
        this.context = context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recycler, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_titulo.setText(verEventos.get(position).getDescripcion());

       // Glide.with(context).load(verEventos.get(position).getUrlImg()).into(holder.iv_portada)
        Glide.with(context).load(verEventos.get(position).getImagen().getUrlImg()).into(holder.iv_portada);



    }

    @Override
    public int getItemCount() {

        return verEventos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_portada;
        private TextView tv_titulo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_portada = itemView.findViewById(R.id.iv_portada);
            tv_titulo = itemView.findViewById(R.id.tv_titulo);
        }
    }
}
