package com.example.allegroandroid.ia.posedetector.posesuggerences;

import android.content.Context;

import com.example.allegroandroid.ia.GraphicOverlay;
import com.example.allegroandroid.ia.posedetector.BodyLandMark;


public class PrimeraPoseSuggerence extends AbstractPoseSugerence implements ISuggerence {

    public PrimeraPoseSuggerence(GraphicOverlay overlay) {
        super(overlay);
    }

        /*
        Los pies están completamente apoyados en el suelo, los talones en contacto y las puntas hacia fuera, de modo
        que los ejes de los pies forman una línea recta.
         */
    @Override
    public String getSuggerence(Context context, BodyLandMark bodyLandMark) {
        Double distanceHeel = getDistanceBetweenTwoPoint(bodyLandMark.getLegLandMarks().rightHeel, bodyLandMark.getLegLandMarks().leftHeel, context);

        // los talones en contacto en cm
        if (distanceHeel > 3) {
            return "Los talones deben estar pegados";
        }

        // ejes de los pies forman una línea recta
        Double rightFootIndexD = getDistanceBetweenTwoPoint(bodyLandMark.getLegLandMarks().rightFootIndex, bodyLandMark.getLegLandMarks().leftFootIndex, context);
        if (rightFootIndexD < 3) {
            return "La punta de los pies tiene que apuntar hacia afuera";
        }

        return "";
    }

}
