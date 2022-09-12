package com.example.allegrobackend.repository;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import android.content.Context;

import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.models.user.UserRequest;
import com.example.allegrobackend.models.user.get.UserResponse;
import com.example.allegrobackend.models.response.ApiResponse;
import com.example.allegrobackend.services.user.IUser;
import com.example.allegrobackend.services.user.UserRoomService;
import com.example.allegrobackend.services.user.UserServiceApi;
import com.example.allegrobackend.webservices.token.OnCallBackResponse;

public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();
    private IUser userService, userRoomService;
    private InitActitvy initActitvy;

    public UserRepository(InitActitvy initActitvy) {
        this.initActitvy = initActitvy;
    }

    public MutableLiveData<UserResponse> get(String email, String token) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        userRoomService = new UserRoomService(initActitvy.getContext());
        userService = new UserServiceApi(token);
        MutableLiveData<UserResponse> finalData = data;

        //consulto en la base de datos local, si no esta cargado el user voy a la api
        userRoomService.get(email, (OnCallBackResponse<UserResponse>) response -> {
            if (response.isSuccesful()) {
                finalData.postValue(response.getData());
            } else {
                getUserFromApi(finalData, email);
            }
        },initActitvy);
        return finalData;
    }

    private void getUserFromApi(MutableLiveData<UserResponse> finalData, String email) {
        if (finalData.getValue() == null) {
            userService.get(email, (OnCallBackResponse<UserResponse>) response -> {
                if (response.isSuccesful()) {
                    finalData.postValue(response.getData());
                    userRoomService.add(response.getData(),initActitvy);
                }
            },initActitvy);
        }
    }

    public MutableLiveData<UserResponse> createUser(UserRequest user) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        userService = new UserServiceApi();
        userService.add(user, response -> {
            if (response.isSuccesful()) {
                data.postValue(response.getData());
            }
        },initActitvy);
        return data;
    }
}
