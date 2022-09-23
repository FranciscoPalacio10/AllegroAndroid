package com.example.allegroandroid.models;


import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;

public class Reproductor {

    public Float timeLeftVideo;
    public YouTubePlayer youTubePlayer;

    public Reproductor(Float timeLeftVideo,YouTubePlayer youTubePlayer) {
        this.timeLeftVideo = timeLeftVideo;
        this.youTubePlayer = youTubePlayer;
    }



}
