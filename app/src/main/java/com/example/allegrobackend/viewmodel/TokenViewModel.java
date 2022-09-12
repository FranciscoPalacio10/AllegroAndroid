package com.example.allegrobackend.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.models.token.TokenResponse;
import com.example.allegrobackend.repository.TokenRepository;

import java.util.Calendar;
import java.util.Date;

public class TokenViewModel extends AndroidViewModel {
    private final TokenRepository tokenRepository;

    public TokenViewModel(Application application) {
        super(application);
        this.tokenRepository = new TokenRepository(new InitActitvy(application.getApplicationContext(),null));

    }

    public  LiveData<TokenResponse> getBearerToken(String email) {
        return tokenRepository.getToken(email);
    }
}
