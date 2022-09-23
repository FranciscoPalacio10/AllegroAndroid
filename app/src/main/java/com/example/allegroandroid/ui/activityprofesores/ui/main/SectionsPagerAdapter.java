package com.example.allegroandroid.ui.activityprofesores.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.allegroandroid.R;
import com.example.allegroandroid.ui.activityprofesores.fragmentregistrarclase.registrarClaseFragment;
import com.example.allegroandroid.ui.activityprofesores.fragmentregistrarprofesores.registrarProfesora;
import com.example.allegroandroid.ui.activityprofesores.fragmeteditarclases.editarClases;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;
    FragmentManager fm;
    FragmentTransaction fg;
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case  0:
                registrarProfesora registrarProfesora = new registrarProfesora();

                return registrarProfesora;
            case 1:
                registrarClaseFragment registrarClaseFragment= new registrarClaseFragment();
                return registrarClaseFragment;
            case 2:
                editarClases editarClases= new editarClases();
                return editarClases;

        }


        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
           // Show 2 total pages.
           return 3;
           }
           }


