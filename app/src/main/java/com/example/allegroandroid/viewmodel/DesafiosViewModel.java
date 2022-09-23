package com.example.allegroandroid.viewmodel;

import android.content.Context;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.allegroandroid.models.clase.ClaseRequest;
import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.desafios.DesafioRequest;
import com.example.allegroandroid.models.desafios.DesafiosXUserResponse;
import com.example.allegroandroid.models.pointsxuser.PointXUserRequest;
import com.example.allegroandroid.models.pointsxuser.PointXUserResponse;
import com.example.allegroandroid.repository.DesafiosRepository;
import com.example.allegroandroid.repository.PointXUserRepository;
import com.example.allegroandroid.repository.resource.Resource;

import java.util.ArrayList;
import java.util.Objects;

public class DesafiosViewModel extends ViewModel {
    final MutableLiveData<DesafioRequest> desafioRequest;
    private DesafiosRepository desafiosRepository;
    private Context context;

    public DesafiosViewModel(DesafiosRepository desafiosRepository, Context context) {
        desafioRequest = new MutableLiveData<>();
        this.desafiosRepository = desafiosRepository;
        this.context = context;
    }

    public LiveData<Resource<ArrayList<DesafiosXUserResponse>>> getDesafiosXUser() {
        return Transformations.switchMap(desafioRequest, new Function<DesafioRequest, LiveData<Resource< ArrayList<DesafiosXUserResponse>>>>() {
            @Override
            public LiveData<Resource<ArrayList<DesafiosXUserResponse>>> apply(DesafioRequest desafioRequest) {
                if (desafioRequest == null) {
                    return null;
                } else {
                    return desafiosRepository.getDesafiosByUser(context, desafioRequest);
                }
            }
        });
    }

    public void retry() {
        DesafioRequest current = desafioRequest.getValue();
        if (current != null) {
            desafioRequest.setValue(current);
        }
    }

    public void setDesafioRequest(DesafioRequest desafioRequest) {
        if (Objects.equals(this.desafioRequest.getValue(), this.desafioRequest)) {
            return;
        }
        this.desafioRequest.setValue(desafioRequest);
    }

}
