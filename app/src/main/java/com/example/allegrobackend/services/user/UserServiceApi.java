package com.example.allegrobackend.services.user;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.allegrobackend.constants.AppConstant;
import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.models.user.User;
import com.example.allegrobackend.models.user.UserRequest;
import com.example.allegrobackend.models.user.create.UserCreateResponse;
import com.example.allegrobackend.models.user.get.UserResponse;
import com.example.allegrobackend.models.response.ApiResponse;
import com.example.allegrobackend.repository.TokenRepository;
import com.example.allegrobackend.services.ActivitiesInitiator;
import com.example.allegrobackend.webservices.RetrofitFactory;
import com.example.allegrobackend.webservices.token.OnCallBackResponse;
import com.example.allegrobackend.webservices.user.allegroback.ApiRequestUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserServiceApi implements IUser {
    private ApiRequestUser apiRequestUser;
    private String TAG= TokenRepository.class.getSimpleName();
    private String token;
    public UserServiceApi(String token) {
        this.apiRequestUser = RetrofitFactory.getRetrofit(AppConstant.USERS).getRetrofitInstance().create(ApiRequestUser.class);
        this.token = token;
    }

    public UserServiceApi() {
        this.apiRequestUser = RetrofitFactory.getRetrofit(AppConstant.USERS).getRetrofitInstance().create(ApiRequestUser.class);
    }

    @Override
    public void get(String id, OnCallBackResponse<UserResponse> callback,InitActitvy initActitvy) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiRequestUser.getUser(new UserRequest(id), token).enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                callback.saveResponse(new ApiResponse<UserResponse>(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable t) {
                callback.saveResponse(new ApiResponse<UserResponse>(t));
                Bundle pBundle = getBundle(t,initActitvy);
                initActitvy.setBundle(pBundle);
                ActivitiesInitiator.initErrorActivity(initActitvy);
            }
        });
    }

    @Override
    public void add(UserRequest entity, OnCallBackResponse<UserResponse> callback,InitActitvy initActitvy) {
        apiRequestUser.createUser(entity).enqueue(new Callback<ApiResponse<UserCreateResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserCreateResponse>> call, Response<ApiResponse<UserCreateResponse>> response) {
                if(response.isSuccessful()){
                    UserCreateResponse userCreateResponse= response.body().getData();
                    UserResponse userGetResponse = new UserResponse(new User(userCreateResponse.email,userCreateResponse.firstName,
                            userCreateResponse.role,userCreateResponse.dateOfBirth,userCreateResponse.userName));
                    callback.saveResponse(new ApiResponse<UserResponse>(userGetResponse));
                }else{
                    callback.saveResponse(new ApiResponse<UserResponse>(response.message(),response.code()));
                }

            }

            @Override
            public void onFailure(Call<ApiResponse<UserCreateResponse>> call, Throwable t) {
                callback.saveResponse(new ApiResponse<UserResponse>(t));
                Bundle pBundle = getBundle(t,initActitvy);
                initActitvy.setBundle(pBundle);
                ActivitiesInitiator.initErrorActivity(initActitvy);
            }
        });
    }

    @Override
    public void update(UserRequest entity, String id, OnCallBackResponse<UserResponse> callback,InitActitvy initActitvy) {

    }

    @Override
    public void add(UserResponse entity,InitActitvy initActitvy) {

    }
    @NonNull
    private Bundle getBundle(Throwable t,InitActitvy initActitvy) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t.getMessage());
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR,this.TAG);
        return pBundle;
    }
}
