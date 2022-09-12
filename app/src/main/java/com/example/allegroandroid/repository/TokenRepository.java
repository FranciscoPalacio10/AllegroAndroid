package com.example.allegroandroid.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.allegroandroid.repository.resource.NetworkBoundResource;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.models.token.TokenRequest;
import com.example.allegroandroid.models.token.TokenResponse;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.webservices.token.ApiRequestToken;

public class TokenRepository {

    private final AppExecutors appExecutors;
    private final ApiRequestToken apiRequestToken;
    private SessionService sessionService;

    public TokenRepository(AppExecutors appExecutors, ApiRequestToken apiRequestToken){
        //  this.userDao = userDao;
        this.apiRequestToken = apiRequestToken;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<TokenResponse>> loadToken(Context context, TokenRequest tokenRequest){
        sessionService= new SessionService(context);
        return new NetworkBoundResource<TokenResponse, TokenRequest>(appExecutors, tokenRequest, context){

            @Override
            protected boolean shouldFetch(TokenResponse data) {
                return sessionService.isNecessaryLoadBearer(tokenRequest.email);
            }

            @Override
            protected TokenResponse loadFromDb() {
                return null;
            }

            @Override
            protected void saveInLocalStore(TokenResponse item) {
                sessionService.SaveBearerToken(tokenRequest.email, item.token);
            }

            @Override
            protected TokenResponse loadFromSharedPreferences() {
                return sessionService.GetBearerToken(tokenRequest.email);
            }

            @Override
            protected void createCall() {
                callApi(apiRequestToken.getToken(tokenRequest));
            }
        }.asLiveData();
    }

}
