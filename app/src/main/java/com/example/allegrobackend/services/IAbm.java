package com.example.allegrobackend.services;

import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.webservices.token.OnCallBackResponse;

public interface IAbm<I,K,L> {
    void get(K id, OnCallBackResponse<I> callback, InitActitvy initActitvy);
    void add(L entity,OnCallBackResponse<I> callback,InitActitvy initActitvy);
    void update(L entity,K id,OnCallBackResponse<I> callback,InitActitvy initActitvy);
}
