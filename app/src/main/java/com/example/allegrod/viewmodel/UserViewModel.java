package com.example.allegrod.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.allegrod.models.user.User;
import com.example.allegrod.models.user.create.UserCreateResponse;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.repository.TokenRepository;
import com.example.allegrod.repository.UserRepository;
import com.example.allegrod.services.SessionService;

public class UserViewModel extends AndroidViewModel{
    private UserRepository userRepository;
    private SessionService sessionService;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
        sessionService = new SessionService(application.getApplicationContext());
    }

    public LiveData<UserGetResponse> getUserResponseLiveData(String email){
        return userRepository.get(email,sessionService.GetBearerToken(email).token);
    }

    public LiveData<UserGetResponse> createUser(User user){
        return userRepository.createUser(user);
    }

}
