package com.example.allegrod.webservices.user;



import com.example.allegrod.models.user.UserRequest;
import com.example.allegrod.models.user.UserResponse;
import com.example.allegrod.response.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiRequestUser {

    @POST("/api/v1/auth")
    Call<ApiResponse<UserResponse>> createUser(
            @Body UserRequest email
    );

}
