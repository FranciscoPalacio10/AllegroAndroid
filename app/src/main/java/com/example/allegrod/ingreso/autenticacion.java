package com.example.allegrod.ingreso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.allegrod.R;
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
    private String TAG="autenticacion";
    private Button btnRegistro;
      private Button btniniciarsesion;
    private EditText btnTextEmail;
    private EditText btnextPassword;
    private TextView ovlidasteContraseña;
    private ProgressDialog progressDialog;
      private SignInButton inicioSesion;
    public static final int RC_SIGN_IN = 7;
    private GoogleSignInClient  mGoogleSignInClient;
    private SignInButton signInButton;
    private  View promptsView;
    private  FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private ProgressBar cargar;
    private FirebaseAuth mAuth;
    @Override
    protected void onDestroy() {
        NotificationManager notificationManager = ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE));
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
        btnRegistro=findViewById(R.id.btnRegistrar);
        btnTextEmail=findViewById(R.id.txtMail);
        btnextPassword=findViewById(R.id.txtPaswaord);
        btniniciarsesion=findViewById(R.id.btningresar);
        ovlidasteContraseña=findViewById(R.id.txtOlvidasteContrseña);

        mAuth = FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);
         signInButton= findViewById(R.id.iniciarsesion);
         setGooglePlusButtonText(signInButton,"INGRESAR CON GOOGLE");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ovlidasteContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                promptsView = li.inflate(R.layout.dialogorestablecercontrasena, null);
                dialogoRestablacerContraseña();
            }
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regitrar(v);
            }
        });
        btniniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguearUsuario(v);
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        }

    private void dialogoRestablacerContraseña() {
        AlertDialog.Builder builder = new AlertDialog.Builder(autenticacion.this);
        builder.setView(promptsView);
        EditText userEmail = promptsView.findViewById(R.id.txtEmailRestablecer);
        builder.setTitle("Restablecer contraseña");
        builder.setCancelable(false);
        builder.setPositiveButton("Enviar Mail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restablecerContraseña(userEmail);
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void restablecerContraseña(EditText userEmail) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = userEmail.getText().toString();
        // Toast.makeText(getApplicationContext(),"Cambia la contraseña en la web de GMAIL", Toast.LENGTH_SHORT).show();
        if(validarEmail(emailAddress)){
            if(emailAddress.contains("@gmail")){
                Toast.makeText(getApplicationContext(),"Cambie la contraseña en GMAIL.COM ", Toast.LENGTH_SHORT).show();
            }else{
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Se envio el email con éxito", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"No existe este usuario, por favor registrese", Toast.LENGTH_SHORT).show();
                                }

                            }


                        });
            }

            return;

        }else{
            Toast.makeText(getApplicationContext(),"Ingrese un mail valido", Toast.LENGTH_SHORT).show();
        }




    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
                // ...
            }
        }

           // handleSignInResult(task);
        }

/*
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            updateUI(null);
        }
    }

 */

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        progressDialog.setMessage("Verificando usuario...");
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
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
        if(account != null){
            if(account.isEmailVerified()){
            Toast.makeText(this,"Bienvenido/a",Toast.LENGTH_SHORT).show();
            Bundle bundle= new Bundle();
            bundle.putString("email",mAuth.getCurrentUser().getEmail());
            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent,bundle);
        }else {
            Toast.makeText(this,"Verifca tu mail",Toast.LENGTH_SHORT).show();
        }
    }
    }


    public void regitrar(View view){
        Intent intent = new Intent(getApplicationContext(), com.example.allegrod.ingreso.registro.class);
        startActivity(intent);
    }

    public void loguearUsuario(View view) {
        //Obtenemos el email y la contraseña desde las cajas de texto
         String email = btnTextEmail.getText().toString().trim();
        String password = btnextPassword.getText().toString().trim();
        if (validarDatos(email, password)) return;
        progressDialog.setMessage("Verificando usuario..");
        progressDialog.show();
        //loguear usuario
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user= mAuth.getCurrentUser();
                            if(user!=null){
                                if(!user.isEmailVerified()){
                                    Toast.makeText(getApplicationContext(),"Debe verificar el usuario",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(autenticacion.this, "Bienvenido/a: ", Toast.LENGTH_SHORT).show();
                                    Intent intencion = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intencion);
                                }
                            }
                        }
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      String ea=e.getMessage();
                      if(ea.equals("The password is invalid or the user does not have a password.")){
                          Toast.makeText(autenticacion.this, "Contraseña invalida", Toast.LENGTH_SHORT).show();
                      }if(ea.equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                            Toast.makeText(autenticacion.this, "Este usuario no existe, por favor registrese.", Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

    private boolean validarDatos(String email, String password) {
        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!validarEmail(email)) {//(precio.equals(""))
            Toast.makeText(this, "Email invalido", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (email.contains("@gmail.com")) {//(precio.equals(""))
            Toast.makeText(this, "Ingrese con el boton de Google", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}


















