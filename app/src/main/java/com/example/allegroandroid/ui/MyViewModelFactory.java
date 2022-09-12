package com.example.allegroandroid.ui;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.allegroandroid.repository.ClaseRepository;
import com.example.allegroandroid.repository.HistorialDeClaseRepository;
import com.example.allegroandroid.repository.TokenRepository;
import com.example.allegroandroid.repository.UserRepository;
import com.example.allegroandroid.viewmodel.ClasesViewModelNew;
import com.example.allegroandroid.viewmodel.HistorialDeClasesViewModel;
import com.example.allegroandroid.viewmodel.TokenViewModelNew;
import com.example.allegroandroid.viewmodel.UserViewModel;


public class MyViewModelFactory<T> extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private T mParams;

    public MyViewModelFactory(Application application, T t) {
        mApplication = application;
        mParams = t;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == TokenViewModelNew.class) {
            return (T) new TokenViewModelNew((TokenRepository) mParams, mApplication.getApplicationContext());
        } else if (modelClass == UserViewModel.class) {
            return (T) new UserViewModel((UserRepository) mParams, mApplication.getApplicationContext());
        }  else if (modelClass == ClasesViewModelNew.class) {
            return (T) new ClasesViewModelNew((ClaseRepository) mParams, mApplication.getApplicationContext());
        }  else if (modelClass == HistorialDeClasesViewModel.class) {
            return (T) new HistorialDeClasesViewModel((HistorialDeClaseRepository) mParams, mApplication.getApplicationContext());
        }
        else {
            return super.create(modelClass);
        }
    }
}