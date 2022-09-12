package com.example.allegrobackend.ui.activityprofesores.fragmeteditarclases;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allegrobackend.R;
import com.example.allegrobackend.ui.activityprofesores.metodos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class editarClases extends Fragment {
    View v;
    private Spinner estiloDanza;
    private String estiloSeleccionado, nivelSeleccionado,fechapubli;
    private String id,link,nroClase,profesora;
    private Spinner nivelDanza;
    private RecyclerView recyclerView;
    private DatabaseReference database;
    private Button btnVerClases;
    metodos metodos = new metodos();
    private MyAdpter2 myadapter;
    Context context;
    private ProgressBar progressBar;
    private TextView textView4;
    private ArrayList<clasesRegistradas> clasesRegistradasArrayList= new ArrayList<>();
    public editarClases() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("estado","lista guardada");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("estado","Start");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("estado","retomarLista");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_editar_clases, container, false);
        estiloDanza=v.findViewById(R.id.spEstiloDanza);
        metodos.llenarSpinnerTipoDeDanza(estiloDanza,getContext());
        textView4=v.findViewById(R.id.textView4);
        context=getContext();
        nivelDanza=v.findViewById(R.id.spNivelDanza);
        metodos.llenarSpinnerNivelDanza(estiloDanza,nivelDanza,getContext(),textView4);
        recyclerView=v.findViewById(R.id.recyclerEditar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        btnVerClases=v.findViewById(R.id.btnVerClases);
        progressBar=v.findViewById(R.id.progressBarEditarClases);
        progressBar.setVisibility(View.GONE);
        btnVerClases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clasesRegistradasArrayList.clear();
                progressBar.setVisibility(View.VISIBLE);
                obtenerClaseRegistrada();
                progressBar.setVisibility(View.GONE);
            }
        });
        database=FirebaseDatabase.getInstance().getReference();

        return v;
    }


    private void obtenerClaseRegistrada() {
        estiloSeleccionado=estiloDanza.getSelectedItem().toString();
        nivelSeleccionado=nivelDanza.getSelectedItem().toString();
            nivelSeleccionado=nivelDanza.getSelectedItem().toString();
            database.child("Clases registradas").child(estiloSeleccionado).child(nivelSeleccionado).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                            fechapubli= singleSnapshot.child("Fecha publicación: ").getValue().toString();
                            id=singleSnapshot.child("id clase:").getValue().toString();
                            link=singleSnapshot.child("link-uri").getValue().toString();
                            nroClase=singleSnapshot.child("nro clase").getValue().toString();
                            profesora=singleSnapshot.child("profesora:").getValue().toString();
                            adaptadorClase(fechapubli, id, link, nroClase, profesora,estiloSeleccionado,nivelSeleccionado);

                        }
                    }else{
                        clasesRegistradasArrayList.clear();
                        Toast.makeText(getContext(),"Esta combinación no tiene clases registradas",Toast.LENGTH_LONG).show();
                        recyclerView.setAdapter(null);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        database.onDisconnect();


    }

    private void adaptadorClase(String fechapubli, String id, String link, String nroClase, String profesora,String estilo,String nivel) {
        clasesRegistradasArrayList.add(new clasesRegistradas(fechapubli,id,link,nroClase,profesora,estilo,nivel));
        myadapter= new MyAdpter2(clasesRegistradasArrayList,R.layout.layoutclaseregistrada,context);
        recyclerView.setAdapter(myadapter);
    }

    private boolean seleccionadoNivel(){
        boolean seleccionado=false;
        nivelDanza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(0).toString()=="Seleccionado"){
                    boolean seleccionado=false;
                }else{
                    nivelSeleccionado=parent.getItemAtPosition(position).toString();
                    boolean seleccionado=true;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return  seleccionado;
    }
       private boolean seleccionadoEstilo(){
        boolean seleccionado=false;
       estiloDanza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(parent.getItemAtPosition(0).toString()=="Seleccionado"){
                   boolean seleccionado=false;
               }else{
                   estiloSeleccionado=parent.getItemAtPosition(position).toString();
                   boolean seleccionado=true;
               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

       return  seleccionado;
    }
}


