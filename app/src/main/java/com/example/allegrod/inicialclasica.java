package com.example.allegrod;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.allegrod.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.storage.FirebaseStorage;

public class inicialclasica extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private VideoView video;
    private YouTubePlayerView youTubePlayerView;
    String claveyoutbe="AIzaSyDo227hMCHOG9ihn6pnDJ_censtoWgRATY";
    private String inicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicialclasica);

        youTubePlayerView= findViewById(R.id.videoyoutbe);
        youTubePlayerView.initialize(claveyoutbe,this);
        FirebaseStorage storage = FirebaseStorage.getInstance();
    }
    public String obtenerUrl(String url) {
        String urlfinal=null;

        return urlfinal;

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean fuereiniciado) {

        if(!fuereiniciado){
            youTubePlayer.cueVideo("X3_-HVM_qFY");
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
       if(youTubeInitializationResult.isUserRecoverableError()){
           youTubeInitializationResult.getErrorDialog(this,1).show();
       }else{

           String s="Error al inicializar youtbe"+youTubeInitializationResult;
           Toast.makeText(this,s, Toast.LENGTH_LONG).show();
       }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent Dat){

            if(resultCode==1){
                getYotubePlayerProvide().initialize(claveyoutbe,this);

            }

    }

   protected YouTubePlayer.Provider getYotubePlayerProvide(){
        return youTubePlayerView;
   }



}
