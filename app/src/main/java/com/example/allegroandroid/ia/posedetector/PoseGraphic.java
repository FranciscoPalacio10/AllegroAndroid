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

package com.example.allegroandroid.ia.posedetector;

import static java.lang.StrictMath.max;
import static java.lang.StrictMath.min;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.ia.GraphicOverlay;
import com.example.allegroandroid.ia.Speaker;
import com.example.allegroandroid.ia.posedetector.classification.ResultPoseClasifier;
import com.example.allegroandroid.ia.posedetector.posesuggerences.FactorySugerences;
import com.example.allegroandroid.ia.posedetector.posesuggerences.ISuggerence;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.models.ResultClaseEvaluate;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.google.common.primitives.Ints;
import com.google.gson.Gson;
import com.google.mlkit.vision.common.PointF3D;

import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Draw the detected pose in preview.
 */
public class PoseGraphic extends GraphicOverlay.Graphic {

    private static final float DOT_RADIUS = 8.0f;
    private static final float IN_FRAME_LIKELIHOOD_TEXT_SIZE = 30.0f;
    private static final float STROKE_WIDTH = 10.0f;
    private static final float POSE_CLASSIFICATION_TEXT_SIZE = 60.0f;
    public static final Integer secondsToEvaluate = 5;

    private final Pose pose;
    private final boolean showInFrameLikelihood;
    private final boolean visualizeZ;
    private final boolean rescaleZForVisualization;
    private float zMin = Float.MAX_VALUE;
    private float zMax = Float.MIN_VALUE;

    private List<ResultPoseClasifier> poseClassification;
    private final Paint classificationTextPaint;
    private final Paint leftPaint;
    private final Paint rightPaint, backgroundCuadrado;
    private final Context context;
    private final Paint whitePaint, textPaint, timeTextPaint;

    private final GraphicOverlay graphicOverlay;
    private final Speaker speaker;
    private InitActitvy initActitvy;
    private HistorialDeClaseResponse historialDeClaseResponse;
    private boolean isStartPoseGraphic = false;
    private List<INotifier> notifierList = new ArrayList<>();


