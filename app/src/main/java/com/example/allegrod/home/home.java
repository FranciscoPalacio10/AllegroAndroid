package com.example.allegrod.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.allegrod.R;
import com.example.allegrod.home.estiloDanza.adaptadorEstilo;
import com.example.allegrod.home.estiloDanza.estiloDanza;
import com.example.allegrod.ingreso.autenticacion;
import com.example.allegrod.obtenerFecha;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class home extends Fragment {

    private ArrayList<estiloDanza> danzaArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Context context;
    private Button iniciarclase;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    View v;
    CollapsingToolbarLayout toolbar;
    private FirebaseUser acct;
    private StorageReference pathReference ;
    private int contador=0;
    private DatabaseReference Database, Database1;
    private boolean USUARIO_PRIVILEGIADO;
    private String usuariosObtenidos;
    private String UsuarioActual;
    private ProgressBar progressBar;
    public home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(contador==0){
                    Toast.makeText(getContext(),"Presione de nuevo para salir",Toast.LENGTH_SHORT).show();
                    contador++;
                }else {
                    Intent intent = new Intent(getContext(), autenticacion.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    startActivity(intent);

                }
                new CountDownTimer(3000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        contador=0;
                    }
                }.start();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);




    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_home, container, false);
        toolbar=v.findViewById(R.id.toolbar);
        progressBar=v.findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.VISIBLE);
        USUARIO_PRIVILEGIADO=false;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail ()
                .build();
        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        acct=FirebaseAuth.getInstance().getCurrentUser();
        Database = FirebaseDatabase.getInstance().getReference();
        //referencia storage
        pathReference= FirebaseStorage.getInstance().getReference();
        //recylerView
        recyclerView=v.findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        context=getContext();
        Log.e("actual",acct.getEmail());
        esUsuarioPrivilegiado();
        //llenarRecycler


        return v;

            }



    private void llenarEstilo(){
        danzaArrayList.clear();
        String f="FORTALECER"
                + System.getProperty ("line.separator") +
                "PIE Y PUNTAS";
        String b="BALLET"
                + System.getProperty ("line.separator") +
                "REPERTORIO";
        String c=getString(R.string.clasica);
        String d="CLASICO";
        String j=getString(R.string.JAZZ);
        String e= getString(R.string.elongacion);
        String co=getString(R.string.contemporaneo);
        String y=getString(R.string.yogance);
        String k=getString(R.string.fitnesDance);
        String ta="SUBIR CLASES";
            danzaArrayList.add(new estiloDanza(R.drawable.barepertorio,b,getString(R.string.descripcionBallet),getString(R.string.balletrepertorio1)));
            danzaArrayList.add(new estiloDanza(R.drawable.puntaspie,f,getString(R.string.descripcionFortalcerPuntas),getString(R.string.fortalecer1)));
            danzaArrayList.add(new estiloDanza(R.drawable.bailarinaclas,d,getString(R.string.descripcionClasica),d));
            danzaArrayList.add(new estiloDanza(R.drawable.elonga,e,getString(R.string.descripcionElongacion),e));
            danzaArrayList.add(new estiloDanza(R.drawable.danzajazzzz,j,getString(R.string.descripcionJazz),j));
            danzaArrayList.add(new estiloDanza(R.drawable.yogane,y,getString(R.string.descripcionYogance),y));
            danzaArrayList.add(new estiloDanza(R.drawable.cont,co,getString(R.string.descripcionContemporaneo),co));
           if(acct.getEmail().equals(usuariosObtenidos)){
               danzaArrayList.add(new estiloDanza(R.drawable.tablerocontrol,ta,"lol",ta));
           }

        adaptadorEstilo adaptadorEstilo = new adaptadorEstilo(danzaArrayList,R.layout.adaptadorestilo,context);
        recyclerView.setAdapter(adaptadorEstilo);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void esUsuarioPrivilegiado(){
        Database.child("Usuario priviligiado").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if(dataSnapshot.exists()){
                  for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                       usuariosObtenidos=dataSnapshot1.child("email").getValue().toString();
                       llenarEstilo();
                       progressBar.setVisibility(View.GONE);
                  }
              }

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

    }

    @Override
    public void onStart() {
        super.onStart();

    }



}
