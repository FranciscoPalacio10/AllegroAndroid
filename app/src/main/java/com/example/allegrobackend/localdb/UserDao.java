package com.example.allegrobackend.localdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.allegrobackend.models.user.get.UserResponse;

import java.util.List;


@Dao
public interface UserDao {
    @Query("SELECT * FROM UserResponse")
    List<UserResponse> getAll();

    @Query("SELECT * FROM UserResponse WHERE email=(:emails) LIMIT 1")
    UserResponse findByEmail(String emails);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserResponse... users);

    @Query("SELECT * FROM UserResponse WHERE id=(:id) LIMIT 1")
    UserResponse findById(int id);
}
