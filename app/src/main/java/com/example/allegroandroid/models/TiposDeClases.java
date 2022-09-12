package com.example.allegroandroid.models;

public class TiposDeClases {

    public String name;
    public Integer materiaId;
    public Integer resourceId;

    public TiposDeClases(String name, Integer resourceId, Integer materiaId) {
        this.name = name;
        this.resourceId = resourceId;
        this.materiaId = materiaId;
    }
}
