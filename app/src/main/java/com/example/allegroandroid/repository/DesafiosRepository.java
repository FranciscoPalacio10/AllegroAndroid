package com.example.allegroandroid.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.allegroandroid.models.clase.ClaseRequest;
import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.desafios.DesafioRequest;
import com.example.allegroandroid.models.desafios.DesafiosXUserResponse;
import com.example.allegroandroid.repository.resource.NetworkBoundResource;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.services.DateService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.webservices.desafios.ApiRequestDesafios;

import java.util.ArrayList;
import java.util.List;

public class DesafiosRepository {

    private final AppExecutors appExecutors;
    private final ApiRequestDesafios apiRequestDesafios;
    private SessionService sessionService;


    public DesafiosRepository(AppExecutors appExecutors, ApiRequestDesafios apiRequestDesafios, SessionService sessionService) {
        this.apiRequestDesafios = apiRequestDesafios;
        this.appExecutors = appExecutors;
        this.sessionService = sessionService;
    }


    public LiveData<Resource<ArrayList<DesafiosXUserResponse>>> getDesafiosByUser(Context context, DesafioRequest desafioRequest) {
        return new NetworkBoundResource< ArrayList<DesafiosXUserResponse>, DesafioRequest>(appExecutors, desafioRequest, context) {
            @Override
            protected boolean shouldFetch( ArrayList<DesafiosXUserResponse> data) {
                return data == null;
            }

            @Override
            protected  ArrayList<DesafiosXUserResponse> loadFromDb() {
                return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveInLocalStore( ArrayList<DesafiosXUserResponse> items) {

            }

            @Override
            protected  ArrayList<DesafiosXUserResponse> loadFromSharedPreferences() {
                return null;
            }

            @Override
            protected void createCall() {
                String token = sessionService.GetBearerToken(desafioRequest.email).token;
                callApi(apiRequestDesafios.getDesafiosXUser(token));
            }

        }.asLiveData();
    }

}
