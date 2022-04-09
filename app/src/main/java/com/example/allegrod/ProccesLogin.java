package com.example.allegrod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.services.SessionService;

public class ProccesLogin extends AppCompatActivity {
    private String bearer;
    private ProgressBar loading;
    private SessionService sessionService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procces_login);
        loading = (ProgressBar) findViewById(R.id.loaded);
        loading.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        initMain(bundle);
    }

    private void initMain(Bundle bundle) {
        if (bundle != null) {
            bearer = bundle.getString(AppConstant.USER_ISLOGIN);
        }

        if(bearer != ""){
            initMain();
        }
        //si no funciona el bundle se consulta con el service.
        sessionService= new SessionService(getApplicationContext());
        String bearer=  sessionService.GetBearerToken();
        if(bearer != ""){
            initMain();
        }
    }

    private void initMain() {
        loading.setVisibility(View.GONE);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}