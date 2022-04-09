package com.example.allegrod.webservices.user;



import com.example.allegrod.models.user.UserRequest;
import com.example.allegrod.models.user.create.UserCreateResponse;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.response.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiRequestUser {
    @POST("/api/v1/auth")
    Call<ApiResponse<UserCreateResponse>> createUser(
            @Body UserRequest email
    );

    @POST("/api/v1/user")
    Call<ApiResponse<UserGetResponse>> getUser(
            @Body UserRequest email, @Header("Authorization") String auth
    );

}
