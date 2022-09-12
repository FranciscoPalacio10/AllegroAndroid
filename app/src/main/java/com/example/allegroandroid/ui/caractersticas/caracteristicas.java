package com.example.allegroandroid.ui.caractersticas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.example.allegroandroid.R;
import com.example.allegroandroid.ui.start.AuthenticationActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;


public class caracteristicas extends Fragment{
    private String nombreyapelldio;
    private TextView email,nombre;
    private ImageView imgUsuario;
    private Button salir;
    private DatabaseReference basededatos;
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ArrayList<caracteristicasItems> caracteristicasItems = new ArrayList<>();
    private FirebaseUser user;
   private RecyclerView recyclerView;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigateUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail ()
                .build();
        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        View v= inflater.inflate(R.layout.fragment_caracteristicas, container, false);
        email=v.findViewById(R.id.txtUsuario);
        nombre=v.findViewById(R.id.txtNombre);
        imgUsuario=v.findViewById(R.id.imageUsuario);

        user=FirebaseAuth.getInstance().getCurrentUser();

        obtenerUsuario();
        llenarRecycler(v);
        return v;
    }

    private void llenarRecycler(View v) {
        recyclerView=v.findViewById(R.id.recyclerCaracteristicas);
       GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(linearLayoutManager);
        caracteristicasItems.add(new caracteristicasItems(R.drawable.suscrpicion2,"Suscripción"));
        caracteristicasItems.add(new caracteristicasItems(R.drawable.identidad,"Cuenta"));
        caracteristicasItems.add(new caracteristicasItems(R.drawable.social,"Social"));
        caracteristicasItems.add(new caracteristicasItems(R.drawable.informacion,"Terminos de uso"));
        caracteristicasItems.add(new caracteristicasItems(R.drawable.ic_phone_black_24dp,"Contacto"));
        caracteristicasItems.add(new caracteristicasItems(R.drawable.ic_stars_black_24dp,"Staff"));
        caracteristicasItems.add(new caracteristicasItems(R.drawable.cerrarsesion,"Cerrrar Sesión"));
        adaptadorCaractersticas adaptadorCaractersticas= new adaptadorCaractersticas(caracteristicasItems,R.layout.caracteristicasitems,getContext(),mGoogleSignInClient,getActivity());
        recyclerView.setAdapter(adaptadorCaractersticas);
    }

    private void obtenerUsuarioAtuh(){
        basededatos=FirebaseDatabase.getInstance().getReference();
        if (user != null) {
            String id= user.getUid();
            Log.e(id,id);
            basededatos.child("Usuario").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        String email= dataSnapshot.child("email").getValue().toString();
                        String nombres= dataSnapshot.child("nombre").getValue().toString();
                        String apellido= dataSnapshot.child("apellido").getValue().toString();
                        nombreyapelldio=nombres.concat(" ").concat(apellido);
                        nombre.setText(nombreyapelldio);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });



        }

    }

    private void obtenerUsuario(){
        obtenerUsuarioAtuh();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                String provideId= profile.getProviderId();
                String hola=profile.getDisplayName();
                Log.e("hola",hola);
                Log.e("Ppoiver",provideId);
            }
            String nombre = user.getDisplayName();
            Log.e("nobre",nombre);
            String personEmail = user.getEmail();
            Uri personPhoto = user.getPhotoUrl();
            email.setText(personEmail);
            this.nombre.setText(nombre);


            if(personPhoto!=null){
                Glide.with(getContext()).
                        load(String.valueOf(personPhoto)).apply(RequestOptions.circleCropTransform()).
                        into(imgUsuario);
                          }else {
                imgUsuario.setImageResource(R.drawable.unabailarina);
                imgUsuario.setBackgroundResource(R.color.transeparente);
            }
        }

    }

    private void obtenerUsuarioGoogle() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            String nombre = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            //Glide.with(getContext()).load(String.valueOf(personPhoto)).into(imagen);

        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(),"Cerro sesión con éxito", Toast.LENGTH_LONG).show();
                        salir(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    }
                });
    }
    private void revokeAccess () {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete()
                .addOnCompleteListener (getActivity(), new OnCompleteListener <Void> () {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(),"Eliminaste el usuario con exito", Toast.LENGTH_LONG).show();

                salir(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }
        });
    }

    private void salir(int i) {
        Intent intent = new Intent(getContext(), AuthenticationActivity.class);
        intent.setFlags(i);
        startActivity(intent);
    }



}








