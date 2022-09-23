/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.allegroandroid.ia.posedetector.classification;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.WorkerThread;

import com.example.allegroandroid.ia.posedetector.INotifier;
import com.google.common.base.Preconditions;
import com.google.mlkit.vision.pose.Pose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Accepts a stream of {@link Pose} for classification and Rep counting.
 */
public class PoseClassifierProcessor implements INotifier {
    private static final String TAG = "PoseClassifierProcessor";
    private static final String POSE_SAMPLES_FILE = "pose/fitness_poses_csvs_out_basic.csv";

    // Specify classes for which we want rep counting.
    // These are the labels in the given {@code POSE_SAMPLES_FILE}. You can set your own class labels
    // for your pose samples.
    private static final String PoseCincoBien = "PoseCincoBien";
    private static final String PoseCincoMal = "PoseCincoMal";
    private static final String PoseCuatroBien = "PoseCuatroBien";
    private static final String PoseCuatroMal = "PoseCuatroMal";
    private static final String PoseTresBien = "PoseTresBien";
    private static final String PoseTresMal = "PoseTresMal";
    private static final String PoseDosBien = "PoseDosBien";
    private static final String PoseDosMal = "PoseDosMal";
    private static final String PoseUnoBien = "PoseUnoBien";
    private static final String PoseUnoMal = "PoseUnoMal";
    private static final String[] POSE_CLASSES = {
            PoseCincoBien, PoseCincoMal, PoseCuatroBien, PoseCuatroMal, PoseTresBien, PoseTresMal, PoseDosBien,
            PoseDosMal, PoseUnoBien, PoseUnoMal
    };

    private final boolean isStreamMode;

    private EMASmoothing emaSmoothing;
    private List<RepetitionCounter> repCounters;
    private PoseClassifier poseClassifier;
    private ResultPoseClasifier lastRepResult;
    private Integer contador;
    private Counter counter;
    private Date date;
    private boolean start = false;

    @WorkerThread
    public PoseClassifierProcessor(Context context, boolean isStreamMode) {
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper());
        this.isStreamMode = isStreamMode;
        if (isStreamMode) {
            emaSmoothing = new EMASmoothing();
            repCounters = new ArrayList<>();
            lastRepResult = new ResultPoseClasifier();
            date = new Date();
            counter = new Counter(date);
        }
        loadPoseSamples(context);
    }

    private void loadPoseSamples(Context context) {
        List<PoseSample> poseSamples = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(POSE_SAMPLES_FILE)));
            String csvLine = reader.readLine();
            while (csvLine != null) {
                // If line is not a valid {@link PoseSample}, we'll get null and skip adding to the list.
                PoseSample poseSample = PoseSample.getPoseSample(csvLine, ",");
                if (poseSample != null) {
                    poseSamples.add(poseSample);
                }
                csvLine = reader.readLine();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error when loading pose samples.\n" + e);
        }
        poseClassifier = new PoseClassifier(poseSamples);
    }

    /**
     * Given a new {@link Pose} input, returns a list of formatted {@link String}s with Pose
     * classification results.
     *
     * <p>Currently it returns up to 2 strings as following:
     * 0: PoseClass : X reps
     * 1: PoseClass : [0.0-1.0] confidence
     */
    @WorkerThread
    public List<ResultPoseClasifier> getPoseResult(Pose pose) {
        List<ResultPoseClasifier> result = new ArrayList<>();

        if (!start) {
            return result;
        }

        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper());

        ClassificationResult classification = poseClassifier.classify(pose);

        // Update {@link RepetitionCounter}s if {@code isStreamMode}.
        if (isStreamMode) {
            // Feed pose to smoothing even if no pose found.
            classification = emaSmoothing.getSmoothedResult(classification);

            // Return early without updating repCounter if no pose found.
            if (pose.getAllPoseLandmarks().isEmpty()) {
                result.add(lastRepResult);
                return result;
            }

            String maxConfidenceClass = classification.getMaxConfidenceClass();
            if (counter.getRepetition() == 0 || lastRepResult.pose.equals(maxConfidenceClass)) {
                Float maxConfidenceClassResult = classification.getClassConfidence(maxConfidenceClass) / poseClassifier.confidenceRange();

                if (lastRepResult.pose == null) {
                    counter.RestartsecondsAndReps();
                } else {
                    if (maxConfidenceClassResult + 0.1 >= lastRepResult.confidence) {
                        counter.AddSecondsAndReps();
                    } else {
                        counter.RestartsecondsAndReps();
                    }
                }
                lastRepResult = new ResultPoseClasifier(classification.getMaxConfidenceClass(), maxConfidenceClassResult, counter, true);
            } else {
                counter.RestartsecondsAndReps();
            }
        }


        // Add maxConfidence class of current frame to result if pose is found.
        if (!pose.getAllPoseLandmarks().isEmpty()) {
            String maxConfidenceClass = classification.getMaxConfidenceClass();
            Float maxConfidenceClassResult = classification.getClassConfidence(maxConfidenceClass) / poseClassifier.confidenceRange();
            result.add(new ResultPoseClasifier(maxConfidenceClass, maxConfidenceClassResult, counter, isStreamMode));
        }
        Log.e("counter", counter.GetSecondsLast().toString() + " " + counter.getRepetition().toString());
        Log.e("result", result.get(0).pose + result.get(0).confidence);
        return result;
    }

    @Override
    public void update(Object o) {
        start = (boolean) o;
    }
}
