package com.example.allegrod.services.user;

import androidx.lifecycle.MutableLiveData;

import com.example.allegrod.models.user.get.UserGetResponse;

public class UserServiceFirebase implements IUser {
    public UserServiceFirebase() {
    }

    @Override
    public MutableLiveData<UserGetResponse> get(String id) {
        return null;
    }

    @Override
    public MutableLiveData<UserGetResponse> add(UserGetResponse entity) {
        return null;
    }

    @Override
    public MutableLiveData<UserGetResponse> update(UserGetResponse entity, String id) {
        return null;
    }
}