    PoseGraphic(
            Context context,
            GraphicOverlay overlay,
            Pose pose,
            boolean showInFrameLikelihood,
            boolean visualizeZ,
            boolean rescaleZForVisualization,
            HistorialDeClaseResponse historialDeClaseResponse,
            List<ResultPoseClasifier> poseClassification,
            Speaker speaker) {
        super(overlay);

        this.context = context;
        this.pose = pose;
        this.showInFrameLikelihood = showInFrameLikelihood;
        this.visualizeZ = visualizeZ;
        this.rescaleZForVisualization = rescaleZForVisualization;
        this.historialDeClaseResponse = historialDeClaseResponse;
        this.poseClassification = poseClassification;
        this.graphicOverlay = overlay;
        this.speaker = speaker;

        if (speaker != null) {
            speaker.speakStart("Evaluaremos la " + historialDeClaseResponse.clase.name + ", recuerda que debes permanecer" +
                    secondsToEvaluate.toString() + "segundos para pasar la prueba");
        }

        classificationTextPaint = new Paint();
        classificationTextPaint.setColor(Color.WHITE);
        classificationTextPaint.setShadowLayer(5.0f, 0f, 0f, Color.BLACK);

        whitePaint = new Paint();
        whitePaint.setStrokeWidth(STROKE_WIDTH);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setTextSize(IN_FRAME_LIKELIHOOD_TEXT_SIZE);


        leftPaint = new Paint();
        leftPaint.setStrokeWidth(STROKE_WIDTH);
        leftPaint.setColor(Color.GREEN);

        rightPaint = new Paint();
        rightPaint.setStrokeWidth(STROKE_WIDTH);
        rightPaint.setColor(Color.YELLOW);


        timeTextPaint = new Paint();
        timeTextPaint.setStrokeWidth(STROKE_WIDTH);
        timeTextPaint.setColor(Color.WHITE);
        timeTextPaint.setTextSize(100f);

        //PUNTAJE
        textPaint = new Paint();
        textPaint.setStrokeWidth(STROKE_WIDTH);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(180f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        backgroundCuadrado = new Paint();
        backgroundCuadrado.setStrokeWidth(10);
        backgroundCuadrado.setStyle(Paint.Style.FILL);
    }


    //dibuja en pantalla los resultados
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void draw(Canvas canvas) {

        List<PoseLandmark> landmarks = pose.getAllPoseLandmarks();

        if (landmarks.isEmpty()) {
            return;
        }

        BodyLandMark bodyLandMark = new BodyLandMark(pose);
        Integer width = canvas.getWidth();
        Integer height = canvas.getHeight();

        // Face
        drawLine(canvas, bodyLandMark.nose, bodyLandMark.lefyEyeInner, whitePaint);
        drawLine(canvas, bodyLandMark.lefyEyeInner, bodyLandMark.lefyEye, whitePaint);
        drawLine(canvas, bodyLandMark.lefyEye, bodyLandMark.leftEyeOuter, whitePaint);
        drawLine(canvas, bodyLandMark.leftEyeOuter, bodyLandMark.leftEar, whitePaint);
        drawLine(canvas, bodyLandMark.nose, bodyLandMark.rightEyeInner, whitePaint);
        drawLine(canvas, bodyLandMark.rightEyeInner, bodyLandMark.rightEye, whitePaint);
        drawLine(canvas, bodyLandMark.rightEye, bodyLandMark.rightEyeOuter, whitePaint);
        drawLine(canvas, bodyLandMark.rightEyeOuter, bodyLandMark.rightEar, whitePaint);
        drawLine(canvas, bodyLandMark.leftMouth, bodyLandMark.rightMouth, whitePaint);

        drawLine(canvas, bodyLandMark.leftShoulder, bodyLandMark.rightShoulder, whitePaint);
        drawLine(canvas, bodyLandMark.leftHip, bodyLandMark.rightHip, whitePaint);

        // Left body
        drawLine(canvas, bodyLandMark.leftShoulder, bodyLandMark.leftElbow, leftPaint);
        drawLine(canvas, bodyLandMark.leftElbow, bodyLandMark.leftWrist, leftPaint);
        drawLine(canvas, bodyLandMark.leftShoulder, bodyLandMark.leftHip, leftPaint);
        drawLine(canvas, bodyLandMark.leftHip, bodyLandMark.leftKnee, leftPaint);
        drawLine(canvas, bodyLandMark.leftKnee, bodyLandMark.leftAnkle, leftPaint);
        drawLine(canvas, bodyLandMark.leftWrist, bodyLandMark.leftThumb, leftPaint);
        drawLine(canvas, bodyLandMark.leftWrist, bodyLandMark.leftPinky, leftPaint);
        drawLine(canvas, bodyLandMark.leftWrist, bodyLandMark.leftIndex, leftPaint);
        drawLine(canvas, bodyLandMark.leftIndex, bodyLandMark.leftPinky, leftPaint);
        drawLine(canvas, bodyLandMark.leftAnkle, bodyLandMark.leftHeel, leftPaint);
        drawLine(canvas, bodyLandMark.leftHeel, bodyLandMark.leftFootIndex, leftPaint);

        // Right body
        drawLine(canvas, bodyLandMark.rightShoulder, bodyLandMark.rightElbow, rightPaint);
        drawLine(canvas, bodyLandMark.rightElbow, bodyLandMark.rightWrist, rightPaint);
        drawLine(canvas, bodyLandMark.rightShoulder, bodyLandMark.rightHip, rightPaint);
        drawLine(canvas, bodyLandMark.rightHip, bodyLandMark.rightKnee, rightPaint);
        drawLine(canvas, bodyLandMark.rightKnee, bodyLandMark.rightAnkle, rightPaint);
        drawLine(canvas, bodyLandMark.rightWrist, bodyLandMark.rightThumb, rightPaint);
        drawLine(canvas, bodyLandMark.rightWrist, bodyLandMark.rightPinky, rightPaint);
        drawLine(canvas, bodyLandMark.rightWrist, bodyLandMark.rightIndex, rightPaint);
        drawLine(canvas, bodyLandMark.rightIndex, bodyLandMark.rightPinky, rightPaint);
        drawLine(canvas, bodyLandMark.rightAnkle, bodyLandMark.rightHeel, rightPaint);
        drawLine(canvas, bodyLandMark.rightHeel, bodyLandMark.rightFootIndex, rightPaint);

        // Draw all the points
        for (PoseLandmark landmark : landmarks) {
            drawPoint(canvas, landmark, whitePaint);
            if (visualizeZ && rescaleZForVisualization) {
                zMin = min(zMin, landmark.getPosition3D().getZ());
                zMax = max(zMax, landmark.getPosition3D().getZ());
            }
        }

        // Draw inFrameLikelihood for all points
        if (showInFrameLikelihood) {
            for (PoseLandmark landmark : landmarks) {
                canvas.drawText(
                        String.format(Locale.US, "%.2f", landmark.getInFrameLikelihood()),
                        translateX(landmark.getPosition().x),
                        translateY(landmark.getPosition().y),
                        whitePaint);
            }
        }

        if (speaker != null) {
            ISuggerence suggerence = FactorySugerences.getPoseToGetSuggerence(historialDeClaseResponse.clase.name, graphicOverlay);
            if (suggerence != null) {
                String suggest = suggerence.getSuggerence(context, bodyLandMark);

                if (suggest != "") {
                    showSuggest(canvas, suggest);
                    return;
                }
            }
        }

        // Draw pose classification text.
        for (int i = 0; i < poseClassification.size(); i++) {
            showClassification(canvas, i, bodyLandMark);
        }

    }


    private void showSuggest(Canvas canvas, String suggest) {
        float classificationX = POSE_CLASSIFICATION_TEXT_SIZE * 0.5f;
        float classificationY = (canvas.getHeight() - POSE_CLASSIFICATION_TEXT_SIZE * 0.5f
                * (poseClassification.size()) - 1);
        classificationTextPaint.setTextSize(40f * poseClassification.size());
        canvas.drawText(suggest, classificationX, classificationY,
                classificationTextPaint);
        speaker.speakSuggest(suggest);
    }

    private void showClassification(Canvas canvas, int i, BodyLandMark bodyLandMark) {
        ResultPoseClasifier resultPoseClasifier = poseClassification.get(i);

        // pinto el color  según el resultado de la clasificacion
        PoseEvaluatedResult poseEvaluatedResult = processPoseToEvaluate(canvas, bodyLandMark, resultPoseClasifier);

        paintCuadradoAndTextClassification(poseEvaluatedResult.colorResult, poseEvaluatedResult.colorBackGround);

        // el cuadrado con la puntacion se ubica encima de la cabeza
        int xPos = (int) translateX(bodyLandMark.nose.getPosition().x);
        int yPos = (int) ((translateY(bodyLandMark.nose.getPosition().y / 3)) - ((textPaint.descent() + textPaint.ascent()) / 3));

        String poseConfidence = poseEvaluatedResult.confidence.toString();

        canvas.drawRect(xPos - (textPaint.measureText(poseConfidence)), yPos - textPaint.getTextSize(), xPos + (textPaint.measureText(poseConfidence)), yPos, backgroundCuadrado);
        canvas.drawText(poseConfidence, xPos, yPos, textPaint);

        boolean isCompleteSecondsToEvaluate = isCompleteSecondsToEvaluate(resultPoseClasifier);

        // vamos a la activity de resultados para mostrar puntos obtenidos
        if (isCompleteSecondsToEvaluate || !resultPoseClasifier.isStream) {
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            ResultClaseEvaluate resultClaseEvaluate = new ResultClaseEvaluate(historialDeClaseResponse, poseEvaluatedResult);
            String jsonStr = gson.toJson(resultClaseEvaluate);
            bundle.putString(AppConstant.RESULT_CLASE_EVALUATE, jsonStr);
            openResultActivity(bundle);
        }
    }

    @NonNull
    private PoseEvaluatedResult processPoseToEvaluate(Canvas canvas, BodyLandMark bodyLandMark, ResultPoseClasifier resultPoseClasifier) {
        counterSecondsInTheSamePosition(canvas, bodyLandMark, resultPoseClasifier);

        if (!resultPoseClasifier.pose.toLowerCase().contains(historialDeClaseResponse.clase.name.toLowerCase())) {
            return new PoseEvaluatedResult(1, "MAL", Color.RED, Color.WHITE);
        }

        if (resultPoseClasifier.pose.toUpperCase(Locale.ROOT).contains("MAL")) {
            return new PoseEvaluatedResult(resultPoseClasifier.confidence, "MAL", Color.RED, Color.WHITE);
        }

        return new PoseEvaluatedResult(resultPoseClasifier.confidence, "BIEN", Color.GREEN, Color.BLACK);
    }

    private void openResultActivity(Bundle pBundle) {
        initActitvy = new InitActitvy(context, pBundle);
        ActivitiesInitiator.initResultActivity(initActitvy);
    }

    private boolean isCompleteSecondsToEvaluate(ResultPoseClasifier resultPoseClasifier) {
        if (resultPoseClasifier.counter != null) {
            return resultPoseClasifier.counter.GetSecondsLast() == secondsToEvaluate.longValue();
        }
        return false;
    }

    private void counterSecondsInTheSamePosition(Canvas canvas, BodyLandMark bodyLandMark, ResultPoseClasifier resultPoseClasifier) {
        if (resultPoseClasifier.counter != null) {
            int xPos = (int) translateX(bodyLandMark.rightElbow.getPosition().x / 2);
            int yPos = (int) ((translateY(bodyLandMark.rightElbow.getPosition().y)));
            canvas.drawText(resultPoseClasifier.counter.GetSecondsLast().toString(), xPos, yPos, timeTextPaint);
            if (resultPoseClasifier.isStream) {
                speaker.speakSecond(resultPoseClasifier.counter.GetSecondsLast());
            }
        }
    }

    private void paintCuadradoAndTextClassification(int red, int white) {
        backgroundCuadrado.setColor(red);
        textPaint.setColor(white);
    }

    void drawPoint(Canvas canvas, PoseLandmark landmark, Paint paint) {
        PointF3D point = landmark.getPosition3D();
        maybeUpdatePaintColor(paint, canvas, point.getZ());
        canvas.drawCircle(translateX(point.getX()), translateY(point.getY()), DOT_RADIUS, paint);
    }

    void drawLine(Canvas canvas, PoseLandmark startLandmark, PoseLandmark endLandmark, Paint paint) {
        PointF3D start = startLandmark.getPosition3D();
        PointF3D end = endLandmark.getPosition3D();

        // Gets average z for the current body line
        float avgZInImagePixel = (start.getZ() + end.getZ()) / 2;
        maybeUpdatePaintColor(paint, canvas, avgZInImagePixel);

        canvas.drawLine(
                translateX(start.getX()),
                translateY(start.getY()),
                translateX(end.getX()),
                translateY(end.getY()),
                paint);
    }

    private void maybeUpdatePaintColor(Paint paint, Canvas canvas, float zInImagePixel) {
        if (!visualizeZ) {
            return;
        }

        // When visualizeZ is true, sets up the paint to different colors based on z values.
        // Gets the range of z value.
        float zLowerBoundInScreenPixel;
        float zUpperBoundInScreenPixel;

        if (rescaleZForVisualization) {
            zLowerBoundInScreenPixel = min(-0.001f, scale(zMin));
            zUpperBoundInScreenPixel = max(0.001f, scale(zMax));
        } else {
            // By default, assume the range of z value in screen pixel is [-canvasWidth, canvasWidth].
            float defaultRangeFactor = 1f;
            zLowerBoundInScreenPixel = -defaultRangeFactor * canvas.getWidth();
            zUpperBoundInScreenPixel = defaultRangeFactor * canvas.getWidth();
        }

        float zInScreenPixel = scale(zInImagePixel);

        if (zInScreenPixel < 0) {
            // Sets up the paint to draw the body line in red if it is in front of the z origin.
            // Maps values within [zLowerBoundInScreenPixel, 0) to [255, 0) and use it to control the
            // color. The larger the value is, the more red it will be.
            int v = (int) (zInScreenPixel / zLowerBoundInScreenPixel * 255);
            v = Ints.constrainToRange(v, 0, 255);
            paint.setARGB(255, 255, 255 - v, 255 - v);
        } else {
            // Sets up the paint to draw the body line in blue if it is behind the z origin.
            // Maps values within [0, zUpperBoundInScreenPixel] to [0, 255] and use it to control the
            // color. The larger the value is, the more blue it will be.
            int v = (int) (zInScreenPixel / zUpperBoundInScreenPixel * 255);
            v = Ints.constrainToRange(v, 0, 255);
            paint.setARGB(255, 255 - v, 255 - v, 255);
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void addObserver(INotifier channel) {
        this.notifierList.add(channel);
    }

    public void removeObserver(INotifier channel) {
        this.notifierList.remove(channel);
    }

    public void setIsStartPoseGraphic(Boolean news) {
        this.isStartPoseGraphic = news;
        for (INotifier channel : this.notifierList) {
            channel.update(this.isStartPoseGraphic);
        }
    }

}


