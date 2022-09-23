package com.example.allegroandroid.webservices.desafios;

import com.example.allegroandroid.models.desafios.DesafiosXUserResponse;
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

public interface ApiRequestDesafios {

    @GET("/api/v1/desafios")
    Call<ApiResponse<ArrayList<DesafiosXUserResponse>>> getDesafiosXUser( @Header("Authorization") String auth);

}
