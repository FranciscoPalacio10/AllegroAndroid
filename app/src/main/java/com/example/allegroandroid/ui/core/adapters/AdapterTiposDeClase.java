package com.example.allegroandroid.ui.core.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.TiposDeClases;
import com.example.allegroandroid.ui.core.clases.ClasesFragment;

import java.util.List;

public class AdapterTiposDeClase extends RecyclerView.Adapter<AdapterTiposDeClase.ViewHolderString> {
    int resource;
    Context context;
    private List<TiposDeClases> tiposDeClase;

    public AdapterTiposDeClase(List<TiposDeClases> tiposDeClase, int resource, Context context) {
        this.tiposDeClase = tiposDeClase;
        this.resource = resource;
        this.context = context;
    }

    class ViewHolderString extends RecyclerView.ViewHolder {
        private TextView txtPose;
        private ImageView imageView;

        public ViewHolderString(@NonNull View v) {
            super(v);
            txtPose = v.findViewById(R.id.txtPose);
            imageView = v.findViewById(R.id.imagenTipoClase);
        }
    }


    @NonNull
    @Override
    public ViewHolderString onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolderString(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderString holder, int position) {
        final TiposDeClases tiposDeClases = tiposDeClase.get(position);

        holder.txtPose.setText(tiposDeClases.name);
        holder.imageView.setImageResource(tiposDeClases.resourceId);
        holder.txtPose.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.TIPO_CLASE_SELECT, tiposDeClases.name);
            bundle.putInt(AppConstant.ID_MATERIA, tiposDeClases.materiaId);
            Navigation.findNavController(v).navigate(R.id.action_tipoDeClaseFragment_to_clasesFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return tiposDeClase.size();
    }

}
