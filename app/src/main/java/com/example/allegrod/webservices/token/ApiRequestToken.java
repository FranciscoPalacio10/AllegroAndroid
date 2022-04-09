package com.example.allegrod.webservices.token;

import com.example.allegrod.models.token.TokenRequest;
import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.response.ApiResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequestToken {
    @POST("api/v1/auth/login")
    Call<ApiResponse<TokenResponse>> getToken(
            @Body TokenRequest email
    );

}
