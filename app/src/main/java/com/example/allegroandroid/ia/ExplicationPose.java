package com.example.allegroandroid.ia;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.ia.posedetector.ConstantPoseToEvalute;

public class ExplicationPose{
    private int imagePose;
    private String descriptionPose;
    private Context context;
    private boolean isClosed = false;
    private IChangeListener listener;
    private ImageView dialogImageView;
    private TextView dialogExplainPose;
    public ExplicationPose(Context context) {
        this.context = context;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void showExplicationPose(String name) {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(context);
        LayoutInflater factory = LayoutInflater.from(context);
        final View view = factory.inflate(R.layout.dialogexplainpose, null);
        dialogImageView = view.findViewById(R.id.dialog_imageview);
        dialogExplainPose = view.findViewById(R.id.dialogExplainPose);

        setExplicationPose(dialogImageView, dialogExplainPose, name);

        alertadd.setView(view);
        ImageButton button = view.findViewById(R.id.btn_close_dialog_explain_pose);
        AlertDialog alertDialog = alertadd.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                if(listener != null){
                    listener.alertDialogIsClosed();
                }

            }
        });
    }

    private void setExplicationPose(ImageView dialogImageView, TextView dialogExplainPose, String name) {
        switch (name){
            case ConstantPoseToEvalute.POSE_UNO:
                dialogImageView.setImageResource(R.drawable.poseuno);
                dialogExplainPose.setText(R.string.explainPoseUno);
                break;
            case ConstantPoseToEvalute.POSE_DOS:
                dialogImageView.setImageResource(R.drawable.posedos);
                dialogExplainPose.setText(R.string.explainPoseDos);
                break;
            case ConstantPoseToEvalute.POSE_TRES:
                dialogImageView.setImageResource(R.drawable.posetres);
                dialogExplainPose.setText(R.string.explainPoseTres);
                break;
            case ConstantPoseToEvalute.POSE_CUATRO:
                dialogImageView.setImageResource(R.drawable.posecuatro);
                dialogExplainPose.setText(R.string.explainPoseCuatro);
                break;
            default:
                dialogImageView.setImageResource(R.drawable.posecinco);
                dialogExplainPose.setText(R.string.explainPoseCinco);
        }
    }


    public void setChangeListener(IChangeListener listener) {
        this.listener = listener;
    }
}
