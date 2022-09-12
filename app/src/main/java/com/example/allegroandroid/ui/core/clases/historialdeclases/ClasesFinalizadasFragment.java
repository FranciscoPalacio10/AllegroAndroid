package com.example.allegroandroid.ui.core.clases.historialdeclases;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allegroandroid.R;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.ui.core.adapters.AdapterClasesFinalizadas;

import java.util.ArrayList;

public class ClasesFinalizadasFragment extends Fragment {
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<HistorialDeClaseResponse> historialDeClaseResponseArrayList;

    public ClasesFinalizadasFragment(ArrayList<HistorialDeClaseResponse> historialDeClaseResponseArrayList) {
        this.historialDeClaseResponseArrayList = historialDeClaseResponseArrayList;
    }

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
        recyclerView = v.findViewById(R.id.recylerClaseFinalizada);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        context=getContext();
        AdapterClasesFinalizadas adaptadorClasesFinalizadas = new AdapterClasesFinalizadas(historialDeClaseResponseArrayList, context, R.layout.adaptadorclasefinalizada);
        recyclerView.setAdapter(adaptadorClasesFinalizadas);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
