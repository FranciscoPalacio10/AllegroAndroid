package com.example.allegrod.clases.claseEnCurso;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegrod.clases.claseFinalizada.adaptadorClasesFinalizadas;
import com.example.allegrod.clases.clasesRegistradasLista;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class obtnerClasesCurso {
    private String usuario;
    private ProgressBar progressBar;
    private DatabaseReference database;
    private String estado;
    private ArrayList<clasesRegistradasLista> arrayListF = new ArrayList<>();
    public obtnerClasesCurso(String usuario,ProgressBar progressBar) {
        this.usuario=usuario;
        this.progressBar=progressBar;
    }
    //
    public void conectaraBasededatosHistorial(){
        database= FirebaseDatabase.getInstance().getReference("Historial Clases");
        Log.e("estado", "conectado");
    }
    public void  llenarAdaptadores(String estilo, String nivel, RecyclerView recyclerView, int layout, Context context){
        Log.e("estado", "llenado");
        if(usuario!=null){
            database.child(usuario).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for(DataSnapshot dataSnapshot2: dataSnapshot.child(estilo).child(nivel).getChildren()){
                            estado= dataSnapshot2.child("estado").getValue().toString();
                            String estilo= dataSnapshot2.child("estilo").getValue().toString();
                            String nroClase=dataSnapshot2.child("nroClase").getValue().toString();
                            String nivel=dataSnapshot2.child("nivel").getValue().toString();
                            String profesora= dataSnapshot2.child("profesora").getValue().toString();
                            String minuto=  dataSnapshot2.child("minuto").getValue().toString();
                            String uri=  dataSnapshot2.child("uri").getValue().toString();
                            String fecha=  dataSnapshot2.child("fecha").getValue().toString();
                            arrayListF.add(new clasesRegistradasLista(estilo,nivel,profesora,nroClase,minuto,uri,fecha));
                            if(estado.equals("play")){
                                adaptadorClasesFinalizadas adaptadorClasesFinalizadas = new adaptadorClasesFinalizadas(arrayListF,context,layout);
                                recyclerView.setAdapter(adaptadorClasesFinalizadas);
                            }
                        }

                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }



}