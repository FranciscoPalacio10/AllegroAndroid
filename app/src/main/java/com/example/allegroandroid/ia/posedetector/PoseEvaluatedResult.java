package com.example.allegroandroid.ia.posedetector;

public class PoseEvaluatedResult {
   public Integer confidence;
   public  String resultado;
   public Integer colorResult, colorBackGround;

    public PoseEvaluatedResult(int confidence, String resultado,  int colorResult, int colorBackGround) {
        this.confidence = confidence * 100;
        this.resultado = resultado;
        this.colorBackGround = colorBackGround;
        this.colorResult = colorResult;
    }

}
