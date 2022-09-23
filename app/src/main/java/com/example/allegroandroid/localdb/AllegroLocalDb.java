package com.example.allegroandroid.localdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.models.pointsxuser.PointXUserResponse;
import com.example.allegroandroid.models.user.get.UserResponse;


@Database(entities = {UserResponse.class, ClaseResponse.class, HistorialDeClaseResponse.class, PointXUserResponse.class}, version = 38)
public abstract class AllegroLocalDb extends RoomDatabase {

    abstract public UserDao userDao();

    abstract public ClaseDao claseDao();

    abstract public HistorialDeClasesDao historialDeClasesDao();

    abstract public PointsXUserDao pointsXUserDao();
}
