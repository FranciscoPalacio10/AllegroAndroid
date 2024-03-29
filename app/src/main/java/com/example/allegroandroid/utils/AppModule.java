package com.example.allegroandroid.utils;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.localdb.AllegroLocalDb;
import com.example.allegroandroid.webservices.clases.ApiRequestClases;
import com.example.allegroandroid.webservices.desafios.ApiRequestDesafios;
import com.example.allegroandroid.webservices.historialdeclases.ApiRequestHistorialDeClases;
import com.example.allegroandroid.webservices.pointxuser.ApiRequestPointXUser;
import com.example.allegroandroid.webservices.token.ApiRequestToken;
import com.example.allegroandroid.webservices.user.allegroback.ApiRequestUser;

import java.util.concurrent.Executor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppModule {
    private static Context context;

    public AppModule(Context context) {
        if(this.context == null){
            this.context = context;
        }
    }

    public ApiRequestToken provideTokenRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL_USER)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequestToken.class);
    }

    public ApiRequestUser provideUserRetrofit() {
           return new Retrofit.Builder()
                    .baseUrl(AppConstant.BASE_URL_USER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiRequestUser.class);
    }

    public ApiRequestClases provideClaseRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL_PLAN_STUDIO)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequestClases.class);
    }


    public ApiRequestHistorialDeClases provideHistorialDeClaseRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL_PLAN_STUDIO)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequestHistorialDeClases.class);
    }

    public ApiRequestPointXUser providePointsXUserRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL_PLAN_STUDIO)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequestPointXUser.class);
    }

    public ApiRequestDesafios provideDesafiosRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL_PLAN_STUDIO)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequestDesafios.class);
    }

    public RoomDatabase.Builder<AllegroLocalDb> provideDb() {
       return Room.databaseBuilder(context,
                AllegroLocalDb.class, "database-allegro").
                fallbackToDestructiveMigration();
    }

    public void cleanDb(Executor diskIO) {
        diskIO.execute(new Runnable() {
            @Override
            public void run() {
                Room.databaseBuilder(context,
                        AllegroLocalDb.class, "database-allegro").
                        fallbackToDestructiveMigration().build().clearAllTables();
            }
        });
    }


//
//    UserDao provideUserDao(GitHubDb db){
//        return db.userDao();
//    }
//
//
//    RepoDao provideRepoDao(GitHubDb db){
//        return db.repoDao();
//    }
}
