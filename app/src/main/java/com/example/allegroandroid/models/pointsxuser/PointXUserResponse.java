package com.example.allegroandroid.models.pointsxuser;

import android.content.Intent;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.converters.DateConverter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class PointXUserResponse {
    @PrimaryKey
    @SerializedName("id")
    public Integer idPointXUser;
    @SerializedName("points")
    public Integer points;
    @SerializedName("clase_id")
    public int claseId;
    @SerializedName("date_created")
    public String dateCreated;
    @SerializedName("clase_response")
    @Embedded
    @TypeConverters(DateConverter.class)
    public ClaseResponse claseResponse;
    @SerializedName("usuario_id")
    public String userIdPointXUser;
    @Embedded
    @TypeConverters(DateConverter.class)
    public Date dateUpdate;

    public PointXUserResponse(int idPointXUser, int points, int claseId, String dateCreated, ClaseResponse claseResponse) {
        this.idPointXUser = idPointXUser;
        this.points = points;
        this.claseId = claseId;
        this.dateCreated = dateCreated;
        this.claseResponse = claseResponse;
    }
}
