package com.example.allegrobackend.models.user.get;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.allegrobackend.models.Cursos;
import com.example.allegrobackend.models.converters.DateConverter;
import com.example.allegrobackend.models.converters.MateriasConverter;
import com.example.allegrobackend.models.user.User;
import com.example.allegrobackend.models.user.UserRequest;
import com.example.allegrobackend.services.DateService;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class UserResponse {
    @PrimaryKey(autoGenerate = true)
    public int key;
    @TypeConverters(DateConverter.class)
    public Date dateUpdate;
    @Embedded
    public User user;
    @Embedded
    public ArrayList<String> rol;
    @Embedded
    public Cursos cursos;

    public UserResponse(User user) {
        this.user=user;
    }
}

