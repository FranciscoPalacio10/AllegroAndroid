package com.example.allegroandroid.ia.posedetector.classification;

public class ResultPoseClasifier {
    public String pose;
    public Double confidence;
    public Counter counter;
    public Boolean isStream;

    public ResultPoseClasifier(String pose, Float confidence,  Counter counter,Boolean isStream) {
        this.pose = pose;
        this.confidence =  (double) Math.round(confidence * 100) / 100;;
        this.counter = counter;
        this.isStream = isStream;
    }

    public ResultPoseClasifier() {

    }
}


