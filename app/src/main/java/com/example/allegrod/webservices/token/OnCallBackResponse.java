package com.example.allegrod.webservices.token;

public interface OnCallBackResponse<T> {
    void saveResponse(T token);
}
