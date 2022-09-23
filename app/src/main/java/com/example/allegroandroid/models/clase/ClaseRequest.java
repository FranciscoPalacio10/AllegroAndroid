package com.example.allegroandroid.models.clase;

import com.example.allegroandroid.models.user.User;

public class ClaseRequest {

    public String tipo;
    public int materiaId;
    public User user;

    public ClaseRequest(String type, int idMateria, User user) {
        tipo = type;
        this.materiaId = idMateria;
        this.user = user;
    }
}
