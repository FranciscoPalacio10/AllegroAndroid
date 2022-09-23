package com.example.allegroandroid.ui.core.clases.historialdeclases;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allegroandroid.R;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.ui.core.adapters.AdapterClasesNoFinalizadas;

import java.util.ArrayList;


public class ClasesNoFinalizadasFragment extends Fragment {
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<HistorialDeClaseResponse> historialDeClaseResponseArrayList;

    public ClasesNoFinalizadasFragment(ArrayList<HistorialDeClaseResponse> historialDeClaseResponseArrayList) {
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
        View v = inflater.inflate(R.layout.fragment_clases_en_curso, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewClaseEnCurso);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        context = getContext();
        AdapterClasesNoFinalizadas adaptadorClasesNoFinalizadas = new AdapterClasesNoFinalizadas(historialDeClaseResponseArrayList, context, R.layout.adaptadorclaseencurso);
        recyclerView.setAdapter(adaptadorClasesNoFinalizadas);
        return v;
    }


}
