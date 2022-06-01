package com.example.allegrobackend.services.youtube;

import static com.example.allegrobackend.constants.AppConstant.TIME_STOP_YOUTUBE;
import static com.example.allegrobackend.constants.AppConstant.YOUTUBE_PLAYER;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.allegrobackend.R;
import com.example.allegrobackend.constants.AppConstant;
import com.example.allegrobackend.models.token.TokenResponse;
import com.example.allegrobackend.services.DateService;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.gson.Gson;

import java.util.Date;

public class YoutubeService {
    private SharedPreferences sharedPref;
   private SharedPreferences.Editor editor;
    private Gson gson;

    public YoutubeService(Context context) {
        sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        gson = new Gson();
    }

    private void SaveTime(int seconds) {
        editor.putInt(TIME_STOP_YOUTUBE, seconds);
        editor.apply();
    }

    public void SaveTimeAndPlayer(int seconds,YouTubePlayer youTubePlayer){
        ClearSharedPreferences();
        SaveTime(seconds);
    }

    private void SavePlayer(YouTubePlayer youTubePlayer) {
        String json = gson.toJson(youTubePlayer);
        editor.putString(YOUTUBE_PLAYER, json);
        editor.apply();
    }

    public int GetTimeSaved() {
        return sharedPref.getInt(TIME_STOP_YOUTUBE, 0);
    }

    public YouTubePlayer GetYoutubePlayer() {
        String json = sharedPref.getString(YOUTUBE_PLAYER, null);
        return gson.fromJson(json, YouTubePlayer.class);
    }

    public void ClearSharedPreferences() {
        editor.clear().commit();
    }


}
