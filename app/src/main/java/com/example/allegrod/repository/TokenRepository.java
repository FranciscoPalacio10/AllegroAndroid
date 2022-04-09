package com.example.allegrod.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.token.TokenRequest;
import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.webservices.RetrofitFactory;
import com.example.allegrod.webservices.token.ApiRequestToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenRepository {
    private static final String TAG = TokenRepository.class.getSimpleName();
    private ApiRequestToken apiRequestToken;

    public TokenRepository() {
        apiRequestToken = RetrofitFactory.getRetrofit(AppConstant.TOKEN).getRetrofitInstance().create(ApiRequestToken.class);
    }

    public TokenResponse getToken(String email) {
       TokenResponse data = new TokenResponse();
        apiRequestToken.getToken(new TokenRequest(email)).enqueue(new Callback<ApiResponse<TokenResponse>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<TokenResponse>> call, Response<ApiResponse<TokenResponse>> response) {
                        TokenResponse result = response.body().getData();
                        data.token = result.token;
                        data.login = result.login;
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<TokenResponse>> call, Throwable t) {
                        Log.d(TAG,t.getMessage());
                    }
                });
        return data;
    }
}