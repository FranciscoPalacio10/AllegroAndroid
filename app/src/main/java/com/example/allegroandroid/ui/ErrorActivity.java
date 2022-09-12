package com.example.allegroandroid.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.example.allegroandroid.services.token.SessionService;

public class ErrorActivity extends AppCompatActivity {

    private TextView txtErrorActivity, txtErrorMessagge;
    private Button boton;
    private SessionService sessionService;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivitiesInitiator.initAutenticationActivity(new InitActitvy(this, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        Bundle extras = getIntent().getExtras();
        String activityWithError = null, error = null;
        sessionService = new SessionService(this);
        if (extras != null) {
            activityWithError = extras.getString(AppConstant.ACTIVITY_WITH_ERROR);
            error = extras.getString(AppConstant.ERROR_MESSAGE);
        }
        initApp(activityWithError, error);

    }

    private void initApp(String activityWithError, String error) {
        txtErrorActivity = findViewById(R.id.txtActivityError);
        txtErrorMessagge = findViewById(R.id.txtMotiveError);
        boton = findViewById(R.id.btnClose);

        txtErrorMessagge.setText(error);
        txtErrorActivity.setText(activityWithError);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                finish();
                System.exit(0);
                sessionService.ClearSharedPreferences();
            }
        });
    }


}