package com.example.allegrobackend.models;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class Materias {
    @ColumnInfo(name = "materias_id")
    private float id;
    @ColumnInfo(name = "materias_description")
    private String description;
    @ColumnInfo(name = "materias_usuario_id")
    private String usuarioId;
    @ColumnInfo(name = "materias_date_created")
    private String dateCreated;
    @ColumnInfo(name = "materias_date_modief")
    private String dateModief;
    @ColumnInfo(name = "materias_is_active")
    private boolean isActive;
    @ColumnInfo(name = "materias_name")
    private String name;
    @ColumnInfo(name = "materias_hours")
    private float hours;
    @ColumnInfo(name = "materias_cursos_id")
    private float cursosId;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateModief() {
        return dateModief;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public String getName() {
        return name;
    }

    public float getHours() {
        return hours;
    }

    public float getCursosId() {
        return cursosId;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateModief(String dateModief) {
        this.dateModief = dateModief;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public void setCursosId(float cursosId) {
        this.cursosId = cursosId;
    }

    public Materias() {

    }
}
