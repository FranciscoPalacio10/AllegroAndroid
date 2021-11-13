package com;

import android.widget.Spinner;

public class rutaVideo {
       private String tipoDeDanza;
       private String nivelDanza;
       private String clase;
       private String nombreProfesora;
    public rutaVideo(Spinner tipoDanza, Spinner nivel, Spinner nro_clase, Spinner subirProfesora){
         tipoDeDanza=tipoDanza.getSelectedItem().toString().substring(0,3);
          nivelDanza=nivel.getSelectedItem().toString().substring(0,3);
          clase=nro_clase.getSelectedItem().toString();
          nombreProfesora=subirProfesora.getSelectedItem().toString().substring(0,3);
    }

    public String getTipoDeDanza() {
        return tipoDeDanza;
    }

    public String getNivelDanza() {
        return nivelDanza;
    }

    public String getClase() {
        return clase;
    }

    public String getNombreProfesora() {
        return nombreProfesora;
    }

    public String getRutaArchivo(){
        String direccion=tipoDeDanza.concat(nivelDanza).concat(clase).concat(nombreProfesora);
        return direccion;
    }

}
