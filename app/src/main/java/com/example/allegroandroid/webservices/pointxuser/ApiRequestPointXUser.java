package com.example.allegroandroid.webservices.pointxuser;

import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.models.pointsxuser.PointXUserRequest;
import com.example.allegroandroid.models.pointsxuser.PointXUserResponse;
import com.example.allegroandroid.models.response.ApiResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiRequestPointXUser {

    @GET("/api/v1/pointxuser/user")
    Call<ApiResponse<ArrayList<PointXUserResponse>>> getPointXUserById(@Header("Authorization") String auth);

    @POST("/api/v1/pointxuser")
    Call<ApiResponse<PointXUserResponse>> createPointXUser(
            @Body PointXUserRequest pointXUserRequest, @Header("Authorization") String auth
    );

}
