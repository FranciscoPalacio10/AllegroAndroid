package com.example.allegroandroid.ui.core.clases.historialdeclases;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequestGet;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.models.user.UserRequest;
import com.example.allegroandroid.models.user.get.UserResponse;
import com.example.allegroandroid.repository.HistorialDeClaseRepository;
import com.example.allegroandroid.repository.UserRepository;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.resource.Status;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.example.allegroandroid.services.FireBaseLoginService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.ui.MyViewModelFactory;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.example.allegroandroid.viewmodel.HistorialDeClasesViewModel;
import com.example.allegroandroid.viewmodel.UserViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class HistorialDeClasesFragment extends Fragment {
    private static final String TAG = "HistorialDeClasesFragment.class";
    HistorialDeClasesViewModel historialDeClasesViewModel;
    UserViewModel userViewModel;
    AppExecutors appExecutors;
    AppModule appModule;
    ProgressDialog progress;
    private FireBaseLoginService fireBaseLoginService;

    public HistorialDeClasesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        initAdapter(getView());
        super.onResume();
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
        appExecutors = new AppExecutors();
        appModule = new AppModule(getContext());

        historialDeClasesViewModel = ViewModelProviders.of(this, new MyViewModelFactory<>(getActivity().getApplication(),
                new HistorialDeClaseRepository(appExecutors, appModule.provideHistorialDeClaseRetrofit(), appModule.provideDb(),
                        new SessionService(getContext())
                )))
                .get(HistorialDeClasesViewModel.class);

        userViewModel = ViewModelProviders.of(this, new MyViewModelFactory<>(getActivity().getApplication(),
                new UserRepository(appExecutors, appModule.provideUserRetrofit(), appModule.provideDb(),
                        new SessionService(getContext())
                )))
                .get(UserViewModel.class);

        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), getActivity());
        progress = new ProgressDialog(getContext());
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_historial_de_clases, container, false);

        initAdapter(v);
        ;

        return v;
    }

    private void initPagerAdapter(View v, ArrayList<HistorialDeClaseResponse> arrayListResource) {
        HistorialDeClasesPagerAdapter sectionsPagerAdapter = new HistorialDeClasesPagerAdapter(getChildFragmentManager(), getContext(), arrayListResource);
        ViewPager viewPager = v.findViewById(R.id.view_pager3);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setSaveEnabled(false);
        TabLayout tabs = v.findViewById(R.id.tabs1);
        tabs.setupWithViewPager(viewPager);
        tabs.setSelectedTabIndicatorColor(Color.MAGENTA);
    }

    private void initAdapter(View v) {
        try {
            userViewModel.setUserRequest(new UserRequest(fireBaseLoginService.getCurrentUser().getEmail()));
            userViewModel.getUser().observe(getActivity(), new Observer<Resource<UserResponse>>() {
                @Override
                public void onChanged(Resource<UserResponse> userResponseResource) {
                    if(!progress.isShowing()){
                        progress.show();
                    }
                    if (userResponseResource.status == Status.ERROR) {
                        ActivitiesInitiator.initErrorActivity(new InitActitvy(getContext(), getBundle(userResponseResource.message)));
                    } else if (userResponseResource.status == Status.SUCCESS) {
                        loadHistorialDeClases(userResponseResource, v);
                        progress.dismiss();
                    } else {
                        progress.setTitle("Loading");
                        progress.setMessage("Espera unos segundos...");
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();
                    }
                }
            });

        } catch (Exception e) {
            ActivitiesInitiator.initErrorActivity(new InitActitvy(getContext(), null));
        }
    }

    private void loadHistorialDeClases(Resource<UserResponse> userResponseResource, View v) {
        historialDeClasesViewModel.setHistorialClaseRequestGet(new HistorialDeClaseRequestGet(userResponseResource.data.user, 14, 1));
        historialDeClasesViewModel.getHistorialDeClases().observe(getActivity(), new Observer<Resource<ArrayList<HistorialDeClaseResponse>>>() {
            @Override
            public void onChanged(Resource<ArrayList<HistorialDeClaseResponse>> arrayListResource) {
                if(!progress.isShowing()){
                    progress.show();
                }
                if (arrayListResource.status == Status.ERROR) {
                    ActivitiesInitiator.initErrorActivity(new InitActitvy(getContext(), getBundle(arrayListResource.message)));
                } else if (arrayListResource.status == Status.SUCCESS) {
                    initPagerAdapter(v, arrayListResource.data);
                    progress.dismiss();
                } else {
                    progress.setTitle("Loading");
                    progress.setMessage("Espera unos segundos...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                }
            }
        });
    }


    @Override
    public void onStart() {
        initAdapter(getView());
        super.onStart();
    }

    @NonNull
    private Bundle getBundle(String t) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t);
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR, this.TAG);
        return pBundle;
    }


}

