package com.example.allegrobackend.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import android.view.View;

import com.example.allegrobackend.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static String usuario = "names";
    private NavController navController;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.clases, R.id.configuracion)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        visibilityMenu();
    }

    private void visibilityMenu() {
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.nivelClaseSeleccionado:
                    case R.id.clasesRegistradas:
                        hiddenMenu();
                        break;
                    default:
                        showMenu();
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

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
