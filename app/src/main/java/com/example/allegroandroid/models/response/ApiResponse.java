package com.example.allegroandroid.models.response;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {

    private int status;
    private T data;
    private Paging paging;
    private String errorMessage;

    public ApiResponse(T data){
        this.data = data;
        status=200;
    }


    public ApiResponse(String errorBody,int code){
        this.errorMessage = errorBody;
        status=code;
    }

    public ApiResponse(T data,int status){
        this.data = data;
        this.status=status;
    }

    public ApiResponse(@NonNull Response<ApiResponse<T>> response) {
        status = response.code();
        if(response.isSuccessful()){
            data =  response.body().data;
            errorMessage = null;
            paging = response.body().paging;
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

    public ApiResponse(@NonNull Throwable error) {
        status = 500;
        errorMessage = error.getMessage();
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status=" + status +
                ", data=" + data +
                ", paging=" + paging +
                ", errorMessage='" + errorMessage + '\'' +

                '}';
    }

    public boolean isSuccesful(){
        return status >= 200 && status < 300;
    }


}
