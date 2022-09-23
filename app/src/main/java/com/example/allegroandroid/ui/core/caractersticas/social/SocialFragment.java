package com.example.allegroandroid.ui.core.caractersticas.social;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allegroandroid.R;
import com.example.allegroandroid.models.CaracteristicasSocial;
import com.example.allegroandroid.ui.core.adapters.AdapterSocial;

import java.util.ArrayList;


public class SocialFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=  inflater.inflate(R.layout.fragment_social, container, false);

        recyclerView = v.findViewById(R.id.recicler_fragment_social);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<CaracteristicasSocial> caracteristicasSocials = new ArrayList<>();

        caracteristicasSocials.add(new CaracteristicasSocial(R.drawable.logofacebook, getString(R.string.facebook),
                getString(R.string.allegroFacebook), getString(R.string.facebookUrl)));

        caracteristicasSocials.add(new CaracteristicasSocial(R.drawable.instatransaparente, getString(R.string.instagram),
                getString(R.string.allegroInstagram), getString(R.string.instagramUrl)));

        caracteristicasSocials.add(new CaracteristicasSocial(R.drawable.whatsaap, getString(R.string.whatsapp),
                getString(R.string.allegroWhatsapp), getString(R.string.whatsappUrl)));

        AdapterSocial adapterSocial= new AdapterSocial(caracteristicasSocials, getContext() ,R.layout.adaptadorcontacto);
        recyclerView.setAdapter(adapterSocial);

        return v;
    }

}
