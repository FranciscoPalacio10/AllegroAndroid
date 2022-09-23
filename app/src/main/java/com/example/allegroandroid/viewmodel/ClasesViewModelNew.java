package com.example.allegroandroid.viewmodel;

import android.content.Context;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.allegroandroid.models.clase.ClaseRequest;
import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.repository.ClaseRepository;
import com.example.allegroandroid.repository.resource.Resource;

import java.util.ArrayList;
import java.util.Objects;

public class ClasesViewModelNew extends ViewModel {
    final MutableLiveData<ClaseRequest> claseRequest;
    private ClaseRepository claseRepository;
    private Context context;

    public ClasesViewModelNew(ClaseRepository claseRepository, Context context) {
        claseRequest = new MutableLiveData<>();
        this.claseRepository = claseRepository;
        this.context = context;
    }

    public LiveData<Resource<ArrayList<ClaseResponse>>> getClasesByTypeAndMateria() {
        return Transformations.switchMap(claseRequest, new Function<ClaseRequest, LiveData<Resource< ArrayList<ClaseResponse>>>>() {
            @Override
            public LiveData<Resource< ArrayList<ClaseResponse>>> apply(ClaseRequest claseRequest) {
                if (claseRequest == null) {
                    return null;
                } else {
                    return claseRepository.getClasesByTypeAndMateria(context, claseRequest);
                }
            }
        });
    }

    public void retry() {
        ClaseRequest current = claseRequest.getValue();
        if (current != null) {
            claseRequest.setValue(current);
        }
    }

    public void setClaseRequest(ClaseRequest claseRequest) {
        if (Objects.equals(this.claseRequest.getValue(), this.claseRequest)) {
            return;
        }
        this.claseRequest.setValue(claseRequest);
    }

}
