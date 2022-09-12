package com.example.allegroandroid.ui.core.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.MateriasResponse;

import java.util.List;


public class AdapterMaterias extends RecyclerView.Adapter<AdapterMaterias.ViewHolderMaterias> {
    int resource;
    Context context;
    private List<MateriasResponse> materiasList;

    public AdapterMaterias(List<MateriasResponse> materiasList, int resource, Context context) {
        this.materiasList =materiasList;
        this.resource=resource;
        this.context=context;
    }

    class ViewHolderMaterias extends RecyclerView.ViewHolder{
        private ImageView btnestiloClase;
        private TextView nombreEstilo;
        public ViewHolderMaterias(@NonNull View v) {
            super(v);
            btnestiloClase=v.findViewById(R.id.imageButtonEstilo);
            nombreEstilo=v.findViewById(R.id.txtEstilo);
        }
    }

    @NonNull
    @Override
    public ViewHolderMaterias onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
            return new ViewHolderMaterias(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderMaterias holder, int position) {
            final MateriasResponse materias= materiasList.get(position);
            holder.btnestiloClase.setImageResource(R.drawable.barepertorio);
            holder.nombreEstilo.setText(materias.getName());
            holder.btnestiloClase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(AppConstant.ID_MATERIA, (int) materias.getId());
                        Navigation.findNavController(v).navigate(R.id.action_materiasFragment_to_tipoClaseFragment,bundle);
                }
            });

    }

    @Override
    public int getItemCount() {
        return materiasList.size();
    }

}
