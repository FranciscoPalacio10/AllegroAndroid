package com.example.allegrod.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.user.UserRequest;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.webservices.RetrofitFactory;
import com.example.allegrod.webservices.user.ApiRequestUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();
    private ApiRequestUser apiRequestUser;
    public UserRepository() {
        this.apiRequestUser = RetrofitFactory.getRetrofit(AppConstant.USERS).getRetrofitInstance().create(ApiRequestUser.class);
    }

    public LiveData<UserGetResponse> getUser(String email, String token) {
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


}
