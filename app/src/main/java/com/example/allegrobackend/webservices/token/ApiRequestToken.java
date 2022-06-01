package com.example.allegrobackend.webservices.token;

import com.example.allegrobackend.models.token.TokenRequest;
import com.example.allegrobackend.models.token.TokenResponse;
import com.example.allegrobackend.models.response.ApiResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
;

public interface ApiRequestToken {
    @POST("api/v1/auth/login")
    Call<ApiResponse<TokenResponse>> getToken(
            @Body TokenRequest email
    );

}
