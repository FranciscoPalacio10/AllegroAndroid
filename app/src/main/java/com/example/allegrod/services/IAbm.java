package com.example.allegrod.services;

import androidx.lifecycle.MutableLiveData;

import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.webservices.token.OnCallBackResponse;

public interface IAbm<I,K,L> {
    void get(K id, OnCallBackResponse<I> callback);
    void add(L entity,OnCallBackResponse<I> callback);
    void update(L entity,K id,OnCallBackResponse<I> callback);
}
