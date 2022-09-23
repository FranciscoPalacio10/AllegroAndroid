package com.example.allegroandroid.models.desafios;

public class DesafiosXUserResponse {
    public String name;
    public String description;
    public String tipo;
    public Integer cantidadTotal;
    public Integer cantidadComplete;
    public Integer pointsTotal;
    public Integer pointsNow;
    public boolean isCompleted;

    public DesafiosXUserResponse(String name, String description, String tipo, int cantidadTotal, int cantidadComplete, int pointsTotal, int pointsNow, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.tipo = tipo;
        this.cantidadTotal = cantidadTotal;
        this.cantidadComplete = cantidadComplete;
        this.pointsTotal = pointsTotal;
        this.pointsNow = pointsNow;
        this.isCompleted = isCompleted;
    }
}
