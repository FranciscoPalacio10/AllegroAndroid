package com.example.allegrobackend.ui.home.nivelDanza;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allegrobackend.R;
import com.example.allegrobackend.clickleable;

import java.util.ArrayList;


public class Clases extends Fragment{

    private TextView txtTitulo,txtLeyenda;
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<nivelClase> nivelClaseArrayList = new ArrayList<>();
    private String inicial="INICIAL";
    private String principitanteI="PRINCIPIANTE I";
    private String principitanteII="PRINCIPIANTE II";
    private String intermedio="INTERMEDIO";
    private String avanzado="AVANZADO";
    private String principitante="PRINCIPIANTE";
    private String estilo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).popBackStack();
            }

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_nivel_clase_seleccionado, container, false);
        txtTitulo=v.findViewById(R.id.txtTitulo);
        txtLeyenda=v.findViewById(R.id.txtDescripcion);
         mostrarDatos(txtTitulo,txtLeyenda);
        recyclerView=v.findViewById(R.id.reclyclerNivel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        context=getContext();
        obtnerNiveles(txtTitulo);
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void obtnerNiveles(TextView titulo){
        nivelClaseArrayList.clear();
        switch (titulo.getText().toString()){
            case "BALLET REPERTORIO":
            case "EJERCICIOS PARA FORTALECER PIE-PUNTAS":
                nivelIntermedio();
                nivelAvanzado();
                break;
            case "JAZZ":
            case "CLASICO":
                nivelInicial();
                nivel1Principiante();
                nivel2Principiante();
                nivelIntermedio();
                nivelAvanzado();
                break;
            case "YOGANCE":
                nivelPrincipiante1();
                nivelIntermedio1();
                nivelAvanzado1();
                break;
            case "CONTEMPORANEO":
            case "ELONGACIÓN":
                nivelInicial();
                nivelPrincipiante();
               nivelIntermedio1();
                nivelAvanzado1();
                break;
        }
        adaptadorNivel adaptadorNivel= new adaptadorNivel(nivelClaseArrayList,R.layout.adaptadornivel,context);
        recyclerView.setAdapter(adaptadorNivel);
                }

    private void mostrarDatos(TextView txt1,TextView txtLeyenda){
        estilo= getArguments().getString("estilo");
        String leyenda= getArguments().getString("leyenda");

            txt1.setText(estilo);

        txtLeyenda.setText(leyenda);
        mostrarInformacion(txt1);
                     }

    private void nivelInicial(){
        if(estilo.equals("CLASICO")){
            nivelClaseArrayList.add(new nivelClase(inicial,estilo,R.drawable.definicial,getString(R.string.descripcionNivelIncialClasica)));
        }else if(estilo.equals("JAZZ")){
            nivelClaseArrayList.add(new nivelClase(inicial,estilo,R.drawable.definicial,getString(R.string.descripcionNivelIncialJazz)));
        }else if(estilo.equals("CONTEMPORANEO")) {
            nivelClaseArrayList.add(new nivelClase(inicial,estilo,R.drawable.definicial,getString(R.string.descripcionNivelIncicialConte)));
        }else  {
            nivelClaseArrayList.add(new nivelClase(inicial,estilo,R.drawable.definicial,getString(R.string.descripcionNivelIncicial)));
        }

    }
    private void nivel1Principiante(){
        nivelClaseArrayList.add(new nivelClase(principitanteI,estilo,R.drawable.ic_nivelprincipiantei,getString(R.string.descripcionPrincipianteI)));
    }
    private void nivel2Principiante(){
        nivelClaseArrayList.add(new nivelClase(principitanteII,estilo,R.drawable.ic_niveliprincipiate2,getString(R.string.descripcionPrincipianteII)));

    }
    private void nivelIntermedio(){
        nivelClaseArrayList.add(new nivelClase(intermedio,txtTitulo.getText().toString(),R.drawable.ic_nivelintermedio,getString(R.string.descripcionIntermedio)));
        Log.e("nombre",txtTitulo.getText().toString());
        Log.e("nombreRecuperado",estilo);
    }
    private void nivelAvanzado(){
        nivelClaseArrayList.add(new nivelClase(avanzado,txtTitulo.getText().toString(),R.drawable.ic_nivelavanzado,getString(R.string.descripcionAvanzado)));
    }
    private void nivelPrincipiante(){
        nivelClaseArrayList.add(new nivelClase(principitante,estilo,R.drawable.ic_nivelprincipiantei,getString(R.string.descripcionPrincipianteII)));
    }
    private void nivelPrincipiante1(){
        nivelClaseArrayList.add(new nivelClase(principitante,estilo,R.drawable.definicial,getString(R.string.descripcionPrincipianteII)));
    }
    private void nivelIntermedio1(){
        nivelClaseArrayList.add(new nivelClase(intermedio,estilo,R.drawable.ic_niveliprincipiate2,getString(R.string.descripcionIntermedio)));
    }
    private void nivelAvanzado1(){
        nivelClaseArrayList.add(new nivelClase(avanzado,txtTitulo.getText().toString(),R.drawable.ic_nivelintermedio,getString(R.string.descripcionAvanzado)));
    }

    private void mostrarInformacion(TextView txt1) {
        SpannableString spannableString = new SpannableString(estilo);
        txt1.setText(spannableString);
        txt1.setMovementMethod(LinkMovementMethod.getInstance());

        clickleable clickable = new clickleable() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(getContext(), "Hiciste click", Toast.LENGTH_SHORT).show();
            }
        };
        clickable.wrap(txt1);
        clickable.enableHighlightMode(Color.TRANSPARENT, Color.BLUE);
        spannableString.setSpan(clickable, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
