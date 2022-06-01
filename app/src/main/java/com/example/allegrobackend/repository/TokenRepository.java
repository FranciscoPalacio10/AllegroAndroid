package com.example.allegrobackend.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.models.token.TokenResponse;
import com.example.allegrobackend.models.response.ApiResponse;
import com.example.allegrobackend.services.token.SessionService;
import com.example.allegrobackend.services.token.TokenServiceApi;
import com.example.allegrobackend.webservices.token.OnCallBackResponse;

public class TokenRepository {
    private static final String TAG = TokenRepository.class.getSimpleName();
    private TokenServiceApi tokenServiceApi;
    private SessionService sessionService;
    public TokenRepository(InitActitvy initActitvy) {
        tokenServiceApi= new TokenServiceApi(initActitvy);
        sessionService= new SessionService(initActitvy.getContext());
    }

    public MutableLiveData<TokenResponse> getToken(String email) {
        MutableLiveData<TokenResponse> data = new MutableLiveData<>();
        if(!sessionService.isNecessaryLoadBearer(email)){
            data.setValue(sessionService.GetBearerToken(email));
        }else{
            tokenServiceApi.getToken(email, response -> {
                if(response.isSuccesful()){
                    data.setValue(response.getData());
                }else{
                    data.setValue(new TokenResponse("",false));
                }
            });
        }

        return data;
    }

}