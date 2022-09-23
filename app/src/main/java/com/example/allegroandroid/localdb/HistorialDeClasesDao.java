package com.example.allegroandroid.localdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;


import java.util.ArrayList;
import java.util.List;

@Dao
public interface HistorialDeClasesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<HistorialDeClaseResponse> historialDeClaseResponses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistorialDeClaseResponse historialDeClaseResponses);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(HistorialDeClaseResponse historialDeClaseResponses);

    @Query("SELECT * FROM HistorialDeClaseResponse WHERE user_id_historial_de_clase=(:userId) Order by dateUpdate desc ")
    List<HistorialDeClaseResponse> findByUserId(String userId);

    @Query("SELECT * FROM HistorialDeClaseResponse WHERE id_historial=(:id)  LIMIT 1")
    HistorialDeClaseResponse getById(Integer id);
}
