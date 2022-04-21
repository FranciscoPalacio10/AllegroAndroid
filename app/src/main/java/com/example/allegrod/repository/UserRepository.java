package com.example.allegrod.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.allegrod.models.user.User;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.services.user.IUser;
import com.example.allegrod.services.user.UserServiceApi;
import com.example.allegrod.services.user.UserServiceFirebase;
import com.example.allegrod.webservices.token.OnCallBackResponse;

public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();
    private IUser userService,userFirebase;
    public UserRepository() {
    }

    public MutableLiveData<UserGetResponse> get(String email,String token) {
        MutableLiveData<UserGetResponse> data = new MutableLiveData<>();
        userService= new UserServiceApi(token);
        userFirebase = new UserServiceFirebase();
        MutableLiveData<UserGetResponse> finalData = data;

        userFirebase.get(email, new OnCallBackResponse<UserGetResponse>() {
            @Override
            public void saveResponse(ApiResponse<UserGetResponse> response) {
                if(response.isSuccesful()){
                    finalData.setValue(response.getData());
                }else{
                    Log.e(TAG,response.getErrorMessage());
                }
            }
        });

        if(data.getValue() == null){
            userService.get(email, new OnCallBackResponse<UserGetResponse>() {
                @Override
                public void saveResponse(ApiResponse<UserGetResponse> response) {
                    if(response.isSuccesful()){
                        finalData.setValue(response.getData());
                    }else{
                        Log.e(TAG,response.getErrorMessage());
                    }
                }
            });
        }
        return finalData;
    }

    public MutableLiveData<UserGetResponse> createUser(User user) {
        MutableLiveData<UserGetResponse> data = new MutableLiveData<>();
        userService= new UserServiceApi();
        userService.add(user, response -> {
            if(response.isSuccesful()){
                data.setValue(response.getData());
            }else{
                Log.e(TAG,response.getErrorMessage());
            }
        });
        return data;
    }





}
