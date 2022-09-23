package com.example.allegroandroid.viewmodel;

import android.content.Context;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.allegroandroid.models.user.UserRequest;
import com.example.allegroandroid.models.user.create.UserCreateResponse;
import com.example.allegroandroid.models.user.get.UserResponse;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.UserRepository;

import java.util.Objects;

public class UserViewModel extends ViewModel {
    final MutableLiveData<UserRequest> userRequest;
    private UserRepository userRepositoryNew;
    private Context context;
    public UserViewModel(UserRepository repository, Context context){
        userRequest = new MutableLiveData<>();
        userRepositoryNew=repository;
        this.context=context;
    }

    public LiveData<Resource<UserResponse>> getUser(){
       return Transformations.switchMap(userRequest, new Function<UserRequest, LiveData<Resource<UserResponse>>>() {
            @Override
            public LiveData<Resource<UserResponse>> apply(UserRequest userRequest) {
                if(userRequest == null){
                    return null;
                }else{
                    return userRepositoryNew.loadUser(context, userRequest);
                }
            }
        });
    }

    public LiveData<Resource<UserCreateResponse>> postUser(){
        return Transformations.switchMap(userRequest, new Function<UserRequest, LiveData<Resource<UserCreateResponse>>>() {
            @Override
            public LiveData<Resource<UserCreateResponse>> apply(UserRequest userRequest) {
                if(userRequest == null){
                    return null;
                }else{
                    return userRepositoryNew.postUser(context, userRequest);
                }
            }
        });
    }

    public void retry(){
        UserRequest current = userRequest.getValue();
        if(current != null){
            userRequest.setValue(current);
        }
    }

    public void setUserRequest(UserRequest userRequest){
        if(Objects.equals(this.userRequest.getValue(), this.userRequest)){
            return;
        }
        this.userRequest.setValue(userRequest);
    }

}
