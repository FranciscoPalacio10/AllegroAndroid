package com.example.allegroandroid.models.user.get;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.allegroandroid.models.Cursos;
import com.example.allegroandroid.models.converters.DateConverter;
import com.example.allegroandroid.models.user.User;

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

