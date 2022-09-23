package com.example.allegroandroid.viewmodel;

import android.content.Context;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequest;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequestGet;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.models.user.User;
import com.example.allegroandroid.repository.HistorialDeClaseRepository;
import com.example.allegroandroid.repository.resource.Resource;

import java.util.ArrayList;
import java.util.Objects;

public class HistorialDeClasesViewModel extends ViewModel {
    private MutableLiveData<HistorialDeClaseRequest> historialDeClaseRequest;
    private MutableLiveData<HistorialDeClaseRequestGet> historialDeClaseRequestGet;
    private HistorialDeClaseRepository historialDeClaseRepository;
    private Context context;

    public HistorialDeClasesViewModel(HistorialDeClaseRepository historialDeClaseRepository, Context context) {
        historialDeClaseRequest = new MutableLiveData<>();
        historialDeClaseRequestGet = new MutableLiveData<>();
        this.historialDeClaseRepository = historialDeClaseRepository;
        this.context = context;
    }

    public LiveData<Resource<HistorialDeClaseResponse>> postHistorialDeClases(String email) {
        return Transformations.switchMap(historialDeClaseRequest, new Function<HistorialDeClaseRequest, LiveData<Resource<HistorialDeClaseResponse>>>() {
            @Override
            public LiveData<Resource<HistorialDeClaseResponse>> apply(HistorialDeClaseRequest historialDeClaseRequest) {
                if (historialDeClaseRequest == null) {
                    return null;
                } else {
                    return historialDeClaseRepository.postHistorialDeClases(context, historialDeClaseRequest, email);
                }
            }
        });
    }

    public LiveData<Resource<HistorialDeClaseResponse>> putHistorialDeClases(String email, int historialDeClaseId) {
        return Transformations.switchMap(historialDeClaseRequest, new Function<HistorialDeClaseRequest, LiveData<Resource<HistorialDeClaseResponse>>>() {
            @Override
            public LiveData<Resource<HistorialDeClaseResponse>> apply(HistorialDeClaseRequest historialDeClaseRequest) {
                if (historialDeClaseRequest == null) {
                    return null;
                } else {
                    return historialDeClaseRepository.putHistorialDeClases(context, historialDeClaseRequest, email, historialDeClaseId);
                }
            }
        });
    }


    public LiveData<Resource<ArrayList<HistorialDeClaseResponse>>> getHistorialDeClases() {
        return Transformations.switchMap(historialDeClaseRequestGet, new Function<HistorialDeClaseRequestGet, LiveData<Resource<ArrayList<HistorialDeClaseResponse>>>>() {
            @Override
            public LiveData<Resource< ArrayList<HistorialDeClaseResponse>>> apply(HistorialDeClaseRequestGet historialDeClaseRequest) {
                if (historialDeClaseRequest == null) {
                    return null;
                } else {
                    return historialDeClaseRepository.getHistorialDeClases(context, historialDeClaseRequest);
                }
            }
        });
    }

    public void retry() {
        HistorialDeClaseRequest current = historialDeClaseRequest.getValue();
        if (current != null) {
            historialDeClaseRequest.setValue(current);
        }
    }

    public void setHistorialClaseRequestPost(HistorialDeClaseRequest historialDeClaseRequest) {
        if (Objects.equals(this.historialDeClaseRequest.getValue(), this.historialDeClaseRequest)) {
            return;
        }
        this.historialDeClaseRequest.setValue(historialDeClaseRequest);
    }

    public void setHistorialClaseRequestGet(HistorialDeClaseRequestGet historialDeClaseRequestGet) {
        if (Objects.equals(this.historialDeClaseRequestGet.getValue(), this.historialDeClaseRequestGet)) {
            return;
        }
        this.historialDeClaseRequestGet.setValue(historialDeClaseRequestGet);
    }
}
