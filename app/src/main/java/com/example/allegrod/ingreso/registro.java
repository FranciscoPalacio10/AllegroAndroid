package com.example.allegrod.ingreso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allegrod.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class registro extends AppCompatActivity {


    private Button registrar;
    private EditText txtUsuario,txtApelldio;
    private EditText txtContraseña;
    private EditText txtNombre;
    private ProgressDialog cargar;
    private FirebaseAuth firebaseAuth;
    private  DatabaseReference databaseReference;

     //variables usuarios
     private String email,nombreM;
    private String nombre,apellidoM;
    private String apelldio;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        registrar=findViewById(R.id.registrar);
        txtUsuario=findViewById(R.id.registrousuario);
        txtContraseña=findViewById(R.id.registrocontrasena);
        txtApelldio=findViewById(R.id.apellido);
        txtNombre=findViewById(R.id.nombre);
        cargar= new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        medidasActivityFlotante();
    }

    private void medidasActivityFlotante() {
        DisplayMetrics dn = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dn);
        int width= dn.widthPixels;
        int height=dn.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.6));
        WindowManager.LayoutParams params= getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        getWindow().setAttributes(params);
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }
    public void registrarUsuario(View view){
        //Obtenemos el email y la contraseña desde las cajas de texto
       email = txtUsuario.getText().toString().trim();
       password  = txtContraseña.getText().toString().trim();
       nombre= txtNombre.getText().toString().trim();
        nombreM=ucFirst(nombre);
       apelldio=txtApelldio.getText().toString().trim();
        apellidoM=ucFirst(apelldio);
        if (verificarCampos()) return;
        cargar.setMessage("Realizando registro en linea...");
        cargar.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            verificarEmail();
                            registrarEnBaseDeDatos();
                        }else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(registro.this,"Email ya registrado, ingrese uno nuevo",Toast.LENGTH_LONG).show();
                                 limpiarCamposEmail();
                             }else{
                                 Toast.makeText(registro.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                             }
                        cargar.dismiss();
                        }
                });
    }

    private boolean verificarCampos() {
        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this,"Falta ingresar el nombre",Toast.LENGTH_LONG).show();
            return true;
        }
        if(TextUtils.isEmpty(apelldio)){
            Toast.makeText(this,"Falta ingresar apellido",Toast.LENGTH_LONG).show();
            return true;
        }
        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return true;
        }
        if(!validarEmail(email)){
            Toast.makeText(this,"Email Invalido",Toast.LENGTH_LONG).show();
            return true;
        }
        if(email.contains("@gmail.com")){
            Toast.makeText(this,"Registro invalido, ingresa con el boton de Google",Toast.LENGTH_LONG).show();
            return true;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return true;
        }
        if(password.length()<6){
            Toast.makeText(this,"La contraseña debe contener al menos 6 caracteres.",Toast.LENGTH_LONG).show();
            return true;
        }

        return false;

    }

    private void registrarEnBaseDeDatos() {
        Map<String, Object> map=new HashMap<>();
        map.put("email",email);
        map.put("nombre",nombreM);
        map.put("apellido",apellidoM);
        String id= firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Usuario").child(id).setValue(map);
    }

    private void verificarEmail() {
        Toast.makeText(registro.this,"Debes verificar el usuario en el email: "
                + txtUsuario.getText(),Toast.LENGTH_SHORT).show();
        FirebaseUser user1=firebaseAuth.getCurrentUser();
        user1.sendEmailVerification();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtUsuario.setText("");
        txtUsuario.setHint(R.string.ingrese_mail);
        txtContraseña.setText("");
        txtContraseña.setHint(R.string.ingrese_contraseña);
        txtApelldio.setText("");
        txtApelldio.setHint(R.string.ingrese_apellido);
        txtNombre.setText("");
        txtNombre.setHint(R.string.ingrese_nombre);
    }

    private void limpiarCamposEmail() {
        txtUsuario.setText("");
        txtUsuario.setHint(R.string.Modifique_mail);
    }
    //creating a new user


}









