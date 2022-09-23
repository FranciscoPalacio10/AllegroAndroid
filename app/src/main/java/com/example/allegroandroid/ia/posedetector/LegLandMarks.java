package com.example.allegroandroid.ia.posedetector;

import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

public class LegLandMarks {

    public PoseLandmark leftKnee;
    public PoseLandmark rightKnee;
    public PoseLandmark leftAnkle;
    public PoseLandmark rightAnkle;
    public PoseLandmark leftHeel;
    public PoseLandmark rightHeel;
    public PoseLandmark leftFootIndex;
    public PoseLandmark rightFootIndex;
    public PoseLandmark leftHip;
    public PoseLandmark rightHip;

    public LegLandMarks(Pose pose) {
        leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);
        rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);
        leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE);
        rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE);
        leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL);
        rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL);
        leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX);
        rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX);
        leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
        rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);

    }
    public LegLandMarks getLegLandMarks() {
        return this;
    }
}

