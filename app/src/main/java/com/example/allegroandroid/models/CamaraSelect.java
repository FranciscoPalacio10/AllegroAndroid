package com.example.allegroandroid.models;

import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;

public class CamaraSelect {

    private String name;
    private String descrption;
    public String getName() {
        return name;
    }
    public HistorialDeClaseResponse historialDeClaseResponse;

    public HistorialDeClaseResponse getHistorialDeClaseResponse() {
        return historialDeClaseResponse;
    }

    public String getDescrption() {
        return descrption;
    }

    public Integer getNavigationDestination() {
        return navigationDestination;
    }

    private Integer navigationDestination;

    public CamaraSelect(String name, String descrption, Integer layout, HistorialDeClaseResponse historialDeClaseResponse) {
        this.name = name;
        this.descrption = descrption;
        this.navigationDestination = layout;
        this.historialDeClaseResponse=historialDeClaseResponse;
    }
}
