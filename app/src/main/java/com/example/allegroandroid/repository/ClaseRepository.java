package com.example.allegroandroid.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.example.allegroandroid.repository.resource.NetworkBoundResource;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.localdb.AllegroLocalDb;
import com.example.allegroandroid.models.clase.ClaseRequest;
import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.services.DateService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.webservices.clases.ApiRequestClases;

import java.util.ArrayList;
import java.util.List;

public class ClaseRepository {

    private final AppExecutors appExecutors;
    private final ApiRequestClases apiRequestClases;
    private SessionService sessionService;
    private RoomDatabase.Builder<AllegroLocalDb> provideDb;


    public ClaseRepository(AppExecutors appExecutors, ApiRequestClases apiRequestClases,
                           RoomDatabase.Builder<AllegroLocalDb> provideDb, SessionService sessionService) {
        this.provideDb = provideDb;
        this.apiRequestClases = apiRequestClases;
        this.appExecutors = appExecutors;
        this.sessionService = sessionService;
    }


    public LiveData<Resource<ArrayList<ClaseResponse>>> getClasesByTypeAndMateria(Context context, ClaseRequest claseRequest) {
        return new NetworkBoundResource< ArrayList<ClaseResponse>, ClaseRequest>(appExecutors, claseRequest, context) {
            @Override
            protected boolean shouldFetch( ArrayList<ClaseResponse> data) {
                return data == null;
            }

            @Override
            protected  ArrayList<ClaseResponse> loadFromDb() {
                List<ClaseResponse> clasesResponse = provideDb.allowMainThreadQueries().build().claseDao().findByTipoAndMateria(claseRequest.tipo, claseRequest.materiaId);
                if (clasesResponse != null && !clasesResponse.isEmpty()) {
                    if (!DateService.getInstance().isPassedNHoursFromLastTimeRequested(clasesResponse.get(0).dateUpdate, 1)) {
                        return (ArrayList<ClaseResponse>) clasesResponse;
                    }
                }
                return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveInLocalStore( ArrayList<ClaseResponse> items) {
                items.forEach(x -> {
                    x.dateUpdate = DateService.getInstance().getDateNow();
                });

                provideDb.build().claseDao().insertAll(items);
            }

            @Override
            protected  ArrayList<ClaseResponse> loadFromSharedPreferences() {
                return null;
            }

            @Override
            protected void createCall() {
                String token = sessionService.GetBearerToken(claseRequest.user.email).token;
                callApi(apiRequestClases.geClasesByTypeAndMateriaId(claseRequest.tipo, claseRequest.materiaId, token));
            }

        }.asLiveData();
    }

}
