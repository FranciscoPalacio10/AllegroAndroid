package com.example.allegroandroid.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.View;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.models.user.UserRequest;
import com.example.allegroandroid.models.user.get.UserResponse;
import com.example.allegroandroid.repository.UserRepository;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.resource.Status;
import com.example.allegroandroid.services.ActivitiesInitiator;
import com.example.allegroandroid.services.FireBaseLoginService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.ui.core.MateriasFragment;
import com.example.allegroandroid.ui.core.clases.ClasesFragment;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.example.allegroandroid.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity.class";
    public static String usuario = "names";
    private NavController navController;
    private BottomNavigationView navView;

    protected UserResponse userResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.fragmentHistorialDeClase, R.id.configuracion)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        listener();
    }

    private void listener() {
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    default:
                        break;
                }
            }
        });
    }

    private void showMenu() {
        navView.setVisibility(View.VISIBLE);
        getSupportActionBar().hide();

    }

    private void hiddenMenu() {
        navView.setVisibility(View.GONE);
        getSupportActionBar().show();
    }

    @NonNull
    private Bundle getBundle(String t) {
        Bundle pBundle = new Bundle();
        pBundle.putString(AppConstant.ERROR_MESSAGE, t);
        pBundle.putString(AppConstant.ACTIVITY_WITH_ERROR, this.TAG);
        return pBundle;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
