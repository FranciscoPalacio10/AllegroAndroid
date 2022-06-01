package com.example.allegrobackend.ui.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.allegrobackend.R;
import com.example.allegrobackend.constants.AppConstant;
import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.models.token.TokenResponse;
import com.example.allegrobackend.services.ActivitiesInitiator;
import com.example.allegrobackend.services.FireBaseLoginService;
import com.example.allegrobackend.viewmodel.TokenViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoadUserActivity extends AppCompatActivity {
    public static final String TAG = "LoadUser";
    private TokenViewModel tokenViewModel;
    private FireBaseLoginService fireBaseLoginService;
    public static final int RC_SIGN_IN = 7;
    private String Token;
    private ProgressBar pbLoadUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_user);
        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), this);
        Token=null;
        signIn();
    }

    private void signIn() {
        pbLoadUser = findViewById(R.id.pbLoadUser);
        pbLoadUser.setVisibility(View.VISIBLE);
        FirebaseUser user = fireBaseLoginService.getCurrentUser();
        loadUserFromApi(user.getEmail());
        startActivityForResult(fireBaseLoginService.getmGoogleSignInClient().getSignInIntent(), RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            initApplication(Token);
        }
        pbLoadUser.setVisibility(View.GONE);
    }

    private void initApplication(String token) {
        if (token == "") {
            ActivitiesInitiator.initCompleteProfileActivity(new InitActitvy(this,null));
        }else if(token ==null){
            Bundle pBundle = getBundle("Error with token");
            ActivitiesInitiator.initErrorActivity(new InitActitvy(this,pBundle));
        }else {
            ActivitiesInitiator.initMainActivity(new InitActitvy(this,null));
        }
    }

    @NonNull
    private Bundle getBundle(String t) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t);
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR,this.TAG);
        return pBundle;
    }

    private void loadUserFromApi(String email) {
        tokenViewModel = ViewModelProviders.of(this).get(TokenViewModel.class);
            tokenViewModel.getBearerToken(email).observe(this, new Observer<TokenResponse>() {
                @Override
                public void onChanged(TokenResponse tokenResponse) {
                    if (tokenResponse.login) {
                        Token = tokenResponse.token;
                    } else {
                        Token = "";
                    }
                }
            });
    }
}