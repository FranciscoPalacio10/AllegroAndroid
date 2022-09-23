package com.example.allegroandroid.webservices.token;

import com.example.allegroandroid.models.token.TokenRequest;
import com.example.allegroandroid.models.token.TokenResponse;
import com.example.allegroandroid.models.response.ApiResponse;


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
