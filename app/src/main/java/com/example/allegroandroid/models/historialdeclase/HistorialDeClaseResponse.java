package com.example.allegroandroid.models.historialdeclase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.converters.DateConverter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

@Entity
public class HistorialDeClaseResponse {
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id_historial")
    public Integer historialDeClaseId;
    @SerializedName("userId")
    @ColumnInfo(name = "user_id_historial_de_clase")
    public String userIdHistorialDeClase;
    @ColumnInfo(name = "date_created_historial_de_clase")
    @SerializedName("dateCreated")
    public String dateCreated;
    @ColumnInfo(name = "date_modifed_historial_de_clase")
    public String dateModief;
    @ColumnInfo(name = "is_finished")
    public boolean isFinished;
    @Embedded
    public ClaseResponse clase;
    @ColumnInfo(name = "clase_id_historial_de_clase")
    public Integer claseId;
    @ColumnInfo(name = "time_total_video")
    public Integer timeTotalVideo;
    @ColumnInfo(name = "time_leave_video")
    public Integer timeLeaveVideo;
    @ColumnInfo(name = "date_update_historial_de_clase")
    @TypeConverters(DateConverter.class)
    public Date dateUpdate;

    public HistorialDeClaseResponse(Integer id, String userId, String dateCreated, String dateModief, ClaseResponse clase, boolean isFinished, int timeTotalVideo, int timeLeaveVideo) {
        this.historialDeClaseId = id;
        this.userIdHistorialDeClase = userId;
        this.dateCreated = dateCreated;
        this.dateModief = dateModief;
        this.isFinished = isFinished;
        this.clase = clase;
        this.timeTotalVideo = timeTotalVideo;
        this.timeLeaveVideo = timeLeaveVideo;
    }

    public HistorialDeClaseResponse() {
    }

}
