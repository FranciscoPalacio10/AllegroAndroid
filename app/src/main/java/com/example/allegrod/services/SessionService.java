package com.example.allegrod.services;

import static android.provider.Settings.System.getString;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.allegrod.R;
import com.example.allegrod.constants.AppConstant;

import java.util.Calendar;
import java.util.Date;

public class SessionService {
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    public SessionService(Context context) {
        sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void SaveBearerToken(String email, String token) {
        if(token != ""){
            editor.putString(email, "Bearer " + token);
            Long time = Calendar.getInstance().getTime().getTime();
            editor.putLong(email + AppConstant.TIME_USER_TOKEN, time);
            editor.apply();
        }
    }

    public String GetBearerToken(String email) {
        if(sharedPref.contains(email)){
            return sharedPref.getString(email, null);
        }
        return "";
    }

    public Date GetBearerTokenTimeCreated(String email) {
        Long dateLong = sharedPref.getLong(email + AppConstant.TIME_USER_TOKEN, 0);
        if (dateLong > 0) {
            return new Date(dateLong);
        }
        return null;
    }

    public void ClearSharedPreferences(){
        editor.clear().commit();
    }

}
