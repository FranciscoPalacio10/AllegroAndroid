package com.example.allegrobackend.clases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.allegrobackend.R;
import com.example.allegrobackend.clases.claseEnCurso.clasesEnCurso;
import com.example.allegrobackend.clases.claseFinalizada.claseFinalizada;

public class ClasesPagerAdapter extends FragmentStatePagerAdapter {

    private static final int[] TAB_TITLES = new int[]{R.string.clasesencurso, R.string.clasesfinalziada};
    private final Context mContetx;

    public ClasesPagerAdapter(@NonNull FragmentManager fm,Context context) {
        super(fm);
        mContetx = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case  0:
                clasesEnCurso clasesEnCurso = new clasesEnCurso();
                return clasesEnCurso;
            case 1:
                claseFinalizada claseFinalizada = new claseFinalizada();
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

