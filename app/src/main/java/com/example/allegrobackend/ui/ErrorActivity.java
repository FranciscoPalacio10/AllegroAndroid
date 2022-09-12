package com.example.allegrobackend.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.allegrobackend.R;
import com.example.allegrobackend.constants.AppConstant;
import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.services.ActivitiesInitiator;

public class ErrorActivity extends AppCompatActivity {

    private TextView txtErrorActivity,txtErrorMessagge;
    private   Button boton ;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivitiesInitiator.initAutenticationActivity(new InitActitvy(this,null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        Bundle extras = getIntent().getExtras();
        String activityWithError = null,error = null;

        if (extras != null) {
             activityWithError = extras.getString(AppConstant.ACTIVITY_WITH_ERROR);
             error= extras.getString(AppConstant.ERROR_MESSAGE);
        }
        initApp(activityWithError,error);

    }

    private void initApp(String activityWithError, String error) {
        txtErrorActivity=findViewById(R.id.txtActivityError);
        txtErrorMessagge=findViewById(R.id.txtMotiveError);
        boton = findViewById(R.id.btnClose);

        txtErrorMessagge.setText(error);
        txtErrorActivity.setText(activityWithError);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                finish();
                System.exit(0);
            }
        });
    }

}