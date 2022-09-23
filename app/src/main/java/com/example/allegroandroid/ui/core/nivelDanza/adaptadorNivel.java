package com.example.allegroandroid.ui.core.nivelDanza;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegroandroid.R;

import java.util.ArrayList;

public class adaptadorNivel extends RecyclerView.Adapter<adaptadorNivel.adaptadorNivelDanza> {
   private ArrayList<nivelClase> nivelClaseArrayList;
   private int resource;
   private Context context;


    public adaptadorNivel(ArrayList<nivelClase> nivelClaseArrayList, int resoucse, Context context) {
        this.nivelClaseArrayList=nivelClaseArrayList;
        this.resource=resoucse;
        this.context=context;

    }
    class adaptadorNivelDanza extends RecyclerView.ViewHolder{
        private ImageView bailinarinasNivel;
        private TextView txtNivel;
        private LinearLayout linearLayout;
        public adaptadorNivelDanza(@NonNull View v) {
            super(v);
            bailinarinasNivel=v.findViewById(R.id.imagenNivel);
            txtNivel=v.findViewById(R.id.txtNivel);
            linearLayout=v.findViewById(R.id.linearNivel);
        }
    }

    @NonNull
    @Override
    public adaptadorNivelDanza onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        adaptadorNivel.adaptadorNivelDanza adapterNivelDanza = new adaptadorNivel.adaptadorNivelDanza(view);
        return adapterNivelDanza ;
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorNivelDanza holder, int position) {
        nivelClase registro=nivelClaseArrayList.get(position);
        holder.txtNivel.setText(registro.getNivel());
        holder.bailinarinasNivel.setImageResource(registro.getImagen());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirClasesRegistradas(v, registro);
            }
        });
    }

    private void abrirClasesRegistradas(View v, nivelClase registro) {
        if(registro.getNivel().equals("PRINCIPIANTE I")){
            Bundle bundle= new Bundle();
            bundle.putString("leyenda",registro.getLeyenda());
            bundle.putString("estilo",registro.getEstilo());
            bundle.putString("nivel","1-PRINCIPIANTE");
           // Navigation.findNavController(v).navigate(R.id.action_nivelClaseSeleccionado_to_clasesRegistradas,bundle);
        }else if( registro.getNivel().equals("PRINCIPIANTE II")){
            Bundle bundle= new Bundle();
            bundle.putString("leyenda",registro.getLeyenda());
            bundle.putString("estilo",registro.getEstilo());
            bundle.putString("nivel","2-PRINCIPIANTE");
          //  Navigation.findNavController(v).navigate(R.id.action_nivelClaseSeleccionado_to_clasesRegistradas,bundle);
        }


        else{
            Bundle bundle= new Bundle();
            bundle.putString("leyenda",registro.getLeyenda());
            bundle.putString("estilo",registro.getEstilo());
            bundle.putString("nivel",registro.getNivel());
          //  Navigation.findNavController(v).navigate(R.id.action_nivelClaseSeleccionado_to_clasesRegistradas,bundle);
        }
    }

    @Override
    public int getItemCount() {
        return nivelClaseArrayList.size();
    }


}
