package com.example.allegroandroid.ui.core.clases.historialdeclases;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.allegroandroid.R;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.repository.resource.Resource;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HistorialDeClasesPagerAdapter extends FragmentStatePagerAdapter {

    private static final int[] TAB_TITLES = new int[]{R.string.clasesencurso, R.string.clasesfinalziada};
    private final Context mContetx;
    ArrayList<HistorialDeClaseResponse>arrayListResource;

    public HistorialDeClasesPagerAdapter(@NonNull FragmentManager fm, Context context, ArrayList<HistorialDeClaseResponse> arrayListResource) {
        super(fm);
        mContetx = context;
        this.arrayListResource = arrayListResource;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case  0:
                ClasesNoFinalizadasFragment clasesEnCurso = new ClasesNoFinalizadasFragment(arrayListResource.stream()
                        .filter(x -> !x.isFinished)
                        .collect(Collectors.toCollection(ArrayList::new)));
                return clasesEnCurso;
            case 1:
                ClasesFinalizadasFragment claseFinalizada = new ClasesFinalizadasFragment(arrayListResource.stream()
                        .filter(x -> x.isFinished)
                        .collect(Collectors.toCollection(ArrayList::new)));
                return claseFinalizada;
                  }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContetx.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}

