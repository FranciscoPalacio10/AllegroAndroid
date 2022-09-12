package com.example.allegroandroid.ui.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.models.token.TokenRequest;
import com.example.allegroandroid.models.token.TokenResponse;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.resource.Status;
import com.example.allegroandroid.repository.TokenRepository;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.example.allegroandroid.services.BroadcastService;
import com.example.allegroandroid.services.FireBaseLoginService;
import com.example.allegroandroid.services.PopUpService;
import com.example.allegroandroid.ui.MyViewModelFactory;
import com.example.allegroandroid.viewmodel.TokenViewModelNew;
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

public class AuthenticationActivity extends AppCompatActivity {
    private String TAG = "autenticacion";
    private ProgressDialog progressDialog;
    public static final int RC_SIGN_IN = 7;
    private SignInButton signInButton;
    private FireBaseLoginService fireBaseLoginService;
    private PopUpService popUpService;
    TokenViewModelNew tokenViewModelNew;
    AppExecutors appExecutors;
    AppModule appModule;
    ProgressDialog progress;

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
        setContentView(R.layout.activity_authenticacion);
        getSupportActionBar().hide();
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        popUpService = PopUpService.getPopUpService();
        appExecutors = new AppExecutors();
        appModule = new AppModule(getApplicationContext());
        tokenViewModelNew = ViewModelProviders.of(this, new MyViewModelFactory<>(getApplication(),
                new TokenRepository(appExecutors, appModule.provideTokenRetrofit()))).get(TokenViewModelNew.class);

        signInButton = findViewById(R.id.iniciarsesion);
        setGooglePlusButtonText(signInButton, "INGRESAR CON GOOGLE");
        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), this);
        progress = new ProgressDialog(AuthenticationActivity.this);
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
        progressDialog = ProgressDialog.show(this, "Cargando...", "Iniciando Session", true);
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
        try {
            Log.e(TAG, "updateUi");
            if (account != null && !onNetworkChange()) {
                getTokenFromApi(account.getEmail());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private Boolean onNetworkChange() {
        Boolean haveInternet = BroadcastService.AppHaveConnection(getApplicationContext());
        if (!haveInternet) {
            popUpService.popupMessage(AuthenticationActivity.this, "No Internet Connection. Check Your Wifi Or enter code hereMobile Data.", "Sin Internet");
            return true;
        }
        return false;
    }

    private void getTokenFromApi(String email) {
        LiveData<Resource<TokenResponse>> repo = tokenViewModelNew.getToken();
        tokenViewModelNew.setTokenRequest(new TokenRequest(email));
        repo.observe(this, tokenResponseResource -> {
                    if (tokenResponseResource.status == Status.ERROR) {
                        ActivitiesInitiator.initErrorActivity(new InitActitvy(getApplicationContext(), getBundle(tokenResponseResource.message)));
                    } else if (tokenResponseResource.status == Status.SUCCESS) {
                        ActivitiesInitiator.initMainActivity(new InitActitvy(getApplicationContext(), null));
                    } else if (tokenResponseResource.status == Status.ERRORFROMAPI) {
                        if (tokenResponseResource.code == 404) {
                            ActivitiesInitiator.initCompleteProfileActivity(new InitActitvy(getApplicationContext(), null));
                        } else {
                            ActivitiesInitiator.initErrorActivity(new InitActitvy(getApplicationContext(), getBundle(tokenResponseResource.message)));
                        }
                    } else {
                        progress.setTitle("Loading");
                        progress.setMessage("Espera unos segundos...");
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();
                    }
                }
        );
    }

    @NonNull
    private Bundle getBundle(String t) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t);
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR, this.TAG);
        return pBundle;
    }
}


















