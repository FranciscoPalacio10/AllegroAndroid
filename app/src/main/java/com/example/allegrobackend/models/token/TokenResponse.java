package com.example.allegrobackend.models.token;

public class TokenResponse {
    public String token;
    public boolean login;

    public TokenResponse(String token, boolean login) {
        this.token = token;
        this.login = login;
    }

    public TokenResponse() {
    }
}
