package com.example.allegroandroid.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.example.allegroandroid.localdb.AllegroLocalDb;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequestGet;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequest;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.models.user.User;
import com.example.allegroandroid.repository.resource.NetworkBoundResource;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.services.DateService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.webservices.historialdeclases.ApiRequestHistorialDeClases;

import java.util.ArrayList;
import java.util.List;

public class HistorialDeClaseRepository {
    private final AppExecutors appExecutors;
    private final ApiRequestHistorialDeClases apiRequestHistorialDeClases;
    private RoomDatabase.Builder<AllegroLocalDb> provideDb;
    private SessionService sessionService;

    public HistorialDeClaseRepository(AppExecutors appExecutors, ApiRequestHistorialDeClases apiRequestHistorialDeClases,
                                      RoomDatabase.Builder<AllegroLocalDb> provideDb, SessionService sessionService) {
        this.provideDb = provideDb;
        this.apiRequestHistorialDeClases = apiRequestHistorialDeClases;
        this.appExecutors = appExecutors;
        this.sessionService = sessionService;
    }

    public LiveData<Resource<HistorialDeClaseResponse>> postHistorialDeClases(Context context,
                                                                              HistorialDeClaseRequest historialDeClaseRequest, String email) {
        return new NetworkBoundResource<HistorialDeClaseResponse, HistorialDeClaseRequest>(appExecutors, historialDeClaseRequest, context) {

            @Override
            protected boolean shouldFetch(HistorialDeClaseResponse data) {
                return true;
            }

            @Override
            protected HistorialDeClaseResponse loadFromDb() {
                return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveInLocalStore(HistorialDeClaseResponse item) {
                List<HistorialDeClaseResponse> historialDeClasesResponse = provideDb.allowMainThreadQueries().build().historialDeClasesDao()
                        .findByUserId(item.userIdHistorialDeClase);
                if (historialDeClasesResponse != null){
                    item.dateUpdate = DateService.getInstance().getDateNow();
                    provideDb.build().historialDeClasesDao().update(item);
                }
            }

            @Override
            protected HistorialDeClaseResponse loadFromSharedPreferences() {
                return null;
            }

            @Override
            protected void createCall() {
                callApi(apiRequestHistorialDeClases.createHistorialDeClase(historialDeClaseRequest,
                        sessionService.GetBearerToken(email).token));
            }
        }.asLiveData();
    }

    public LiveData<Resource<HistorialDeClaseResponse>> putHistorialDeClases(Context context,
                                                                             HistorialDeClaseRequest historialDeClaseRequest,
                                                                             String email, int historialDeClaseId) {
        return new NetworkBoundResource<HistorialDeClaseResponse, HistorialDeClaseRequest>(appExecutors, historialDeClaseRequest, context) {

            @Override
            protected boolean shouldFetch(HistorialDeClaseResponse data) {
                return true;
            }

            @Override
            protected HistorialDeClaseResponse loadFromDb() {
                return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveInLocalStore(HistorialDeClaseResponse item) {
                List<HistorialDeClaseResponse> historialDeClasesResponse = provideDb.allowMainThreadQueries().build().historialDeClasesDao()
                        .findByUserId(item.userIdHistorialDeClase);
                if (historialDeClasesResponse != null){
                    item.dateUpdate = DateService.getInstance().getDateNow();
                    provideDb.build().historialDeClasesDao().update(item);
                }
            }

            @Override
            protected HistorialDeClaseResponse loadFromSharedPreferences() {
                return null;
            }

            @Override
            protected void createCall() {
                callApi(apiRequestHistorialDeClases.UpdateHistorialDeClase(historialDeClaseId, historialDeClaseRequest,
                        sessionService.GetBearerToken(email).token));
            }
        }.asLiveData();
    }

    public LiveData<Resource<ArrayList<HistorialDeClaseResponse>>> getHistorialDeClases(Context context,
                                                                                        HistorialDeClaseRequestGet historialDeClaseGet) {
        return new NetworkBoundResource<ArrayList<HistorialDeClaseResponse>, HistorialDeClaseRequestGet>(appExecutors, historialDeClaseGet, context) {
            @Override
            protected boolean shouldFetch(ArrayList<HistorialDeClaseResponse> data) {
                return data == null;
            }

            @Override
            protected ArrayList<HistorialDeClaseResponse> loadFromDb() {
                List<HistorialDeClaseResponse> historialDeClasesResponse = provideDb.allowMainThreadQueries().build().historialDeClasesDao()
                        .findByUserId(historialDeClaseGet.user.id);
                if (historialDeClasesResponse != null && !historialDeClasesResponse.isEmpty()) {
                    if (!DateService.getInstance().isPassedNHoursFromLastTimeRequested(historialDeClasesResponse.get(0).dateUpdate, 1)
                    && historialDeClasesResponse.get(0).clase.materia != null) {
                        return (ArrayList<HistorialDeClaseResponse>) historialDeClasesResponse;
                    }
                }
                return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveInLocalStore(ArrayList<HistorialDeClaseResponse> items) {
                items.forEach(x -> {
                    x.dateUpdate = DateService.getInstance().getDateNow();
                });

                provideDb.build().historialDeClasesDao().insertAll(items);
            }

            @Override
            protected ArrayList<HistorialDeClaseResponse> loadFromSharedPreferences() {
                return null;
            }

            @Override
            protected void createCall() {
                callApi(apiRequestHistorialDeClases.getHistorialDeClasesByUserId(historialDeClaseGet.taken,
                        historialDeClaseGet.pages, sessionService.GetBearerToken(historialDeClaseGet.user.email).token));
            }
        }.asLiveData();


    }
}
