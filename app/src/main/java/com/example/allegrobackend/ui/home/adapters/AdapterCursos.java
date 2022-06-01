package com.example.allegrobackend.ui.home.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegrobackend.R;
import com.example.allegrobackend.models.Materias;

import java.util.List;


public class AdapterCursos extends RecyclerView.Adapter<AdapterCursos.ViewHolderEstiloDanza> {
    int resource;
    Context context;
    private List<Materias> materiasList;

    public AdapterCursos(List<Materias> materiasList, int resource, Context context) {
        this.materiasList =materiasList;
        this.resource=resource;
        this.context=context;
    }

    class ViewHolderEstiloDanza extends RecyclerView.ViewHolder{
        private ImageView btnestiloClase;
        private TextView nombreEstilo;
        public ViewHolderEstiloDanza(@NonNull View v) {
            super(v);
            btnestiloClase=v.findViewById(R.id.imageButtonEstilo);
            nombreEstilo=v.findViewById(R.id.txtEstilo);
        }
    }

    @NonNull
    @Override
    public ViewHolderEstiloDanza onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
            return new ViewHolderEstiloDanza(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderEstiloDanza holder, int position) {
            final Materias curso= materiasList.get(position);
            holder.btnestiloClase.setImageResource(R.drawable.barepertorio);
            holder.nombreEstilo.setText(curso.getName());
            holder.btnestiloClase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("leyenda","pepe");
                        bundle.putString("estilo","grillo");

                        Navigation.findNavController(v).navigate(R.id.action_home_to_nivelClaseSeleccionado,bundle);
                }
            });

    }

    @Override
    public int getItemCount() {
        return materiasList.size();
    }

}
