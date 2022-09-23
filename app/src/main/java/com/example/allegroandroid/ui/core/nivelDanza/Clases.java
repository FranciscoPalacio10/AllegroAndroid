package com.example.allegroandroid.ui.core.nivelDanza;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allegroandroid.R;
import com.example.allegroandroid.clickleable;

import java.util.ArrayList;


public class Clases extends Fragment {

    private TextView txtTitulo, txtLeyenda;
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<nivelClase> nivelClaseArrayList = new ArrayList<>();
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
        View v = inflater.inflate(R.layout.fragment_nivel_clase_seleccionado, container, false);
        txtTitulo = v.findViewById(R.id.txtTitulo);
        txtLeyenda = v.findViewById(R.id.txtDescripcion);
        mostrarDatos(txtTitulo, txtLeyenda);
        recyclerView = v.findViewById(R.id.reclyclerNivel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        context = getContext();
        obtnerNiveles(txtTitulo);
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void obtnerNiveles(TextView titulo) {
        adaptadorNivel adaptadorNivel = new adaptadorNivel(nivelClaseArrayList, R.layout.adaptadornivel, context);
        recyclerView.setAdapter(adaptadorNivel);
    }

    private void mostrarDatos(TextView txt1, TextView txtLeyenda) {
        estilo = getArguments().getString("estilo");
        String leyenda = getArguments().getString("leyenda");

        txt1.setText(estilo);

        txtLeyenda.setText(leyenda);
        mostrarInformacion(txt1);
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
