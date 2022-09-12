package com.example.allegrobackend.services.token;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.allegrobackend.constants.AppConstant;
import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.models.token.TokenRequest;
import com.example.allegrobackend.models.token.TokenResponse;
import com.example.allegrobackend.repository.TokenRepository;
import com.example.allegrobackend.models.response.ApiResponse;
import com.example.allegrobackend.services.ActivitiesInitiator;
import com.example.allegrobackend.webservices.RetrofitFactory;
import com.example.allegrobackend.webservices.token.ApiRequestToken;
import com.example.allegrobackend.webservices.token.OnCallBackResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenServiceApi {
    private static final String TAG = TokenRepository.class.getSimpleName();
    private ApiRequestToken apiRequestToken;
    private SessionService sessionService;
    private InitActitvy initActitvy;
    public TokenServiceApi(InitActitvy initActitvy) {
        apiRequestToken = RetrofitFactory.getRetrofit(AppConstant.TOKEN).getRetrofitInstance().create(ApiRequestToken.class);
        sessionService= new SessionService(initActitvy.getContext());
        this.initActitvy= initActitvy;
    }

    public void getToken(String email, OnCallBackResponse<TokenResponse> callback) {
        apiRequestToken.getToken(new TokenRequest(email)).enqueue(new Callback<ApiResponse<TokenResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<TokenResponse>> call, Response<ApiResponse<TokenResponse>> response) {
                if(response.isSuccessful()){
                    sessionService.SaveBearerToken(email,response.body().getData().token);
                }
                    callback.saveResponse(new ApiResponse<>(response));
            }
            @Override
            public void onFailure(Call<ApiResponse<TokenResponse>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
                sessionService.SaveBearerToken(email,"");
                callback.saveResponse(new ApiResponse(t));
                Bundle pBundle = getBundle(t);
                initActitvy.setBundle(pBundle);
                ActivitiesInitiator.initErrorActivity(initActitvy);
            }
        });
    }



    @NonNull
    private Bundle getBundle(Throwable t) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t.getMessage());
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR,this.TAG);
        return pBundle;
    }
}
