package com.example.allegrod.services.user;

import androidx.lifecycle.MutableLiveData;

import com.example.allegrod.models.user.User;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.webservices.token.OnCallBackResponse;

public class UserServiceFirebase implements IUser {
    public UserServiceFirebase() {
    }


    @Override
    public void get(String id, OnCallBackResponse<UserGetResponse> callback) {

    }

    @Override
    public void add(User entity, OnCallBackResponse<UserGetResponse> callback) {

    }

    @Override
    public void update(User entity, String id, OnCallBackResponse<UserGetResponse> callback) {

    }
}
