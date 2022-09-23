package com.example.allegroandroid.ui.core;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.models.MateriasResponse;
import com.example.allegroandroid.models.user.UserRequest;
import com.example.allegroandroid.models.user.get.UserResponse;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.resource.Status;
import com.example.allegroandroid.repository.UserRepository;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.ui.MyViewModelFactory;
import com.example.allegroandroid.ui.core.adapters.AdapterMaterias;
import com.example.allegroandroid.services.FireBaseLoginService;
import com.example.allegroandroid.ui.start.AuthenticationActivity;
import com.example.allegroandroid.viewmodel.UserViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MateriasFragment extends Fragment {
    private static final String TAG = "MateriasFragment.class";
    private TextView txtSetAno;
    private RecyclerView recyclerView;
    View v;
    CollapsingToolbarLayout toolbar;
    private int contador = 0;
    private FireBaseLoginService fireBaseLoginService;
    private UserViewModel userViewModel;
    private AppExecutors appExecutors;
    private AppModule appModule;
    private ProgressDialog progress;

    public MateriasFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        onBackPressed();

        appExecutors = new AppExecutors();
        appModule = new AppModule(getContext());

        userViewModel = ViewModelProviders.of(this, new MyViewModelFactory<>(getActivity().getApplication(),
                new UserRepository(appExecutors, appModule.provideUserRetrofit(), appModule.provideDb(),
                        new SessionService(getContext())
                )))
                .get(UserViewModel.class);

        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), getActivity());
        userViewModel.setUserRequest(new UserRequest(fireBaseLoginService.getCurrentUser().getEmail()));

        progress = new ProgressDialog(getContext());

    }

    @NonNull
    private Bundle getBundle(String t) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t);
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR, this.TAG);
        return pBundle;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_materias, container, false);

        init();

        userViewModel.getUser().observe(getActivity(), new Observer<Resource<UserResponse>>() {
            @Override
            public void onChanged(Resource<UserResponse> userResponseResource) {
                if (userResponseResource.status == Status.ERROR) {
                    ActivitiesInitiator.initErrorActivity(new InitActitvy(getContext(), getBundle(userResponseResource.message)));
                } else if (userResponseResource.status == Status.SUCCESS) {
                    completeMaterias(userResponseResource.data);
                    progress.dismiss();
                } else {
                    progress.setTitle("Loading");
                    progress.setMessage("Espera unos segundos...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                }
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initComponents() {
        toolbar = v.findViewById(R.id.toolbar);
        recyclerView = v.findViewById(R.id.recycler);
        txtSetAno = v.findViewById(R.id.txt_set_ano);
    }

    private void init() {
        initComponents();
        initLayoutManager();
    }

    private void initLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void completeMaterias(UserResponse userGetResponse) {
        txtSetAno.setText(userGetResponse.cursos.getName());
        completeAdapterWithMaterias(userGetResponse.cursos.getMaterias());
    }

    private void completeAdapterWithMaterias(ArrayList<MateriasResponse> materias) {
        AdapterMaterias adapterCursos = new AdapterMaterias(materias, R.layout.adaptadorestilo, getContext());
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
                    Intent intent = new Intent(getContext(), AuthenticationActivity.class);
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
