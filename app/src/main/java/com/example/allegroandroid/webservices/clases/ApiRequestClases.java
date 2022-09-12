package com.example.allegroandroid.webservices.clases;

import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiRequestClases {

    @GET("/api/v1/clases/type/{type}/materia/{materiaId}")
    Call<ApiResponse<ArrayList<ClaseResponse>>> geClasesByTypeAndMateriaId(
            @Path("type") String type, @Path("materiaId") Integer materiaId, @Header("Authorization") String auth);

}
