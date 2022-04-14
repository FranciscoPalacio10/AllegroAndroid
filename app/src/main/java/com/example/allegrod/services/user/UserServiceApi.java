package com.example.allegrod.services.user;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.user.UserRequest;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.webservices.RetrofitFactory;
import com.example.allegrod.webservices.user.allegroback.ApiRequestUser;

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

    @Override
    public MutableLiveData<UserGetResponse> get(String email) {
        MutableLiveData<UserGetResponse> data = new MutableLiveData<>();
        apiRequestUser.getUser(new UserRequest(email), token).enqueue(new Callback<ApiResponse<UserGetResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserGetResponse>> call, Response<ApiResponse<UserGetResponse>> response) {
                data.setValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<UserGetResponse>> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
        return data;
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
