package com.example.allegroandroid.viewmodel;

import android.content.Context;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.allegroandroid.models.token.TokenRequest;
import com.example.allegroandroid.models.token.TokenResponse;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.TokenRepository;

import java.util.Objects;

public class TokenViewModelNew extends ViewModel {
    final MutableLiveData<TokenRequest> tokenRequest;
    private final LiveData<Resource<TokenResponse>> responseLiveData;

    public TokenViewModelNew(TokenRepository repository, Context context){
        tokenRequest = new MutableLiveData<>();
        responseLiveData = Transformations.switchMap(tokenRequest, new Function<TokenRequest, LiveData<Resource<TokenResponse>>>() {
                   @Override
            public LiveData<Resource<TokenResponse>> apply(TokenRequest tokenRequest) {
                if(tokenRequest == null){
                    return null;
                }else{
                    return repository.loadToken(context, tokenRequest);
                }
            }
        });

    }

    public LiveData<Resource<TokenResponse>> getToken(){
        return responseLiveData;
    }

    public void retry(){
        TokenRequest current = tokenRequest.getValue();
        if(current != null){
            tokenRequest.setValue(current);
        }
    }

    public void setTokenRequest(TokenRequest tokenRequest){
        if(Objects.equals(this.tokenRequest.getValue(), tokenRequest)){
            return;
        }
        this.tokenRequest.setValue(tokenRequest);
    }

}
