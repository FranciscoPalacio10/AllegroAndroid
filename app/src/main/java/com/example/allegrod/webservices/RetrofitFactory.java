package com.example.allegrod.webservices;


import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.webservices.token.RetrofitTokenRequest;

public class RetrofitFactory {

    public static RetrofitRequest getRetrofit(String tipo) {
        switch (tipo){
            case AppConstant.TOKEN:
                return new RetrofitTokenRequest();
            default:
                return null;

        }
    }
}