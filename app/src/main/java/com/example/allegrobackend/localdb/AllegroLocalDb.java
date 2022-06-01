package com.example.allegrobackend.localdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.allegrobackend.models.user.get.UserResponse;


@Database(entities = {UserResponse.class}, version = 12)
public abstract class AllegroLocalDb extends RoomDatabase {

    abstract public UserDao userDao();

}
