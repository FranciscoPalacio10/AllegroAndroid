package com.example.allegrobackend.webservices;


import com.example.allegrobackend.constants.AppConstant;
import com.example.allegrobackend.webservices.token.RetrofitTokenRequest;
import com.example.allegrobackend.webservices.user.allegroback.RetrofitUserRequest;

public class RetrofitFactory {

    public static RetrofitRequest getRetrofit(String tipo) {
        switch (tipo){
            case AppConstant.TOKEN:
                return new RetrofitTokenRequest();
            case AppConstant.USERS:
                return new RetrofitUserRequest();
            default:
                return null;

        }
    }
}