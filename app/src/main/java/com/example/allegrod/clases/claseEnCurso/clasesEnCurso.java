package com.example.allegrod.clases.claseEnCurso;

import android.content.Context;
import android.os.Bundle;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class clasesEnCurso extends Fragment {
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String usuario;
    private RecyclerView recyclerView;
    private Context context;
    private ProgressBar progressBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



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
        View v= inflater.inflate(R.layout.fragment_clases_en_curso, container, false);
        progressBar=v.findViewById(R.id.progressBarClasesEnCurso);
        progressBar.setVisibility(View.VISIBLE);
        obtenerUsuario();
        obtenerUsuarioAtuh();
        recyclerView = v.findViewById(R.id.recyclerViewClaseEnCursp);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        context=getContext();
        llenarRecycler();

        return v;
    }

    private void llenarRecycler(){
        obtenerClaseUsuarios obtner= new obtenerClaseUsuarios(usuario,progressBar);
        obtner.conectaraBasededatosHistorial();
        obtner.setAdapterClaseCurso("CLASICO","INICIAL",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("CLASICO","PRINCIPIANTE I",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("CLASICO","PRINCIPIANTE II",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("CLASICO","INTERMEDIO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("CLASICO","AVANZADO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("JAZZ","INICIAL",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("JAZZ","PRINCIPIANTE I",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("JAZZ","PRINCIPIANTE II",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("JAZZ","INTERMEDIO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("JAZZ","AVANZADO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("BALLET REPERTORIO","INTERMEDIO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("BALLET REPERTORIO","AVANZADO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("EJERCICIOS PARA FORTALECER PIE - PUNTAS","INTERMEDIO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("EJERCICIOS PARA FORTALECER PIE - PUNTAS","AVANZADO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("ELONGACIÓN","INICIAL",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("ELONGACIÓN","PRINCIPIANTE",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("ELONGACIÓN","INTERMEDIO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("ELONGACIÓN","AVANZADO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("YOGANCE","PRINCIPIANTE",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("YOGANCE","INTERMEDIO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("YOGANCE","AVANZADO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("CONTEMPORANEO","INICIAL",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("CONTEMPORANEO","PRINCIPIANTE",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("CONTEMPORANEO","INTERMEDIO",recyclerView,R.layout.adaptadorclaseencurso,context);
        obtner.setAdapterClaseCurso("CONTEMPORANEO","AVANZADO",recyclerView,R.layout.adaptadorclaseencurso,context);

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