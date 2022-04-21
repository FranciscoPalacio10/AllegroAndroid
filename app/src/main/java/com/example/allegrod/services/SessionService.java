package com.example.allegrod.services;

import static android.provider.Settings.System.getString;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;

import com.example.allegrod.R;
import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.token.TokenResponse;

import java.util.Calendar;
import java.util.Date;

public class SessionService {
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    DateService dateService;
    public SessionService(Context context) {
        sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        dateService = DateService.getInstance();
    }

    public void SaveBearerToken(String email, String token) {
        ClearSharedPreferences();
        if(token != ""){
            editor.putString(email, "Bearer " + token);
            Long time = Calendar.getInstance().getTime().getTime();
            editor.putLong(email + AppConstant.TIME_USER_TOKEN, time);
            editor.apply();
        }
    }

    public TokenResponse GetBearerToken(String email) {
        if(sharedPref.contains(email)){
            String token= sharedPref.getString(email, null);
            return new TokenResponse(token,true);
        }
        return new TokenResponse("",false);
    }

    public boolean isBearerLoaded(String email){
        Date lastTimeRequested = GetBearerTokenTimeCreated(email);
        if(lastTimeRequested != null){
            Date lastTimeRequestPlusThree = dateService.addHoursToJavaUtilDate(lastTimeRequested,2);
            return lastTimeRequested.before(lastTimeRequestPlusThree);
        }
        return false;
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
