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
import com.example.allegroandroid.models.pointsxuser.PointXUserRequest;
import com.example.allegroandroid.models.pointsxuser.PointXUserResponse;
import com.example.allegroandroid.repository.HistorialDeClaseRepository;
import com.example.allegroandroid.repository.PointXUserRepository;
import com.example.allegroandroid.repository.resource.Resource;

import java.util.ArrayList;
import java.util.Objects;

public class PointXUserViewModel extends ViewModel {
    final MutableLiveData<PointXUserRequest> pointXUserRequest;
    private PointXUserRepository pointXUserRepository;
    private Context context;

    public PointXUserViewModel(PointXUserRepository pointXUserRepository, Context context) {
        pointXUserRequest = new MutableLiveData<>();
        this.pointXUserRepository = pointXUserRepository;
        this.context = context;
    }

    public LiveData<Resource<ArrayList<PointXUserResponse>>> getPointXUser(String email) {
        return pointXUserRepository.getPointXUser(context, email);
    }

    public void retry() {
        PointXUserRequest current = pointXUserRequest.getValue();
        if (current != null) {
            pointXUserRequest.setValue(current);
        }
    }

    public void setHistorialPointXUserRequest(PointXUserRequest pointXUserRequest) {
        if (Objects.equals(this.pointXUserRequest.getValue(), this.pointXUserRequest)) {
            return;
        }
        this.pointXUserRequest.setValue(pointXUserRequest);
    }

}
