package com.example.allegroandroid.ui.core.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.ia.posedetector.ConstantPoseToEvalute;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.services.DateService;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

public class AdapterClasesFinalizadas extends RecyclerView.Adapter<AdapterClasesFinalizadas.adaptadorClasesF> {
    private int resource;
    private Context context;
    private ArrayList<HistorialDeClaseResponse> historialDeClaseResponseArrayList;
    private DateService dateService;

    public AdapterClasesFinalizadas(ArrayList<HistorialDeClaseResponse> historialDeClaseResponseArrayList, Context context, int reosource) {
        this.resource = reosource;
        this.context = context;
        this.historialDeClaseResponseArrayList = historialDeClaseResponseArrayList;
        dateService = DateService.getInstance();
    }

    class adaptadorClasesF extends RecyclerView.ViewHolder {
        TextView txtClaseName, txtMateriaName, txtTipoDeClaseFinalizada, txtNroClase, txtFecha;
        Button btnRepetirClase;

        public adaptadorClasesF(@NonNull View v) {
            super(v);
            txtClaseName = v.findViewById(R.id.txtClaseFinalizada);
            txtMateriaName = v.findViewById(R.id.txtMateriaFinalizada);
            txtNroClase = v.findViewById(R.id.txtNroDeClaseFinalizada);
            txtFecha = v.findViewById(R.id.txtFechaFinalizado);
            btnRepetirClase = v.findViewById(R.id.btnRepetirClase);
            txtTipoDeClaseFinalizada= v.findViewById(R.id.txtTipoDeClaseFinalizada);
        }
    }

    @NonNull
    @Override
    public adaptadorClasesF onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        AdapterClasesFinalizadas.adaptadorClasesF adaptadorClasesF = new AdapterClasesFinalizadas.adaptadorClasesF(view);
        return adaptadorClasesF;
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorClasesF holder, int position) {
        HistorialDeClaseResponse historialDeClaseResponse = historialDeClaseResponseArrayList.get(position);
        holder.txtClaseName.setText(historialDeClaseResponse.clase.name.toUpperCase(Locale.ROOT));
        holder.txtMateriaName.setText(historialDeClaseResponse.clase.materia.getName());
        holder.txtNroClase.setText(historialDeClaseResponse.clase.claseId.toString());
        holder.txtFecha.setText(dateService.convertStringToDateString(historialDeClaseResponse.dateModief));
        holder.txtTipoDeClaseFinalizada.setText(historialDeClaseResponse.clase.tipo);
        holder.btnRepetirClase.setOnClickListener(new View.OnClickListener() {
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            @Override
            public void onClick(View v) {
                historialDeClaseResponse.timeLeaveVideo = 0;
                String jsonStr = gson.toJson(historialDeClaseResponse);
                bundle.putString(AppConstant.HISTORIAL_DE_CLASE_RESPONSE, jsonStr);
                if (historialDeClaseResponse.clase.tipo.toLowerCase().equals(AppConstant.VIDEO.toLowerCase())) {
                    Navigation.findNavController(v).navigate(R.id.action_fragmentHistorialDeClase_to_youtubeReproducerActivity, bundle);
                } else {
                    Navigation.findNavController(v).navigate(R.id.action_fragmentHistorialDeClase_to_selectCamaraClasePracticaFragment, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return historialDeClaseResponseArrayList.size();
    }

}
