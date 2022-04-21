package com.example.allegrod.webservices.token;

import com.example.allegrod.response.ApiResponse;

public interface OnCallBackResponse<T> {
    void saveResponse(ApiResponse<T> response);
}
