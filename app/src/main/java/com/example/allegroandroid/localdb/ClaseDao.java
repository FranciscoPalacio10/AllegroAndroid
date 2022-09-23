package com.example.allegroandroid.localdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.user.get.UserResponse;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface ClaseDao {

    @Query("SELECT * FROM ClaseResponse")
    List<ClaseResponse> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<ClaseResponse> claseResponses);

    @Query("SELECT * FROM ClaseResponse WHERE tipo=(:tipo) and materia_id=(:materia_id)")
     List<ClaseResponse> findByTipoAndMateria(String tipo, int materia_id);
}
