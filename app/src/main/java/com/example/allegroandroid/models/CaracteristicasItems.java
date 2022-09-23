package com.example.allegroandroid.models;

public class CaracteristicasItems {
    int imagen;
    String nombre;


    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CaracteristicasItems(int imagen, String nombre) {
        this.imagen=imagen;
        this.nombre=nombre;

    }
}
