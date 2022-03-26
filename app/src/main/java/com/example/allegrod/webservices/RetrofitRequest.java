package com.example.allegrod.webservices;

import retrofit2.Retrofit;

public abstract class RetrofitRequest {
    public Retrofit retrofit;
    public abstract Retrofit getRetrofitInstance();
}
