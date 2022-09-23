package com.example.allegroandroid.localdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.allegroandroid.models.pointsxuser.PointXUserResponse;

import java.util.ArrayList;

@Dao
public interface PointsXUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PointXUserResponse pointXUserResponse);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(PointXUserResponse pointXUserResponse);

    @Query("SELECT * FROM PointXUserResponse WHERE userIdPointXUser=(:userId) Order by dateUpdate desc ")
    PointXUserResponse findByUserId(String userId);

    @Query("SELECT * FROM PointXUserResponse WHERE idPointXUser=(:id)  LIMIT 1")
    PointXUserResponse getById(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<PointXUserResponse> items);
}
