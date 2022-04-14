package com.example.allegrod.services;

import androidx.lifecycle.MutableLiveData;

public interface IAbm<I,K> {
    MutableLiveData<I> get(K id);
    MutableLiveData<I> add(I entity);
    MutableLiveData<I> update(I entity,K id);
}
