package com.example.allegrobackend.ui.activityprofesores.fragmeteditarclases;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.example.allegrobackend.R;

public class dialogoInformación extends Activity {
    private TextView idClase;
    private Button btnVolver;
    private VideoView videoClase;
    private String uriClase, IdClase;
    private Boolean tocado=false;
    private ImageButton playVideo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(),"para volver presiona la pantalla",Toast.LENGTH_LONG).show();
        setContentView(R.layout.dialogoinformacion);
        DisplayMetrics dn = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dn);
        int width= dn.widthPixels;
        int height=dn.heightPixels;
        getWindow().setLayout((int)(width*.85),(int)(height*.7));
        WindowManager.LayoutParams params= getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);
        Bundle miBundle=getIntent().getExtras();
        if(miBundle!=null) {
            uriClase=miBundle.getString("uriClase");
            Log.e("url",uriClase);
            IdClase=miBundle.getString("idClase");

        }
        playVideo=findViewById(R.id.imgPlayVideo);
        idClase=findViewById(R.id.txtIdClase);
        videoClase=findViewById(R.id.videoView);
        reproducirClase();
    }


    private void reproducirClase(){
        idClase.setText(IdClase);
        videoClase.setVideoURI(Uri.parse(uriClase));
        videoClase.start();
        videoClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tocado){
                    videoClase.start();
                    tocado=true;
                    playVideo.setVisibility(View.GONE);
                }else{
                    videoClase.pause();
                    tocado=false;
                    playVideo.setVisibility(View.VISIBLE);
                }
            }
        });




    }







/*
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View v= inflater.inflate(R.layout.dialogoinformacion, container, false);
        idClase=v.findViewById(R.id.txtIdClase);
        btnVolver=v.findViewById(R.id.btnSalir);
        videoClase=v.findViewById(R.id.videoView);
        return v;
    }

 */


}
