package com.example.allegrod.services.token;

import android.content.Context;
import android.util.Log;

import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.token.TokenRequest;
import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.repository.TokenRepository;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.services.SessionService;
import com.example.allegrod.webservices.RetrofitFactory;
import com.example.allegrod.webservices.token.ApiRequestToken;
import com.example.allegrod.webservices.token.OnCallBackResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenServiceApi {
    private static final String TAG = TokenRepository.class.getSimpleName();
    private ApiRequestToken apiRequestToken;
    private SessionService sessionService;
    public TokenServiceApi(Context context) {
        apiRequestToken = RetrofitFactory.getRetrofit(AppConstant.TOKEN).getRetrofitInstance().create(ApiRequestToken.class);
        sessionService= new SessionService(context);
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
            }
        });
    }
}
