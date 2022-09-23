package com.example.allegroandroid.ui.core.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.ia.posedetector.ConstantPoseToEvalute;
import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequestGet;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.models.user.User;
import com.example.allegroandroid.services.DateService;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class AdapterClases extends RecyclerView.Adapter<AdapterClases.ViewHolderClases> {
    int resource;
    Context context;
    private User user;
    private ArrayList<ClaseResponse> claseResponseList;
    private DateService dateService;

    public AdapterClases(ArrayList<ClaseResponse> claseResponseList, int resource, Context context, User user) {
        this.claseResponseList = claseResponseList;
        this.resource = resource;
        this.context = context;
        dateService = DateService.getInstance();
        this.user = user;
    }

    class ViewHolderClases extends RecyclerView.ViewHolder {
        private TextView txtNroClase, txtClaseDateCreated;
        private TextView txtNameClase;
        private Button btnInciarClase;
        public ViewHolderClases(@NonNull View v) {
            super(v);
            txtNroClase = v.findViewById(R.id.txtNroClase);
            txtNameClase = v.findViewById(R.id.txtNameClase);
            txtClaseDateCreated = v.findViewById(R.id.txtClaseDateCreated);
            btnInciarClase = v.findViewById(R.id.btnInciarClase);
        }
    }

    @NonNull
    @Override
    public ViewHolderClases onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolderClases(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderClases holder, int position) {
        final ClaseResponse claseResponse = claseResponseList.get(position);

        holder.txtNroClase.setText(claseResponse.claseId.toString());
        holder.txtNameClase.setText(claseResponse.name);
        holder.txtClaseDateCreated.setText(dateService.convertStringToDateString(claseResponse.dateCreated));
        holder.btnInciarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                HistorialDeClaseResponse historialDeClaseResponse = new HistorialDeClaseResponse(0, user.id, DateService.getInstance().getDateNow().toString(),
                        DateService.getInstance().getDateNow().toString(),claseResponse, false, 0, 0);
                Gson gson = new Gson();
                String jsonStr = gson.toJson(historialDeClaseResponse);
                bundle.putString(AppConstant.HISTORIAL_DE_CLASE_RESPONSE, jsonStr);

                if(claseResponse.tipo.toLowerCase().equals(AppConstant.VIDEO.toLowerCase())){
                    Navigation.findNavController(v).navigate(R.id.action_clasesFragment_to_youtubeReproducerActivity, bundle);
                }else {
                    Navigation.findNavController(v).navigate(R.id.action_clasesFragment_to_selectCamaraClasePracticaFragment, bundle);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return claseResponseList.size();
    }

}
