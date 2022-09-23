package com.example.allegroandroid.ia.posedetector;

import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

public class BodyLandMark extends LegLandMarks {

    PoseLandmark nose;
    PoseLandmark lefyEyeInner;
    PoseLandmark lefyEye;
    PoseLandmark leftEyeOuter;
    PoseLandmark rightEyeInner;
    PoseLandmark rightEye;
    PoseLandmark rightEyeOuter;
    PoseLandmark leftEar;
    PoseLandmark rightEar;
    PoseLandmark leftMouth;
    PoseLandmark rightMouth;
    PoseLandmark leftShoulder;
    PoseLandmark rightShoulder;
    PoseLandmark leftElbow;
    PoseLandmark rightElbow;
    PoseLandmark leftWrist;
    PoseLandmark rightWrist;
    PoseLandmark leftKnee;
    PoseLandmark rightKnee;
    PoseLandmark leftAnkle;
    PoseLandmark rightAnkle;
    PoseLandmark leftPinky;
    PoseLandmark rightPinky;
    PoseLandmark leftIndex;
    PoseLandmark rightIndex;
    PoseLandmark leftThumb;
    PoseLandmark rightThumb;
    LegLandMarks legLandMarks;

    public BodyLandMark(Pose pose) {
        super(pose);
        nose = pose.getPoseLandmark(PoseLandmark.NOSE);
        lefyEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER);
         lefyEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE);
         leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER);
         rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER);
         rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE);
         rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER);
         leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR);
         rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR);
         leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH);
         rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH);

         leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
         rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
         leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW);
         rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW);
         leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST);
         rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST);
         leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);
         rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);
         leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE);
         rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE);

         leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY);
         rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY);
         leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX);
         rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX);
         leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB);
         rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB);
         legLandMarks = getLegLandMarks();
    }



}
