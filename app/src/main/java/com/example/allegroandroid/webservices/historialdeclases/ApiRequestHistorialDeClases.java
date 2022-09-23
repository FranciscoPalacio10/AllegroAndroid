package com.example.allegroandroid.webservices.historialdeclases;

import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequest;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.models.response.ApiResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequestHistorialDeClases {

    @GET("/api/v1/historialdeclases/user")
    Call<ApiResponse<ArrayList<HistorialDeClaseResponse>>> getHistorialDeClasesByUserId(
            @Query("take") Integer take, @Query("pages") Integer pages, @Header("Authorization") String auth);

    @POST("/api/v1/historialdeclases")
    Call<ApiResponse<HistorialDeClaseResponse>> createHistorialDeClase(
            @Body HistorialDeClaseRequest historialDeClaseRequest, @Header("Authorization") String auth
    );

    @PUT("/api/v1/historialdeclases/{idClase}")
    Call<ApiResponse<HistorialDeClaseResponse>> UpdateHistorialDeClase(
            @Path("idClase") Integer idClase,
            @Body HistorialDeClaseRequest historialDeClaseRequest,
            @Header("Authorization") String auth
    );

}
