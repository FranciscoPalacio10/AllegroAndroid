package com.example.allegrod.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.allegrod.models.user.create.UserCreateResponse;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.repository.UserRepository;

public class UserViewModel extends AndroidViewModel{
    private UserRepository userRepository;


    public UserViewModel(@NonNull Application application, String token) {
        super(application);
        userRepository = new UserRepository(token);
    }

    public LiveData<UserGetResponse> getUserResponseLiveData(String email){
        return userRepository.getUser(email);
    }

}
