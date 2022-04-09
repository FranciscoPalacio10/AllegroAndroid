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

    public SessionService(Context context) {
        sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void SaveBearerToken(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        if(token != ""){
            editor.putString(AppConstant.USER_TOKEN, "Bearer " + token);
            Long time = Calendar.getInstance().getTime().getTime();
            editor.putLong(AppConstant.TIME_USER_TOKEN, time);
            editor.apply();
        }
    }

    public String GetBearerToken() {
        if(sharedPref.contains(AppConstant.USER_TOKEN)){
            return sharedPref.getString(AppConstant.USER_TOKEN, null);
        }
        return "";
    }

    public Date GetBearerTokenTimeCreated() {
        Long dateLong = sharedPref.getLong(AppConstant.TIME_USER_TOKEN, 0);
        if (dateLong > 0) {
            return new Date(dateLong);
        }
        return null;
    }


}
