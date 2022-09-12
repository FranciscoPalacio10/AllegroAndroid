package com.example.allegrobackend.clases.claseFinalizada;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegrobackend.R;
import com.example.allegrobackend.clases.clasesRegistradasLista;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class adaptadorClasesFinalizadas extends RecyclerView.Adapter<adaptadorClasesFinalizadas.adaptadorClasesF> {
   private int resource;
   private Context context;
    private ArrayList<clasesRegistradasLista> clasesRegistradasListaArrayList;
    private int Tiempo;
    public adaptadorClasesFinalizadas(ArrayList<clasesRegistradasLista> clasesRegistradasListas,Context context,int reosource) {
        this.resource=reosource;
        this.context=context;
        this.clasesRegistradasListaArrayList=clasesRegistradasListas;
    }

    class adaptadorClasesF extends RecyclerView.ViewHolder{
        TextView txtEstilo, txtNivel, txtProfesora, txtNroClase,txtFecha;
        Button btnRepetirClase;
        public adaptadorClasesF(@NonNull View v) {
            super(v);
            txtEstilo=v.findViewById(R.id.txtEstiloFinalizado);
            txtNivel=v.findViewById(R.id.txtNivelFinalizado);
            txtProfesora=v.findViewById(R.id.txtProfesoraFinalizado);
            txtNroClase=v.findViewById(R.id.txtNroClaseFinalizado);
            txtFecha=v.findViewById(R.id.txtFechaFinalizado);
            btnRepetirClase=v.findViewById(R.id.btnRepetirClase);
        }
    }

    @NonNull
    @Override
    public adaptadorClasesF onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        adaptadorClasesFinalizadas.adaptadorClasesF adaptadorClasesF = new  adaptadorClasesFinalizadas.adaptadorClasesF(view);
        return adaptadorClasesF;
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorClasesF holder, int position) {
        clasesRegistradasLista clasesRegistradasLista=clasesRegistradasListaArrayList.get(position);
        holder.txtEstilo.setText(clasesRegistradasLista.getEstilo());
        holder.txtNivel.setText(clasesRegistradasLista.getNivel());
        holder.txtProfesora.setText(clasesRegistradasLista.getProfesora());
        holder.txtNroClase.setText(clasesRegistradasLista.getNumeroClase());
        holder.txtFecha.setText(clasesRegistradasLista.getFecha());
        holder.btnRepetirClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getBundle(clasesRegistradasLista);
                Navigation.findNavController(v).navigate(R.id.action_clases_to_reproductorClase,bundle);
            }
        });
    }

    @NotNull
    private Bundle getBundle(clasesRegistradasLista clasesRegistradasLista) {
        Bundle bundle= new Bundle();
        bundle.putString("url",clasesRegistradasLista.getUri());
        bundle.putString("estilo",clasesRegistradasLista.getEstilo());
        bundle.putString("nivel",clasesRegistradasLista.getNivel());
        bundle.putString("nroClase",clasesRegistradasLista.getNumeroClase());
        bundle.putString("profesora",clasesRegistradasLista.getProfesora());
        return bundle;
    }

    @Override
    public int getItemCount() {
        return clasesRegistradasListaArrayList.size();
    }

}
