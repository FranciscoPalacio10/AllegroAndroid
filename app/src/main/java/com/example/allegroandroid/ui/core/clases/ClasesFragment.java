package com.example.allegroandroid.ui.core.clases;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.allegroandroid.models.user.User;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.models.clase.ClaseRequest;
import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.user.UserRequest;
import com.example.allegroandroid.models.user.get.UserResponse;
import com.example.allegroandroid.repository.ClaseRepository;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.resource.Status;
import com.example.allegroandroid.repository.UserRepository;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.example.allegroandroid.services.FireBaseLoginService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.ui.MyViewModelFactory;
import com.example.allegroandroid.ui.core.adapters.AdapterClases;
import com.example.allegroandroid.viewmodel.ClasesViewModelNew;
import com.example.allegroandroid.viewmodel.UserViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ClasesFragment extends Fragment {
    private static final String TAG = "ClasesFragment.class";
    private String tipoDeClase;
    private Integer idMateria;
    ClasesViewModelNew clasesViewModelNew;
    AppExecutors appExecutors;
    AppModule appModule;
    ProgressDialog progress;
    private FireBaseLoginService fireBaseLoginService;
    private RecyclerView recyclerView;
    private UserViewModel userViewModel;
    private TextView txtClases;

    public ClasesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tipoDeClase = getArguments().getString(AppConstant.TIPO_CLASE_SELECT);
            idMateria = getArguments().getInt(AppConstant.ID_MATERIA);
        }

        appExecutors = new AppExecutors();
        appModule = new AppModule(getContext());

        userViewModel =ViewModelProviders.of(this, new MyViewModelFactory<>(getActivity().getApplication(),
                new UserRepository(appExecutors, appModule.provideUserRetrofit(), appModule.provideDb(),
                        new SessionService(getContext())
                )))
                .get(UserViewModel.class);


        clasesViewModelNew = ViewModelProviders.of(this, new MyViewModelFactory<>(getActivity().getApplication(),
                new ClaseRepository(appExecutors, appModule.provideClaseRetrofit(), appModule.provideDb(),
                        new SessionService(getContext())
                )))
                .get(ClasesViewModelNew.class);

        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), getActivity());
        userViewModel.setUserRequest(new UserRequest(fireBaseLoginService.getCurrentUser().getEmail()));
        progress = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_clases2, container, false);
        init(v);

        return v;
    }

    @NonNull
    private Bundle getBundle(String t) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t);
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR, this.TAG);
        return pBundle;
    }

    private void init(View v) {
        initComponents(v);
        initLayoutManager();
    }

    private void initComponents(View v) {
        txtClases = v.findViewById(R.id.tv_clases);
        recyclerView = v.findViewById(R.id.recyclerClases);
    }


    private void initLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initAdapter() {
        userViewModel.getUser().observe(getActivity(), new Observer<Resource<UserResponse>>() {
            @Override
            public void onChanged(Resource<UserResponse> userResponseResource) {
                if (userResponseResource.status == Status.ERROR) {
                    ActivitiesInitiator.initErrorActivity(new InitActitvy(getContext(), getBundle(userResponseResource.message)));
                } else if (userResponseResource.status == Status.SUCCESS) {
                    loadClases(userResponseResource);
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

    private void loadClases(Resource<UserResponse> userResponseResource) {
        clasesViewModelNew.setClaseRequest(new ClaseRequest(tipoDeClase, idMateria, userResponseResource.data.user));
        clasesViewModelNew.getClasesByTypeAndMateria().observe(getActivity(), new Observer<Resource<ArrayList<ClaseResponse>>>() {
            @Override
            public void onChanged(Resource<ArrayList<ClaseResponse>> listResource) {
                if (listResource.status == Status.ERROR) {
                    ActivitiesInitiator.initErrorActivity(new InitActitvy(getContext(), getBundle(listResource.message)));
                } else if (listResource.status == Status.SUCCESS) {
                    completeAdapterWithClases(listResource.data, userResponseResource.data.user);
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

    private void completeAdapterWithClases(ArrayList<ClaseResponse> claseResponses, User user) {
        if(!claseResponses.isEmpty()){
            txtClases.setText(claseResponses.get(0).materia.getName());
        }

        AdapterClases adapterClases = new AdapterClases(claseResponses, R.layout.adaptadorclases, getContext(), user);
        recyclerView.setAdapter(adapterClases);
    }


    @Override
    public void onStart() {
        super.onStart();
        initAdapter();
    }

}