package com.example.allegrobackend.clases;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.allegrobackend.R;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;


public class clases extends Fragment {
      private LinearLayout linearLayout;
    ArrayList<String> items;


    public clases() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigateUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View v= inflater.inflate(R.layout.fragment_clases, container, false);
        ClasesPagerAdapter sectionsPagerAdapter = new ClasesPagerAdapter (getChildFragmentManager(),getContext());
        ViewPager viewPager = v.findViewById(R.id.view_pager3);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = v.findViewById(R.id.tabs1);
        tabs.setupWithViewPager(viewPager);
        tabs.setSelectedTabIndicatorColor(Color.MAGENTA);



        return v;
    }



}

