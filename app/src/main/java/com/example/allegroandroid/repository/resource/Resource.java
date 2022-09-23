package com.example.allegroandroid.repository.resource;

public class Resource<T> {

    public final Status status;

    public final String message;

    public final T data;

    public final Integer code;

    public Resource(Status status, T data, String message, Integer code){
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static <T> Resource<T> success(T data){
        return new Resource<>(Status.SUCCESS, data, null, 200);
    }

    public static <T> Resource<T> error(String msg, T data){
        return new Resource<>(Status.ERROR, data, msg, 500);
    }

    public static <T> Resource<T> loading(T data){
        return new Resource<>(Status.LOADING, data, null, 0);
    }

    public static <T> Resource<T> errorFromApi(String msg, T data, Integer code){
        return new Resource<>(Status.ERRORFROMAPI, data, msg, code);
    }

}
