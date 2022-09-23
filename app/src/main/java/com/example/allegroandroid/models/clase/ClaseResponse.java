package com.example.allegroandroid.models.clase;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.allegroandroid.models.MateriasResponse;
import com.example.allegroandroid.models.converters.DateConverter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class ClaseResponse {
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "clase_id")
    public Integer claseId;
    @ColumnInfo(name = "description")
    public String description;
    @SerializedName("userId")
    @ColumnInfo(name = "user_id_clase")
    public String userIdClaseResponse;
    @ColumnInfo(name = "date_created")
    public String dateCreated;
    @ColumnInfo(name = "date_modifed")
    public String dateModief;
    @ColumnInfo(name = "is_active")
    public boolean isActive;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "materia_id")
    public Integer materiaId;
    @ColumnInfo(name = "tipo")
    public String tipo;
    @ColumnInfo(name = "url")
    public String url;
    @Embedded
    public MateriasResponse materia;
    @TypeConverters(DateConverter.class)
    public Date dateUpdate;

    public ClaseResponse(Integer claseId, String description, String userId, String dateCreated, String dateModief, boolean isActive, String name, Integer materiaId, String tipo, String url, Date dateUpdate) {
        this.claseId = claseId;
        this.description = description;
        this.userIdClaseResponse = userId;
        this.dateCreated = dateCreated;
        this.dateModief = dateModief;
        this.isActive = isActive;
        this.name = name;
        this.materiaId = materiaId;
        this.tipo = tipo;
        this.url = url;
        this.dateUpdate = dateUpdate;
    }

    public ClaseResponse() {
    }
}
