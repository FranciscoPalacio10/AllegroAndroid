package com.example.allegroandroid.ia.posedetector;

public class PoseEvaluatedResult {
   public Double confidence;
   public String resultado;
   public Integer colorResult, colorBackGround;

    public PoseEvaluatedResult(double confidence, String resultado,  int colorResult, int colorBackGround) {
        this.confidence = confidence * 100;
        this.resultado = resultado;
        this.colorBackGround = colorBackGround;
        this.colorResult = colorResult;
    }

}
