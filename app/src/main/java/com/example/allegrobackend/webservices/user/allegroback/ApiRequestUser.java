package com.example.allegrobackend.webservices.user.allegroback;

import com.example.allegrobackend.models.user.UserRequest;
import com.example.allegrobackend.models.user.create.UserCreateResponse;
import com.example.allegrobackend.models.user.get.UserResponse;
import com.example.allegrobackend.models.response.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiRequestUser {
    @POST("/api/v1/auth")
    Call<ApiResponse<UserCreateResponse>> createUser(
            @Body UserRequest createUser
    );

    @POST("/api/v1/user")
    Call<ApiResponse<UserResponse>> getUser(
            @Body UserRequest email, @Header("Authorization") String auth
    );

}
