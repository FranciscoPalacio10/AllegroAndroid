package com.example.allegrod.services;

import android.app.Activity;

import com.example.allegrod.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FireBaseLoginService {
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    GoogleSignInOptions gso;

    public FireBaseLoginService(String code, Activity activity) {
        this.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(code)
                .requestEmail()
                .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        this.mAuth = FirebaseAuth.getInstance();;
    }

    public GoogleSignInClient getmGoogleSignInClient() {
        return mGoogleSignInClient;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public GoogleSignInOptions getGso() {
        return gso;
    }

    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

}
