package com.example.allegrod.repository;

import android.content.Context;
import android.util.Log;
import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.token.TokenRequest;
import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.services.SessionService;
import com.example.allegrod.webservices.RetrofitFactory;
import com.example.allegrod.webservices.token.ApiRequestToken;
import com.example.allegrod.webservices.token.OnCallBackResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenRepository {
    private static final String TAG = TokenRepository.class.getSimpleName();
    private ApiRequestToken apiRequestToken;

    public TokenRepository(Context context) {
        apiRequestToken = RetrofitFactory.getRetrofit(AppConstant.TOKEN).getRetrofitInstance().create(ApiRequestToken.class);
    }

    public void getToken(String email, OnCallBackResponse<ApiResponse<TokenResponse>> callback) {
        apiRequestToken.getToken(new TokenRequest(email)).enqueue(new Callback<ApiResponse<TokenResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<TokenResponse>> call, Response<ApiResponse<TokenResponse>> response) {
                TokenResponse result = response.body().getData();
                callback.saveResponse(new ApiResponse<TokenResponse>(response));
            }
            @Override
            public void onFailure(Call<ApiResponse<TokenResponse>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
                callback.saveResponse(new ApiResponse(t));
            }
        });
    }
}