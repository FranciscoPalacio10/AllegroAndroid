package com.example.allegroandroid.ui.core.caractersticas.points;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.models.desafios.DesafioRequest;
import com.example.allegroandroid.models.desafios.DesafiosXUserResponse;
import com.example.allegroandroid.models.pointsxuser.PointXUserResponse;
import com.example.allegroandroid.repository.DesafiosRepository;
import com.example.allegroandroid.repository.PointXUserRepository;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.resource.Status;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.example.allegroandroid.services.FireBaseLoginService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.ui.MyViewModelFactory;
import com.example.allegroandroid.ui.core.adapters.AdapterDesafios;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.example.allegroandroid.viewmodel.DesafiosViewModel;
import com.example.allegroandroid.viewmodel.PointXUserViewModel;


import java.util.ArrayList;


public class TotalPointsFragment extends Fragment {

    private static final String TAG = "TotalPointsFragment.class";
    private FireBaseLoginService fireBaseLoginService;
    private PointXUserViewModel pointXUserViewModel;
    private DesafiosViewModel desafiosViewModel;
    private AppExecutors appExecutors;
    private AppModule appModule;
    private ProgressDialog progress;
    private TextView txtPoints;
    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appExecutors = new AppExecutors();
        appModule = new AppModule(getContext());

        pointXUserViewModel = ViewModelProviders.of(this, new MyViewModelFactory<>(getActivity().getApplication(),
                new PointXUserRepository(appExecutors, appModule.providePointsXUserRetrofit(), appModule.provideDb(),
                        new SessionService(getContext())
                )))
                .get(PointXUserViewModel.class);

        desafiosViewModel= ViewModelProviders.of(this, new MyViewModelFactory<>(getActivity().getApplication(),
                new DesafiosRepository(appExecutors, appModule.provideDesafiosRetrofit(),  new SessionService(getContext())
                ))).get(DesafiosViewModel.class);



        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), getActivity());
        progress = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_total_points, container, false);

        recyclerView = v.findViewById(R.id.reciclerFragmentTotalPoints);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        txtPoints = v.findViewById(R.id.txt_points_fragment_total_puntos);

        String emailUser = fireBaseLoginService.getCurrentUser().getEmail();
        pointXUserViewModel.getPointXUser(emailUser).observe(getActivity(), new Observer<Resource<ArrayList<PointXUserResponse>>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(Resource<ArrayList<PointXUserResponse>> listResource) {
                if (listResource.status == Status.ERROR) {
                    ActivitiesInitiator.initErrorActivity(new InitActitvy(getContext(), getBundle(listResource.message)));
                } else if (listResource.status == Status.SUCCESS) {
                    Integer totalPoints = listResource.data.stream().mapToInt(i -> i.points).sum();
                    txtPoints.setText(totalPoints.toString() + " PUNTOS");
                    progress.dismiss();
                } else {
                    progress.setTitle("Loading");
                    progress.setMessage("Espera unos segundos...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                }
            }
        });

        desafiosViewModel.setDesafioRequest(new DesafioRequest(emailUser));

        desafiosViewModel.getDesafiosXUser().observe(getActivity(), new Observer<Resource<ArrayList<DesafiosXUserResponse>>>() {
            @Override
            public void onChanged(Resource<ArrayList<DesafiosXUserResponse>> listResource) {
                if (listResource.status == Status.ERROR) {
                    ActivitiesInitiator.initErrorActivity(new InitActitvy(getContext(), getBundle(listResource.message)));
                } else if (listResource.status == Status.SUCCESS) {
                    AdapterDesafios adapterDesafios = new AdapterDesafios(listResource.data, R.layout.adaptadordesafios, getContext(), txtPoints);
                    recyclerView.setAdapter(adapterDesafios);
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

    @NonNull
    private Bundle getBundle(String t) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t);
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR, this.TAG);
        return pBundle;
    }
}