package com.example.allegrod.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.token.TokenRequest;
import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.services.SessionService;
import com.example.allegrod.services.token.TokenServiceApi;
import com.example.allegrod.webservices.RetrofitFactory;
import com.example.allegrod.webservices.token.ApiRequestToken;
import com.example.allegrod.webservices.token.OnCallBackResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenRepository {
    private static final String TAG = TokenRepository.class.getSimpleName();
    private TokenServiceApi tokenServiceApi;
    private SessionService sessionService;
    public TokenRepository(Context context) {
        tokenServiceApi= new TokenServiceApi(context);
        sessionService= new SessionService(context);
    }

    public MutableLiveData<TokenResponse> getToken(String email) {
        MutableLiveData<TokenResponse> data = new MutableLiveData<>();
        if(sessionService.isBearerLoaded(email)){
            data.setValue(sessionService.GetBearerToken(email));
        }else{
            tokenServiceApi.getToken(email, new OnCallBackResponse<TokenResponse>() {
                @Override
                public void saveResponse(ApiResponse<TokenResponse> response) {
                    if(response.isSuccesful()){
                        data.setValue(response.getData());
                    }else{
                        Log.e(TAG,response.getErrorMessage());
                    }
                }
            });
        }

        return data;
    }






}