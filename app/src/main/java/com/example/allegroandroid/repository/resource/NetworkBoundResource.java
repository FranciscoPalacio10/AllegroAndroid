package com.example.allegroandroid.repository.resource;

import android.content.Context;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;


import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.models.response.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final AppExecutors appExecutors;
    private RequestType requestType;
    private final MediatorLiveData<Resource<ResultType>> result;

    @MainThread
    protected NetworkBoundResource(AppExecutors appExecutors, RequestType requestType, Context context) {
        this.appExecutors = appExecutors;
        this.requestType = requestType;
        result = new MediatorLiveData<>();
        result.setValue(Resource.loading(null));

        ResultType results = loadFromDb();

        if (results != null) {
            NetworkBoundResource.this.postValue(Resource.success(results));
        }else if(NetworkBoundResource.this.shouldFetch(result.getValue().data)) {
            NetworkBoundResource.this.fetchFromNetwork();
        }else {
            NetworkBoundResource.this.postValue(Resource.success(loadFromSharedPreferences()));
        }
    }

    protected void postValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.postValue(newValue);
        }
    }

    private void fetchFromNetwork() {
        appExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.i("Fetch", "Network");
                createCall();
            }
        });
    }

    @MainThread
    protected abstract boolean shouldFetch(ResultType data);

    @MainThread
    protected abstract ResultType loadFromDb();

    protected void onFetchFailed(Throwable t) {
        NetworkBoundResource.this.postValue(Resource.error(t.getMessage(), null));
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    protected ResultType processResponse(ApiResponse<ResultType> response) {
        return response.getData();
    }

    @WorkerThread
    protected void saveCallResult(ResultType item) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                NetworkBoundResource.this.postValue(Resource.success(item));
                saveInLocalStore(item);
            }
        });
    }

    @WorkerThread
    protected void processResponseErrors(ResponseBody errorBody, int code){
        JSONObject jObjError = null;
        try {
            jObjError = new JSONObject(errorBody.string());
            NetworkBoundResource.this.postValue(Resource.errorFromApi(jObjError.getString("errors"),null, code));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void saveInLocalStore(ResultType item);

    protected abstract ResultType loadFromSharedPreferences();

    protected abstract void createCall();

    protected void callApi(Call<ApiResponse<ResultType>> apiResponseCall) {
        apiResponseCall.enqueue(new Callback<ApiResponse<ResultType>>() {
            @Override
            public void onResponse(Call<ApiResponse<ResultType>> call, Response<ApiResponse<ResultType>> response) {
                if (response.isSuccessful()) {
                    saveCallResult(processResponse(response.body()));
                }else {
                    processResponseErrors(response.errorBody(), response.code());
                }
                Log.e(response.message(), response.message());
            }

            @Override
            public void onFailure(Call<ApiResponse<ResultType>> call, Throwable t) {
                onFetchFailed(t);
                Log.e("fail", t.getMessage());
            }
        });
    }

}
