package com.example.allegrod.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.repository.TokenRepository;
import com.example.allegrod.repository.UserRepository;

public class TokenViewModel extends AndroidViewModel {

    private final TokenRepository tokenRepository;

    public TokenViewModel(Application application) {
        super(application);
        this.tokenRepository = new TokenRepository();
    }

    public String getBearerToken(String email){
        TokenResponse token = tokenRepository.getToken(email);
        return "Bearer " + token.token;
    }

}
