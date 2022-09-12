package com.example.allegrobackend.ui.activityprofesores;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Iterator;

public class metodos {
        boolean seleccionado=false;
    public metodos(){


            }
            //llenar Spiner tipo danza
    public void llenarSpinnerTipoDeDanza(Spinner spinner, Context context){
        String[] tipodanza ={"Seleccionar","CLASICA","EJERCICIOS PARA FORTALECER PIE - PUNTAS","ELONGACIÓN",
                "BALLET REPERTORIO","JAZZ","CONTEMPORANEO","YOGANCE"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,tipodanza);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

    }

    //llenar Spiner nivel

    private void llenarSpinner1(Spinner nivelDanza, Context context){
        String[] niveldanza ={"Seleccionar","INICIAL","1-PRINCIPIANTE","2-PRINCIPIANTE","INTERMEDIO","AVANZADO"
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,niveldanza);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nivelDanza.setAdapter(arrayAdapter);

    }
    private void llenarSpinner2(Spinner nivelDanza, Context context){
        String[] niveldanza ={"Seleccionar","INTERMEDIO","AVANZADO"
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,niveldanza);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nivelDanza.setAdapter(arrayAdapter);

    }
    private void llenarSpinner3(Spinner nivelDanza, Context context){
        String[] niveldanza ={"Seleccionar","PRINCIPIANTE","INTERMEDIO","AVANZADO"
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,niveldanza);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nivelDanza.setAdapter(arrayAdapter);

    }
    private void llenarSpinner4(Spinner nivelDanza, Context context){
        String[] niveldanza ={"Seleccionar","INICIAL","PRINCIPIANTE","INTERMEDIO","AVANZADO"
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,niveldanza);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nivelDanza.setAdapter(arrayAdapter);

    }
    private void seleccionado(Spinner nivelDanza, Context context){
        String[] niveldanza ={"Seleccionar","Debe elegir un estilo de danza"
        };


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,niveldanza);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nivelDanza.setAdapter(arrayAdapter);

    }



    public void llenarSpinnerNivelDanza(Spinner tipoDanza, final Spinner nivelDanza, final Context context, final TextView subidoexitoso){


        tipoDanza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccionado= parent.getItemAtPosition(position).toString();
                if(seleccionado.equals("Seleccionar")){
                        seleccionado(nivelDanza,context);
                    }

                if(seleccionado.equals("CLASICA") || seleccionado.equals("JAZZ")
                      ){
                    llenarSpinner1(nivelDanza,context);
                    subidoexitoso.setVisibility(View.GONE);
                }
                else if (seleccionado.equals("ELONGACIÓN") || seleccionado.equals("CONTEMPORANEO")){
                    llenarSpinner4(nivelDanza,context);
                    subidoexitoso.setVisibility(View.GONE);
                }
                else if(seleccionado.equals("EJERCICIOS PARA FORTALECER PIE - PUNTAS") || seleccionado.equals("BALLET REPERTORIO")){
                    llenarSpinner2(nivelDanza,context);
                    subidoexitoso.setVisibility(View.GONE);
                }
                else if(seleccionado.equals("YOGANCE")){
                    llenarSpinner3(nivelDanza,context);
                    subidoexitoso.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context,"Debes seleccionar un Estilo",  Toast.LENGTH_LONG).show();
            }


        });

    }


    //llenar nroClase
    public void setNroClase(Spinner nivelDanza, final Spinner nro_clase, final Context context ){
        nivelDanza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Integer> nombreArrayList = new ArrayList<>();
                for (int i=1; i<=40; i++){
                    nombreArrayList.add(i);
                }


                ArrayAdapter <Integer> arrayAdapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_dropdown_item,nombreArrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                nro_clase.setAdapter(arrayAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context,"Debes seleccionar un Nivel de Danza",  Toast.LENGTH_LONG).show();
            }
        });
    }

    //llenar spiner profesora
    public void llenarProfesora(DatabaseReference Database1, final Spinner profesora, final Context context){
        final ArrayList<String> profesoras = new ArrayList<>();

        Iterator<String> nombreIterator = profesoras.iterator();

            Database1.child("Profesora").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if(dataSnapshot.exists()) {
                        String nombre=dataSnapshot.child("Nombre").getValue().toString();
                        profesoras.add(nombre);
                        ArrayAdapter < String > arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, profesoras);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        profesora.setAdapter(arrayAdapter);
                    }else if(!dataSnapshot.exists()){

                    }

                }


                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                   /* profesora.setAdapter(null);
                    String nombre=dataSnapshot.child("Nombre").getValue().toString();
                    profesoras.add(nombre);

                    ArrayAdapter < String > arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, profesoras);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    profesora.setAdapter(arrayAdapter);


                    */
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){

                            String caca= dataSnapshot.getKey();
                            String nombre= dataSnapshot.child("Nombre").getValue().toString();

                            profesoras.remove(nombre);
                            ArrayAdapter < String > arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, profesoras);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            profesora.setAdapter(arrayAdapter);



                    }




                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });



    }





}
