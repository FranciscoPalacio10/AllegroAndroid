package com.example.allegrod.home.estiloDanza;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegrod.R;

import java.util.ArrayList;


public class adaptadorEstilo extends RecyclerView.Adapter<adaptadorEstilo.adaptadorEstiloDanza> {
    private ImageView imageButton;
    private String estiloDanza,f,b;
    int resource;
    Context context;
    private ArrayList<com.example.allegrod.home.estiloDanza.estiloDanza> estiloDanzaArrayList;


    public adaptadorEstilo(ArrayList<estiloDanza>estiloDanzas, int resource, Context context) {
        estiloDanzaArrayList=estiloDanzas;
        this.resource=resource;
        this.context=context;
    }

    class adaptadorEstiloDanza extends RecyclerView.ViewHolder{
        private ImageView btnestiloClase;
        private TextView nombreEstilo;
        public adaptadorEstiloDanza(@NonNull View v) {
            super(v);
            btnestiloClase=v.findViewById(R.id.imageButtonEstilo);
            nombreEstilo=v.findViewById(R.id.txtEstilo);
        }
    }

        @NonNull
    @Override
    public adaptadorEstiloDanza onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
            adaptadorEstilo.adaptadorEstiloDanza adapterClasesRegistradas = new  adaptadorEstilo.adaptadorEstiloDanza(view);
            return adapterClasesRegistradas;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull adaptadorEstiloDanza holder, int position) {
        String f="FORTALECER"
                + System.getProperty ("line.separator") +
                "PIE Y PUNTAS";
        String b="BALLET"
                + System.getProperty ("line.separator") +
                "REPERTORIO";

            final estiloDanza registro=estiloDanzaArrayList.get(position);
            holder.btnestiloClase.setImageResource(registro.getImageView());
            holder.nombreEstilo.setText(registro.getNombreEstilo());
            holder.btnestiloClase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(registro.getNombreEstilo().equals("SUBIR CLASES")){
                        Navigation.findNavController(v).navigate(R.id.action_home_to_registrarClases);
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("leyenda",registro.getLeyenda());
                        bundle.putString("estilo",registro.getNombreParanivel());

                        Navigation.findNavController(v).navigate(R.id.action_home_to_nivelClaseSeleccionado,bundle);
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return estiloDanzaArrayList.size();
    }

}


      /*if(registro.getNombreEstilo().equals("CLASICA") || registro.getNombreEstilo().equals("JAZZ")){
              Bundle bundle = new Bundle();
              bundle.putString("leyenda",registro.getLeyenda());
              bundle.putString("estilo",registro.getNombreParanivel());
              Navigation.findNavController(v).navigate(R.id.action_home_to_nivelClaseSeleccionado,bundle);
              }else if(registro.getNombreEstilo().equals(f)  || registro.getNombreEstilo().equals(b)){
              Bundle bundle = new Bundle();
              bundle.putString("leyenda",registro.getLeyenda());
              bundle.putString("estilo",registro.getNombreParanivel());
              Navigation.findNavController(v).navigate(R.id.action_home_to_nivelClaseSeleccionado,bundle);
              }else if(registro.getNombreEstilo().equals("ELONGACION") || registro.getNombreEstilo().equals("CONTEMPORANEO")){
              Log.e("seleccion","seleccionado3333");
              }else if(registro.getNombreEstilo().equals("YOGANCE")){
              Log.e("seleccion","seleccionado4444");
              }else{
              Log.e("seleccion","SIN SELECCION");
              }

       */

