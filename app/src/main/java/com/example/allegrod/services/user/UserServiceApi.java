package com.example.allegrod.services.user;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.models.user.User;
import com.example.allegrod.models.user.create.UserCreateResponse;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.webservices.RetrofitFactory;
import com.example.allegrod.webservices.token.OnCallBackResponse;
import com.example.allegrod.webservices.user.allegroback.ApiRequestUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserServiceApi implements IUser {
    private ApiRequestUser apiRequestUser;
    private String TAG= "UserService";
    private String token;
    public UserServiceApi(String token) {
        this.apiRequestUser = RetrofitFactory.getRetrofit(AppConstant.USERS).getRetrofitInstance().create(ApiRequestUser.class);
        this.token = token;
    }

    public UserServiceApi() {
        this.apiRequestUser = RetrofitFactory.getRetrofit(AppConstant.USERS).getRetrofitInstance().create(ApiRequestUser.class);
    }

    @Override
    public void get(String id, OnCallBackResponse<UserGetResponse> callback) {
        MutableLiveData<UserGetResponse> data = new MutableLiveData<>();
        apiRequestUser.getUser(new User(id), token).enqueue(new Callback<ApiResponse<UserGetResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserGetResponse>> call, Response<ApiResponse<UserGetResponse>> response) {
                callback.saveResponse(new ApiResponse<UserGetResponse>(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<UserGetResponse>> call, Throwable t) {
               callback.saveResponse(new ApiResponse<UserGetResponse>(t));
            }
        });
    }

    @Override
    public void add(User entity, OnCallBackResponse<UserGetResponse> callback) {
        apiRequestUser.createUser(entity).enqueue(new Callback<ApiResponse<UserCreateResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserCreateResponse>> call, Response<ApiResponse<UserCreateResponse>> response) {
                UserCreateResponse userCreateResponse= response.body().getData();
                UserGetResponse userGetResponse = new UserGetResponse(userCreateResponse.email, userCreateResponse.firstName,
                        userCreateResponse.lastName, userCreateResponse.rol);
                callback.saveResponse(new ApiResponse<UserGetResponse>(userGetResponse));
            }

            @Override
            public void onFailure(Call<ApiResponse<UserCreateResponse>> call, Throwable t) {
                callback.saveResponse(new ApiResponse<UserGetResponse>(t));
            }
        });
    }

    @Override
    public void update(User entity, String id, OnCallBackResponse<UserGetResponse> callback) {

    }
}
