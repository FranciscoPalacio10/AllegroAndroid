package com.example.allegrod.ui.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.allegrod.ui.CompleteProfileActivity;
import com.example.allegrod.MainActivity;
import com.example.allegrod.R;
import com.example.allegrod.services.BroadcastService;
import com.example.allegrod.services.FireBaseLoginService;
import com.example.allegrod.services.PopUpService;
import com.example.allegrod.viewmodel.TokenViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Autenticacion extends AppCompatActivity {
    private String TAG = "autenticacion";
    private ProgressDialog progressDialog;
    public static final int RC_SIGN_IN = 7;
    private SignInButton signInButton;
    private FireBaseLoginService fireBaseLoginService;
    private TokenViewModel tokenViewModel;
    private PopUpService popUpService;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        NotificationManager notificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
        notificationManager.cancelAll();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacion);
        getSupportActionBar().hide();
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        popUpService = PopUpService.getPopUpService();
        signInButton = findViewById(R.id.iniciarsesion);
        setGooglePlusButtonText(signInButton, "INGRESAR CON GOOGLE");
        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), this);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = ProgressDialog.show(this,"Cargando...","Iniciando Session",true);
        updateUI(fireBaseLoginService.getCurrentUser());
        progressDialog.dismiss();
    }

    private void signIn() {
        startActivityForResult(fireBaseLoginService.getmGoogleSignInClient().getSignInIntent(), RC_SIGN_IN);
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fireBaseLoginService.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "signInWithCredential:success");
                            updateUI(fireBaseLoginService.getCurrentUser());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                        // ...
                    }
                });
    }


    private void updateUI(FirebaseUser account) {
        Log.e(TAG,"updateUi");
        if (account != null) {
            Bundle bundle = new Bundle();
            bundle.putString("email", fireBaseLoginService.getCurrentUser().getEmail());
            initApplication(fireBaseLoginService.getCurrentUser().getEmail(), bundle);
        }
    }

    private String loadUserFromApi(String email) {
        tokenViewModel = ViewModelProviders.of(this).get(TokenViewModel.class);
        return tokenViewModel.getBearerToken(email);
    }

    private void initApplication(String email, Bundle bundle) {
        if(!onNetworkChange()){
            String token = loadUserFromApi(email);
            if (token != "") {
                initMain();
            } else {
                initCompleteProfile();
            }
        }
    }

    private void initCompleteProfile() {
        Intent intent = new Intent(getApplicationContext(), CompleteProfileActivity.class);
        startActivity(intent);
    }

    private void initMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private Boolean onNetworkChange() {
       Boolean haveInternet = BroadcastService.AppHaveConnection(getApplicationContext());
       if (!haveInternet) {
                popUpService.popupMessage(Autenticacion.this,"No Internet Connection. Check Your Wifi Or enter code hereMobile Data.","Sin Internet");
            return true;
        }
        return false;
    }
}


















