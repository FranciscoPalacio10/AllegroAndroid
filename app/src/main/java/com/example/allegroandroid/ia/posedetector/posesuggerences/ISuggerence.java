package com.example.allegroandroid.ia.posedetector.posesuggerences;

import android.content.Context;

import com.example.allegroandroid.ia.posedetector.BodyLandMark;

public interface ISuggerence {

    String getSuggerence(Context context, BodyLandMark bodyLandMark);
}
