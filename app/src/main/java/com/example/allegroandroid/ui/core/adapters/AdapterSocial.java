package com.example.allegroandroid.ui.core.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.models.CaracteristicasSocial;

import java.util.ArrayList;

public class AdapterSocial extends RecyclerView.Adapter<AdapterSocial.AdpterSocialHolder> {
    private ArrayList<CaracteristicasSocial> caracteristicasSocials;
    private Context context;
    private int resource;

    public AdapterSocial(ArrayList<CaracteristicasSocial> caracteristicasSocials, Context context, int resource) {
        this.caracteristicasSocials = caracteristicasSocials;
        this.context = context;
        this.resource = resource;
    }

    class AdpterSocialHolder extends RecyclerView.ViewHolder {
        LinearLayout linearlayoutredsocial;
        TextView txtNameNetwork, txtNetworkContact;
        ImageView imageViewNetwork;

        public AdpterSocialHolder(@NonNull View v) {
            super(v);
            linearlayoutredsocial = v.findViewById(R.id.linearlayoutredsocial);
            txtNameNetwork = v.findViewById(R.id.txt_name_network);
            txtNetworkContact = v.findViewById(R.id.txt_network_contact);
            imageViewNetwork = v.findViewById(R.id.imageViewNetwork);

        }
    }

    @NonNull
    @Override
    public AdpterSocialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        AdpterSocialHolder caracterisitcasHolder = new AdpterSocialHolder(view);
        return caracterisitcasHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterSocialHolder holder, int position) {
        CaracteristicasSocial obtener = caracteristicasSocials.get(position);
        holder.txtNameNetwork.setText(obtener.nameSocialNetwork);
        holder.txtNetworkContact.setText(obtener.contactSocialNetwork);
        holder.imageViewNetwork.setImageResource(obtener.image);

        holder.imageViewNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navegateToUrl(obtener.url);
            }
        });

        holder.txtNetworkContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navegateToUrl(obtener.url);
            }
        });

    }

    public void navegateToUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return caracteristicasSocials.size();
    }


}
