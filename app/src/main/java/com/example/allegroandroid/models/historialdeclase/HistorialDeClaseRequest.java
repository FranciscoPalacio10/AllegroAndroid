package com.example.allegroandroid.models.historialdeclase;

public class HistorialDeClaseRequest {
    public boolean isFinished;
    public Integer claseId;
    public Integer timeTotalVideo;
    public Integer timeLeaveVideo;
    public Integer pointsIA;

    public HistorialDeClaseRequest(boolean isFinished, Integer claseId, Integer timeTotalVideo, Integer timeLeaveVideo, Integer pointsIA) {
        this.isFinished = isFinished;
        this.claseId = claseId;
        this.timeTotalVideo = timeTotalVideo;
        this.timeLeaveVideo = timeLeaveVideo;
        this.pointsIA = pointsIA;
    }
}
