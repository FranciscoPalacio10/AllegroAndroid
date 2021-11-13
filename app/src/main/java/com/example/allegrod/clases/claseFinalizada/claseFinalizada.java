package com.example.allegrod.clases.claseFinalizada;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.allegrod.R;
import com.example.allegrod.clases.clasesRegistradasLista;
import com.example.allegrod.clases.obtenerClaseUsuarios;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class claseFinalizada extends Fragment {
    private GoogleSignInClient mGoogleSignInClient;
    private ArrayList<clasesRegistradasLista> clasesRegistradasListaArrayList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String usuario;
    private RecyclerView recyclerView;
    private Context context;
    private ProgressBar progressBar;
    String estado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_clase_finalizada, container, false);
        progressBar=v.findViewById(R.id.progressBarCkasesFinalizadas);
        progressBar.setVisibility(View.VISIBLE);
        obtenerUsuario();
        obtenerUsuarioAtuh();
         recyclerView = v.findViewById(R.id.recylerClaseFinalizada);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        context=getContext();
        llenarRecycler();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("empezo","OnSTart");
    }

    private void llenarRecycler(){
        obtenerClaseUsuarios obtner= new obtenerClaseUsuarios(usuario,progressBar);
        obtner.conectaraBasededatosHistorial();
        obtner.llenarAdaptadores("CLASICO","INICIAL",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("CLASICO","PRINCIPIANTE I",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("CLASICO","PRINCIPIANTE II",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("CLASICO","INTERMEDIO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("CLASICO","AVANZADO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("JAZZ","INICIAL",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("JAZZ","PRINCIPIANTE I",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("JAZZ","PRINCIPIANTE II",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("JAZZ","INTERMEDIO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("JAZZ","AVANZADO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("BALLET REPERTORIO","INTERMEDIO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("BALLET REPERTORIO","AVANZADO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("EJERCICIOS PARA FORTALECER PIE - PUNTAS","INTERMEDIO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("EJERCICIOS PARA FORTALECER PIE - PUNTAS","AVANZADO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("ELONGACIÓN","INICIAL",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("ELONGACIÓN","PRINCIPIANTE",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("ELONGACIÓN","INTERMEDIO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("ELONGACIÓN","AVANZADO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("YOGANCE","PRINCIPIANTE",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("YOGANCE","INTERMEDIO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("YOGANCE","AVANZADO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("CONTEMPORANEO","INICIAL",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("CONTEMPORANEO","PRINCIPIANTE",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("CONTEMPORANEO","INTERMEDIO",recyclerView,R.layout.adaptadorclasefinalizada,context);
        obtner.llenarAdaptadores("CONTEMPORANEO","AVANZADO",recyclerView,R.layout.adaptadorclasefinalizada,context);

    }



    private void obtenerUsuario() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail ()
                .build();
        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void obtenerUsuarioAtuh(){
        user=FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            usuario= user.getUid();
            Log.e("id",usuario);
        }

    }
}
