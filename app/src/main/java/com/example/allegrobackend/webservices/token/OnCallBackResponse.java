package com.example.allegrobackend.webservices.token;

import com.example.allegrobackend.models.response.ApiResponse;

public interface OnCallBackResponse<T> {
    void saveResponse(ApiResponse<T> response);
}
