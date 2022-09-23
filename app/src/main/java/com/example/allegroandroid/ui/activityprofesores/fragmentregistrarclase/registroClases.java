package com.example.allegroandroid.ui.activityprofesores.fragmentregistrarclase;

import android.widget.Spinner;

public class registroClases {

    private String estilo;
    private String nivel;
    private String clase;
    private String profesora;


    public String getEstilo() {
        return estilo;
    }

    public String getNivel() {
        return nivel;
    }

    public String getClase() {
        return clase;
    }

    public String getProfesora() {
        return profesora;
    }



    public registroClases(Spinner tipoDanza, Spinner nivelS, Spinner nro_clase, Spinner subirProfesora){
        estilo=tipoDanza.getSelectedItem().toString();
        nivel=nivelS.getSelectedItem().toString();
        clase=nro_clase.getSelectedItem().toString();
       profesora=subirProfesora.getSelectedItem().toString();
    }
}
