package com.example.allegroandroid.models;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.allegroandroid.models.converters.MateriasConverter;

import java.util.ArrayList;

public class Cursos {
    @ColumnInfo(name = "curso_id")
    private float id;
    @ColumnInfo(name = "curso_description")
    private String description;
    @ColumnInfo(name = "curso_usuario_id")
    private String usuarioId;
    @ColumnInfo(name = "curso_date_created")
    private String dateCreated;
    @ColumnInfo(name = "curso_date_modief")
    private String dateModief;
    @ColumnInfo(name = "curso_is_active")
    private boolean isActive;
    @ColumnInfo(name = "curso_name")
    private String name;
    @ColumnInfo(name = "curso_niveles_id")
    private float nivelesId;
    @TypeConverters(MateriasConverter.class)
    ArrayList<MateriasResponse> materias;

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModief() {
        return dateModief;
    }

    public void setDateModief(String dateModief) {
        this.dateModief = dateModief;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getNivelesId() {
        return nivelesId;
    }

    public void setNivelesId(float nivelesId) {
        this.nivelesId = nivelesId;
    }

    public ArrayList<MateriasResponse> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<MateriasResponse> materias) {
        this.materias = materias;
    }

}