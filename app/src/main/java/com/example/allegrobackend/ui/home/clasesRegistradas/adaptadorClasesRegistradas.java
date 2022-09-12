package com.example.allegrobackend.ui.home.clasesRegistradas;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegrobackend.R;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class adaptadorClasesRegistradas extends RecyclerView.Adapter<adaptadorClasesRegistradas
        .adaptadorClases> {

    private ArrayList<clases> clasesArrayList;
    private int resource;
    private Context context;
    private String uriClase;
    private DatabaseReference database;
    private ProgressBar progressBar;

    public adaptadorClasesRegistradas(ArrayList<clases> clasesArrayList, int resource, Context context, ProgressBar progressBar) {
        this.clasesArrayList=clasesArrayList;
        this.resource=resource;
        this.context=context;
        this.progressBar=progressBar;
    }

    class adaptadorClases extends RecyclerView.ViewHolder{
        private TextView txtNumeroClase,txtNomreProfe;
        private Button btnEmpezarClase;
        private CardView tarjeta;
        public adaptadorClases(@NonNull View v) {
            super(v);
            txtNomreProfe=v.findViewById(R.id.txtNombreProfe);
            txtNumeroClase=v.findViewById(R.id.txtNroClase);
            btnEmpezarClase=v.findViewById(R.id.btnInciarClase);
            tarjeta=v.findViewById(R.id.cardClasesRegistradas);
        }
    }

    @NonNull
    @Override
    public adaptadorClases onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        adaptadorClasesRegistradas.adaptadorClases adaptadorClasesRegistradas= new adaptadorClasesRegistradas.adaptadorClases(view);
        return adaptadorClasesRegistradas;
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorClases holder, int position) {
            clases registro =clasesArrayList.get(position);
            holder.txtNomreProfe.setText(registro.getProfesora());
            holder.txtNumeroClase.setText(registro.getNroClase());
            progressBar.setVisibility(View.GONE);
            empezarClase(holder, registro);




    }

    private void empezarClase(@NonNull final adaptadorClases holder, final clases registro) {
        holder.btnEmpezarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle pasar = pasarDatos(registro, holder);
                Navigation.findNavController(v).navigate(R.id.action_clasesRegistradas_to_reproductorClase,pasar);
            }
        });
    }

    @NotNull
    private Bundle pasarDatos(clases registro, @NonNull adaptadorClases holder) {
        Bundle pasar= new Bundle();
        pasar.putString("url",registro.getUriCLase());
        if(registro.getEstilo().equals("CLASICA")){
            pasar.putString("estilo","CLASICO");
        }else {
            pasar.putString("estilo",registro.getEstilo());
        }

        pasar.putString("nivel",registro.getNivel());
        pasar.putString("nroClase",registro.getNroClase());
        pasar.putString("profesora",registro.getProfesora());
        return pasar;
    }



    @Override
    public int getItemCount() {
        return clasesArrayList.size();
    }


}
