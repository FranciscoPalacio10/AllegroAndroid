package com.example.allegrod.response;

import android.util.Log;

import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {

    private  int status;
    private T data;

    public ApiResponse(T data){
        this.data = data;
        status=200;
    }


    public ApiResponse(Response<ApiResponse<T>> response) {
        status = response.code();
        if(response.isSuccessful()){
            data =  response.body().data;
            errorMessage = null;
        }else {
            String message = null;
            if(response.errorBody() != null){
                try{
                    message = response.errorBody().string();
                }catch (IOException ignored){
                    Log.d(ignored.getMessage(), "Error while parsing response");
                }

            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            data = null;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private  String errorMessage;



    public ApiResponse(Throwable error) {
        status = 500;
        errorMessage = error.getMessage();
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status=" + status +
                ", data=" + data +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }


}
