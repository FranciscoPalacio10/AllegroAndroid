package com.example.allegrobackend.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.allegrobackend.R;
import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.models.user.get.UserResponse;
import com.example.allegrobackend.services.ActivitiesInitiator;
import com.example.allegrobackend.ui.home.adapters.AdapterCursos;
import com.example.allegrobackend.services.FireBaseLoginService;
import com.example.allegrobackend.ui.start.AutenticacionActivity;
import com.example.allegrobackend.viewmodel.UserViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;


public class MateriasFragment extends Fragment {
    private FireBaseLoginService fireBaseLoginService;
    private UserViewModel userViewModel;
    LiveData<UserResponse> userComplete;
    private RecyclerView recyclerView;
    View v;
    CollapsingToolbarLayout toolbar;
    private int contador = 0;
    private ProgressBar progressBar;

    public MateriasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        onBackPressed();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        initAdapter();
    }

    private void initComponents() {
        toolbar = v.findViewById(R.id.toolbar);
        progressBar = v.findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = v.findViewById(R.id.recycler);
    }

    private void init() {
        initComponents();
        initLayoutManager();
    }

    private void initLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initAdapter() {
        try {
            fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), getActivity());
            userViewModel = ViewModelProviders.of(this.getActivity()).get(UserViewModel.class);
            userComplete = userViewModel.getUser(fireBaseLoginService.getCurrentUser().getEmail());
            userCompleteObserve(userComplete);
        } catch (Exception e) {
            ActivitiesInitiator.initErrorActivity(new InitActitvy(getContext(), null));
        }
    }

    private void userCompleteObserve(LiveData<UserResponse> userComplete) {
        userComplete.observe(getActivity(), userGetResponse -> {
            userObserver(userGetResponse);
        });
    }

    private void userObserver(UserResponse userGetResponse) {
        completeAdapterWithMaterias(userGetResponse.cursos.getMaterias());
    }

    private void completeAdapterWithMaterias(ArrayList<com.example.allegrobackend.models.Materias> materias) {
        AdapterCursos adapterCursos = new AdapterCursos(materias, R.layout.adaptadorestilo, getContext());
        recyclerView.setAdapter(adapterCursos);
    }

    private void onBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (contador == 0) {
                    Toast.makeText(getContext(), "Presione de nuevo para salir", Toast.LENGTH_SHORT).show();
                    contador++;
                } else {
                    Intent intent = new Intent(getContext(), AutenticacionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    startActivity(intent);
                }
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        contador = 0;
                    }
                }.start();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

}
