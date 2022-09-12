package com.example.allegroandroid.models;

import com.example.allegroandroid.ia.posedetector.PoseEvaluatedResult;
import com.example.allegroandroid.ia.posedetector.classification.ResultPoseClasifier;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;

public class ResultClaseEvaluate {
    public HistorialDeClaseResponse historialDeClaseResponse;
    public PoseEvaluatedResult poseEvaluatedResult;

    public ResultClaseEvaluate(HistorialDeClaseResponse historialDeClaseResponse, PoseEvaluatedResult poseEvaluatedResult) {
        this.historialDeClaseResponse = historialDeClaseResponse;
        this.poseEvaluatedResult = poseEvaluatedResult;
    }

}
