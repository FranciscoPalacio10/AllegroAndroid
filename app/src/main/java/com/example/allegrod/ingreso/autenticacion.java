package com.example.allegrod.ingreso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.allegrod.MainActivity;
import com.example.allegrod.ProccesLogin;
import com.example.allegrod.R;
import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.services.FireBaseLoginService;
import com.example.allegrod.viewmodel.TokenViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

public class autenticacion extends AppCompatActivity {
    private String TAG = "autenticacion";
    private ProgressDialog progressDialog;
    public static final int RC_SIGN_IN = 7;
    private SignInButton signInButton;
    private FireBaseLoginService fireBaseLoginService;
    private TokenViewModel tokenViewModel;
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
        progressDialog = new ProgressDialog(this);
        signInButton = findViewById(R.id.iniciarsesion);
        setGooglePlusButtonText(signInButton, "INGRESAR CON GOOGLE");
        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id),this);
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
        updateUI(fireBaseLoginService.getCurrentUser());
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
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        progressDialog.setMessage("Verificando usuario...");
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fireBaseLoginService.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            updateUI(fireBaseLoginService.getCurrentUser());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                        progressDialog.dismiss();
                        // ...
                    }
                });
    }


    private void updateUI(FirebaseUser account) {
        if (account != null) {
                Toast.makeText(this, "Bienvenido/a", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("email",fireBaseLoginService.getCurrentUser().getEmail());
                loadUserFromApi(fireBaseLoginService.getCurrentUser().getEmail(),bundle);
                Intent intent = new Intent(autenticacion.this, ProccesLogin.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }

    private void loadUserFromApi(String email, Bundle bundle) {
        tokenViewModel= ViewModelProviders.of(this).get(TokenViewModel.class);
        String token = tokenViewModel.getBearerToken(email);
        bundle.putString(AppConstant.USER_ISLOGIN,token);
        Log.i(TAG,token.toString());
    }

}


















