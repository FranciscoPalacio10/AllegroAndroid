package com.example.allegrod;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity {
    public static String usuario="names";
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
        visibilidadMenu();
    }

        private void visibilidadMenu() {
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()){
                    case R.id.nivelClaseSeleccionado:
                    case R.id.clasesRegistradas:
                    case R.id.choseProfile:
                        ocultarMenu();
                        break;
                    default:
                        mostrarMenu();
                        break;
                }
            }
        });

        }

        private void mostrarMenu(){
            navView.setVisibility(View.VISIBLE);
            getSupportActionBar().hide();

        }

        private void ocultarMenu(){
        navView.setVisibility(View.GONE);
            getSupportActionBar().show();
        }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this,R.id.nav_host_fragment).navigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }



    /*

     */
}

/*


        bottomNavigationView= findViewById(R.id.nav_view);
        frameLayout=findViewById(R.id.framelayout);
        setFragmet(homefragment);




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                       setFragmet(homefragment);
                       return true;
                    case R.id.clases:
                        setFragmet(clasesfragment);
                        return true;
                    case R.id.configuracion:
                        setFragmet(caracteristicasfragment);
                        return true;
                }

                return false;
            }
        });


    private void setFragmet(Fragment fragmet){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, fragmet);
                fragmentTransaction.commit();

 */


