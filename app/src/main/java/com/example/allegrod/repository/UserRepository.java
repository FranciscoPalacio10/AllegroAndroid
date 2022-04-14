package com.example.allegrod.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.user.UserRequest;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.services.user.IUser;
import com.example.allegrod.services.user.UserServiceApi;
import com.example.allegrod.services.user.UserServiceFirebase;
import com.example.allegrod.webservices.RetrofitFactory;
import com.example.allegrod.webservices.user.allegroback.ApiRequestUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();
    private IUser userService,userFirebase;
    public UserRepository() {
    }

    public MutableLiveData<UserGetResponse> get(String email,String token) {
        MutableLiveData<UserGetResponse> data = new MutableLiveData<>();
        userService= new UserServiceApi(token);
        userFirebase = new UserServiceFirebase();
        data = userFirebase.get(email);

        if(data.getValue() == null){
            data = userService.get(email);
        }


        return data;
    }
}
