package com.example.allegrobackend.ui.home.clasesRegistradas;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allegrobackend.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class clasesRegistradas extends Fragment {

    private String estilo,nivel,leyendaNivel;
    private ProgressBar progressClasesRegistradas;
    private TextView txtEstilo,txtNivel,txtLeyendaNivel;
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<clases> clasesArrayList = new ArrayList<>();
    private DatabaseReference database;
    public clasesRegistradas() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             estilo= getArguments().getString("estilo");
            Log.e("estilo",estilo);
             nivel= getArguments().getString("nivel");
            Log.e("nivel",nivel);
            leyendaNivel=getArguments().getString("leyenda");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_clases_registradas, container, false);
       progressClasesRegistradas=v.findViewById(R.id.progressClasesRegistradas);
        ocultarBarraProgreso(View.VISIBLE);
        llenarVariables(v);
        mostrarNivelyEstilo();
        mostrarLeyenda();
        adaptadorReciclyer();
        obtenerClases();

        return v;
    }

    private void llenarVariables(View v) {
        txtEstilo=v.findViewById(R.id.txtEstiloRegistrado);
        txtNivel=v.findViewById(R.id.txtNivelRegistrado);
        txtLeyendaNivel=v.findViewById(R.id.txtDescripcionClases);
        recyclerView=v.findViewById(R.id.reclyclerClasesRegistradas);
    }

    private void adaptadorReciclyer() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void mostrarNivelyEstilo() {
        if(nivel.equals("2-PRINCIPIANTE")){
            txtNivel.setText("PRINCIPIANTE II");
        }else if(nivel.equals("1-PRINCIPIANTE")){
            txtNivel.setText("PRINCIPIANTE I");
        }
        else {
            txtNivel.setText(nivel);
        }
        txtEstilo.setText(estilo);

    }



    private void obtenerClases(){
        clasesArrayList.clear();
        database= FirebaseDatabase.getInstance().getReference("Clases registradas");
        if(estilo.equals("CLASICO")){
            estilo="CLASICA";
        }
        database.child(estilo).child(nivel).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        String nroClase= dataSnapshot1.child("nro clase").getValue().toString();
                        String profesora= dataSnapshot1.child("profesora:").getValue().toString();
                        String uriClase= dataSnapshot1.child("link-uri").getValue().toString();
                        clasesArrayList.add(new clases(estilo,txtNivel.getText().toString(),nroClase,uriClase,profesora));
                    }
                    ocultarBarraProgreso(View.INVISIBLE);
                    adaptadorClasesRegistradas adaptadorClasesRegistradas = new adaptadorClasesRegistradas
                (clasesArrayList,R.layout.adaptadorclases,getContext(),progressClasesRegistradas);
                    recyclerView.setAdapter(adaptadorClasesRegistradas);
                }else{
                    ocultarBarraProgreso(View.INVISIBLE);
                    Toast.makeText(getContext(),"No hay clases disponibles",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ocultarBarraProgreso(int invisible) {
        progressClasesRegistradas.setVisibility(invisible);
    }

    private void mostrarLeyenda(){
        txtLeyendaNivel.setText(leyendaNivel);
    }
}
