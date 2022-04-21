package com.example.allegrod.ui.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.allegrod.MainActivity;
import com.example.allegrod.R;
import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.services.FireBaseLoginService;
import com.example.allegrod.ui.CompleteProfileActivity;
import com.example.allegrod.viewmodel.TokenViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class LoadUser extends AppCompatActivity {
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
        Token="";
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
        if (token != "") {
            initMain();
        } else {
            initCompleteProfile();
        }
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

    private void initCompleteProfile() {
        Intent intent = new Intent(getApplicationContext(), CompleteProfileActivity.class);
        startActivity(intent);
    }

    private void initMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}