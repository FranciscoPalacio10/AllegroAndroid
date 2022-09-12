package com.example.allegroandroid.ia.posedetector.posesuggerences;

import static java.lang.StrictMath.atan2;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

import com.example.allegroandroid.ia.GraphicOverlay;
import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.HashMap;
import java.util.Map;

public class AbstractPoseSugerence extends GraphicOverlay.Graphic {

    public AbstractPoseSugerence(GraphicOverlay overlay) {
        super(overlay);
    }

    double getAngle(PoseLandmark firstPoint, PoseLandmark midPoint, PoseLandmark lastPoint) {
        double firstPointX = translateX(firstPoint.getPosition().x);
        double firstPointY = translateY(firstPoint.getPosition().y);
        double firstPointZ = translateY(firstPoint.getPosition3D().getZ());
        double midPointX = translateX(midPoint.getPosition().x);
        double midPointY = translateY(midPoint.getPosition().y);
        double midPointZ = translateY(midPoint.getPosition3D().getZ());
        double lastPointY = translateY(lastPoint.getPosition().y);
        double lastPointX = translateY(lastPoint.getPosition().x);
        double lastPointZ = translateY(lastPoint.getPosition3D().getZ());
        double result =
                Math.toDegrees(
                        atan2(lastPointY - midPointY,
                                lastPointX - midPointX)
                                - atan2(firstPointY - midPointY,
                                firstPointX - lastPointX));
        result = Math.abs(result); // Angle should never be negative
        if (result > 180) {
            result = (360.0 - result); // Always get the acute representation of the angle
        }
        return result;
    }

    double getAngle2(PoseLandmark firstPoint, PoseLandmark midPoint, PoseLandmark lastPoint) {
        double firstPointX = translateX(firstPoint.getPosition().x);
        double midPointX = translateX(midPoint.getPosition().x);
        double firstPointY = translateY(firstPoint.getPosition().y);
        double midPointY = translateY(midPoint.getPosition().y);
        double lastPointY = translateY(lastPoint.getPosition().y);
        double lastPointX = translateY(lastPoint.getPosition().x);
        double result =
                Math.toDegrees(
                        atan2(lastPointY - firstPointY,
                                lastPointX - firstPointX)
                                - atan2(midPointY - firstPointY,
                                midPointX - firstPointX));
        result = Math.abs(result); // Angle should never be negative
        if (result > 180) {
            result = (360.0 - result); // Always get the acute representation of the angle
        }
        return result;
    }


    double getDistanceBetweenTwoPoint(PoseLandmark firstPoint, PoseLandmark secondPoint, Context context) {
        Map<String, Integer> pxOfContext = getPxOfContext(context);
        double firstPointX = translateX(firstPoint.getPosition().x);
        double secondPointX = translateX(secondPoint.getPosition().x);
        double firstPointY = translateY(firstPoint.getPosition().y);
        double secondPointY = translateY(secondPoint.getPosition().y);
        return (Math.hypot((firstPointX - secondPointX),
                (firstPointY - secondPointY))) * 0.0264583333;
    }

    static Map<String, Integer> getPxOfContext(Context context) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        map.put("x", width);
        map.put("y", height);
        return map;
    }


    @Override
    public void draw(Canvas canvas) {

    }
}
