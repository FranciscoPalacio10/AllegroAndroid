package com.example.allegrobackend.ui.activityprofesores.fragmentregistrarprofesores;

import android.content.Context;

public class profesora {
    private String nombre;
    private String id;
    private Context context;
    private int position;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public profesora(String nombre, String id, Context context) {
        this.nombre=nombre;
        this.id=id;
        this.context=context;
        this.position=position;

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}