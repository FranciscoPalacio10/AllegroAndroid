package com.example.allegroandroid.ui.core.clases;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allegroandroid.BuildConfig;
import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.TiposDeClases;
import com.example.allegroandroid.ui.core.adapters.AdapterTiposDeClase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TiposDeClaseFragment extends Fragment {
    private static final String TAG = "ClasePracticaFragment";
    private Integer idMateria;
    public TiposDeClaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder()
                            .detectLeakedSqlLiteObjects()
                            .detectLeakedClosableObjects()
                            .penaltyLog()
                            .build());
        }
        if (getArguments() != null) {
            idMateria = getArguments().getInt(AppConstant.ID_MATERIA);
        }


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tipo_de_clase, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.reciclyer_tipos_de_clase);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        TiposDeClases tipoVideo = new TiposDeClases(AppConstant.VIDEO, R.drawable.ic_baseline_play_circle_outline_24, idMateria);
        TiposDeClases tipoPractica = new TiposDeClases(AppConstant.PRACTICA, R.drawable.barepertorio, idMateria);

        List<TiposDeClases> tiposDeClaseList = new ArrayList<>();

        tiposDeClaseList.add(tipoVideo);
        tiposDeClaseList.add(tipoPractica);


        AdapterTiposDeClase adapterTiposDeClase = new AdapterTiposDeClase(tiposDeClaseList, R.layout.tipodeclase, getContext());
        recyclerView.setAdapter(adapterTiposDeClase);

        return v;

    }
}