package com.example.allegroandroid.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.example.allegroandroid.localdb.AllegroLocalDb;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequest;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequestGet;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.models.pointsxuser.PointXUserRequest;
import com.example.allegroandroid.models.pointsxuser.PointXUserResponse;
import com.example.allegroandroid.repository.resource.NetworkBoundResource;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.services.DateService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.webservices.historialdeclases.ApiRequestHistorialDeClases;
import com.example.allegroandroid.webservices.pointxuser.ApiRequestPointXUser;

import java.util.ArrayList;
import java.util.List;

public class PointXUserRepository {
    private final AppExecutors appExecutors;
    private final ApiRequestPointXUser apiRequestPointXUser;
    private RoomDatabase.Builder<AllegroLocalDb> provideDb;
    private SessionService sessionService;

    public PointXUserRepository(AppExecutors appExecutors, ApiRequestPointXUser apiRequestPointXUser,
                                RoomDatabase.Builder<AllegroLocalDb> provideDb, SessionService sessionService) {
        this.provideDb = provideDb;
        this.apiRequestPointXUser = apiRequestPointXUser;
        this.appExecutors = appExecutors;
        this.sessionService = sessionService;
    }

/*    public LiveData<Resource<PointXUserResponse>> postPointXUser(Context context, PointXUserRequest pointXUserRequest, String email) {
        return new NetworkBoundResource<PointXUserResponse, PointXUserRequest>(appExecutors, pointXUserRequest, context) {

            @Override
            protected boolean shouldFetch(PointXUserResponse data) {
                return true;
            }

            @Override
            protected PointXUserResponse loadFromDb() {
                return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveInLocalStore(PointXUserResponse item) {
                PointXUserResponse pointXUserResponse = provideDb.allowMainThreadQueries().build().pointsXUserDao()
                        .findByUserId(item.userIdPointXUser);
                if (pointXUserResponse != null) {
                    item.dateUpdate = DateService.getInstance().getDateNow();
                    provideDb.build().pointsXUserDao().insert(item);
                }
            }

            @Override
            protected PointXUserResponse loadFromSharedPreferences() {
                return null;
            }

            @Override
            protected void createCall() {
                callApi(apiRequestPointXUser.createPointXUser(pointXUserRequest,
                        sessionService.GetBearerToken(email).token));
            }
        }.asLiveData();
    }*/

    public LiveData<Resource<ArrayList<PointXUserResponse>>> getPointXUser(Context context, String email) {
        return new NetworkBoundResource<ArrayList<PointXUserResponse>, PointXUserRequest>(appExecutors, new PointXUserRequest(), context) {

            @Override
            protected boolean shouldFetch(ArrayList<PointXUserResponse> data) {
                return true;
            }

            @Override
            protected ArrayList<PointXUserResponse> loadFromDb() {
                return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveInLocalStore(ArrayList<PointXUserResponse> items) {
                items.forEach(x -> {
                    x.dateUpdate = DateService.getInstance().getDateNow();
                });

                provideDb.build().pointsXUserDao().insertAll(items);
            }

            @Override
            protected ArrayList<PointXUserResponse> loadFromSharedPreferences() {
                return null;
            }

            @Override
            protected void createCall() {
                callApi(apiRequestPointXUser.getPointXUserById(sessionService.GetBearerToken(email).token));
            }
        }.asLiveData();
    }
}

