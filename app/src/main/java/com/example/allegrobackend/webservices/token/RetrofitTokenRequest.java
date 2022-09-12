package com.example.allegrobackend.webservices.token;



import com.example.allegrobackend.constants.AppConstant;
import com.example.allegrobackend.webservices.RetrofitRequest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTokenRequest extends RetrofitRequest {
    public Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.BASE_URL_USER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
