package com.example.allegroandroid.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.example.allegroandroid.repository.resource.NetworkBoundResource;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.localdb.AllegroLocalDb;
import com.example.allegroandroid.models.user.UserRequest;
import com.example.allegroandroid.models.user.create.UserCreateResponse;
import com.example.allegroandroid.models.user.get.UserResponse;
import com.example.allegroandroid.services.DateService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.webservices.user.allegroback.ApiRequestUser;

public class UserRepository {

    private final AppExecutors appExecutors;
    private final ApiRequestUser apiRequestUser;
    private SessionService sessionService;
    private RoomDatabase.Builder<AllegroLocalDb> provideDb;


    public UserRepository(AppExecutors appExecutors, ApiRequestUser apiRequestUser,
                          RoomDatabase.Builder<AllegroLocalDb> provideDb, SessionService sessionService) {
        this.provideDb = provideDb;
        this.apiRequestUser = apiRequestUser;
        this.appExecutors = appExecutors;
        this.sessionService = sessionService;
    }

    public LiveData<Resource<UserResponse>> loadUser(Context context, UserRequest userRequest) {
        return new NetworkBoundResource<UserResponse, UserRequest>(appExecutors, userRequest, context) {
            @Override
            protected boolean shouldFetch(UserResponse data) {
                return data == null;
            }

            @Override
            protected UserResponse loadFromDb() {
                UserResponse user = provideDb.allowMainThreadQueries().build().userDao().findByEmail(userRequest.email);
                if (user != null) {
                    if(!DateService.getInstance().isPassedNHoursFromLastTimeRequested(user.dateUpdate, 24)){
                        return user;
                    }
                }
                return null;
            }

            @Override
            protected void saveInLocalStore(UserResponse item) {
                item.dateUpdate = DateService.getInstance().getDateNow();
                provideDb.build().userDao().insertAll(item);
            }

            @Override
            protected UserResponse loadFromSharedPreferences() {
                return null;
            }

            @Override
            protected void createCall() {
                String token = sessionService.GetBearerToken(userRequest.email).token;
                callApi(apiRequestUser.getUser(userRequest, token));
            }

        }.asLiveData();
    }


    public LiveData<Resource<UserCreateResponse>> postUser(Context context, UserRequest userRequest) {
        return new NetworkBoundResource<UserCreateResponse, UserRequest>(appExecutors, userRequest, context) {
            @Override
            protected boolean shouldFetch(UserCreateResponse data) {
                return data == null;
            }

            @Override
            protected UserCreateResponse loadFromDb() {
                return null;
            }

            @Override
            protected void saveInLocalStore(UserCreateResponse item) {
            }

            @Override
            protected UserCreateResponse loadFromSharedPreferences() {
                return null;
            }

            @Override
            protected void createCall() {
                callApi(apiRequestUser.createUser(userRequest));
            }

        }.asLiveData();
    }


}
