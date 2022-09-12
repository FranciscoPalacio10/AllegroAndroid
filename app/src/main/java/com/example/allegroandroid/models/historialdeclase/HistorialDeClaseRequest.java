package com.example.allegroandroid.models.historialdeclase;

import com.example.allegroandroid.models.user.User;

public class HistorialDeClaseRequest {
    public boolean isFinished;
    public int claseId;
    public int timeTotalVideo;
    public int TimeLeaveVideo;

    public HistorialDeClaseRequest(boolean isFinished, int claseId, int timeTotalVideo, int timeLeaveVideo) {
        this.isFinished = isFinished;
        this.claseId = claseId;
        this.timeTotalVideo = timeTotalVideo;
        TimeLeaveVideo = timeLeaveVideo;
    }

}
