package com.example.allegrod.webservices.user;

import com.example.allegrod.models.token.TokenRequest;
import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.models.user.UserRequest;
import com.example.allegrod.models.user.UserResponse;
import com.example.allegrod.response.ApiResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiRequestToken {

    @POST("/api/v1/auth")
    Call<ApiResponse<UserResponse>> createUser(
            @Body UserRequest email
    );

}
