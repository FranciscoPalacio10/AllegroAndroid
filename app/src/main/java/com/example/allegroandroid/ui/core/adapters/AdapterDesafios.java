package com.example.allegroandroid.ui.core.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.desafios.DesafiosXUserResponse;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.services.DateService;
import com.google.gson.Gson;

import java.util.ArrayList;


public class AdapterDesafios extends RecyclerView.Adapter<AdapterDesafios.ViewHolderDesafios> {
    int resource;
    Context context;
    private ArrayList<DesafiosXUserResponse> desafiosResponseList;
    private DateService dateService;
    private TextView txtPoints;

    public AdapterDesafios(ArrayList<DesafiosXUserResponse> desafiosResponseList, int resource, Context context, TextView txtPoints) {
        this.desafiosResponseList = desafiosResponseList;
        this.resource = resource;
        this.context = context;
        this.txtPoints = txtPoints;
        dateService = DateService.getInstance();
    }

    class ViewHolderDesafios extends RecyclerView.ViewHolder {
        private TextView desafioName, desafionDescription, tipoDeDesafio;
        private TextView txtRealizado, txtTotal, txtPointsNow, txtPointsTotal;
        private LinearLayout linearLayoutPuntos, linearlayoutdesafio;
        public ViewHolderDesafios(@NonNull View v) {
            super(v);
            desafioName = v.findViewById(R.id.desafioname);
            desafionDescription = v.findViewById(R.id.desafiondescription);
            tipoDeDesafio = v.findViewById(R.id.tipodedesafio);
            txtRealizado = v.findViewById(R.id.txtrealizado);
            txtTotal = v.findViewById(R.id.txttotal);
            txtPointsNow = v.findViewById(R.id.txtpointsnow);
            txtPointsTotal = v.findViewById(R.id.txtPointsTotal);
            linearLayoutPuntos = v.findViewById(R.id.linearlayoutpuntos);
            linearlayoutdesafio = v.findViewById(R.id.linearlayoutdesafio);

        }
    }

    @NonNull
    @Override
    public ViewHolderDesafios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolderDesafios(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDesafios holder, int position) {
        final DesafiosXUserResponse desafiosXUserResponse = desafiosResponseList.get(position);

        if(desafiosXUserResponse.isCompleted){
            holder.linearlayoutdesafio.setBackground(context.getResources().getDrawable(R.drawable.circulodesafiocompleto));
            holder.linearLayoutPuntos.setBackground(context.getResources().getDrawable(R.drawable.circulodesafiocompleto));
        }


        holder.desafioName.setText(desafiosXUserResponse.name);
        holder.tipoDeDesafio.setText(desafiosXUserResponse.tipo);
        holder.desafionDescription.setText(desafiosXUserResponse.description);
        holder.txtPointsTotal.setText(desafiosXUserResponse.pointsTotal.toString());
        holder.txtPointsNow.setText(desafiosXUserResponse.pointsNow.toString());
        holder.txtTotal.setText(desafiosXUserResponse.cantidadTotal.toString());
        holder.txtRealizado.setText(desafiosXUserResponse.cantidadComplete.toString());

        String pointsBefore = txtPoints.getText().toString();
        String pointNumber = pointsBefore.replaceAll("[\\D]", "");
        if(!pointNumber.isEmpty()){
            Integer pointsNow = Integer.valueOf(pointNumber) + desafiosXUserResponse.pointsNow;
            txtPoints.setText(pointsNow.toString() + " PUNTOS");
        }


    }

    @Override
    public int getItemCount() {
        return desafiosResponseList.size();
    }

}
