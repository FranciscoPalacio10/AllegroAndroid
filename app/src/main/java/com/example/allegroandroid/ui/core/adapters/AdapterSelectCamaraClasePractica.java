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
import com.example.allegroandroid.models.CamaraSelect;
import com.google.gson.Gson;

import java.util.List;


public class AdapterSelectCamaraClasePractica extends RecyclerView.Adapter<AdapterSelectCamaraClasePractica.ViewHolderMaterias> {
    int resource;
    Context context;
    private List<CamaraSelect> camarasList;

    public AdapterSelectCamaraClasePractica(List<CamaraSelect> camarasList, int resource, Context context) {
        this.camarasList = camarasList;
        this.resource = resource;
        this.context = context;
    }

    class ViewHolderMaterias extends RecyclerView.ViewHolder {
        private TextView txtCameraName, txtCameraDescription;
        private Button btnSelectCamara;

        public ViewHolderMaterias(@NonNull View v) {
            super(v);
            txtCameraName = v.findViewById(R.id.txtCameraName);
            txtCameraDescription = v.findViewById(R.id.txtCameraDescription);
            btnSelectCamara = v.findViewById(R.id.btnSelectCamara);
        }
    }

    @NonNull
    @Override
    public ViewHolderMaterias onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolderMaterias(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMaterias holder, int position) {
        final CamaraSelect camaraSelect = camarasList.get(position);
        holder.txtCameraName.setText(camaraSelect.getName());
        holder.txtCameraDescription.setText(camaraSelect.getDescrption());
        holder.btnSelectCamara.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String jsonStr = gson.toJson(camaraSelect.historialDeClaseResponse);
            bundle.putString(AppConstant.HISTORIAL_DE_CLASE_RESPONSE, jsonStr);
            Navigation.findNavController(v).navigate(camaraSelect.getNavigationDestination(), bundle);
        });
    }

    @Override
    public int getItemCount() {
        return camarasList.size();
    }

}
