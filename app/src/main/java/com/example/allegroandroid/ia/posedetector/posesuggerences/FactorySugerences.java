package com.example.allegroandroid.ia.posedetector.posesuggerences;


import com.example.allegroandroid.ia.GraphicOverlay;
import com.example.allegroandroid.ia.posedetector.ConstantPoseToEvalute;

public class FactorySugerences {

    public static ISuggerence getPoseToGetSuggerence(String constantPoseToEvalute, GraphicOverlay graphicOverlay) {
        switch (constantPoseToEvalute) {
            case ConstantPoseToEvalute.POSE_UNO:
                return new PrimeraPoseSuggerence(graphicOverlay);
            default:
                return null;
        }
    }
}
