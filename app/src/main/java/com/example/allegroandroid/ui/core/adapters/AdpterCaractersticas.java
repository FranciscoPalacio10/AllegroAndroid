package com.example.allegroandroid.ui.core.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.models.CaracteristicasItems;
import com.example.allegroandroid.ui.start.AuthenticationActivity;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.ArrayList;

public class AdpterCaractersticas extends  RecyclerView.Adapter<AdpterCaractersticas.AdpterCaractersticasHolder> {
    private ArrayList<CaracteristicasItems> caracteristicasItemsArrayList;
    private int resource;
    private Context context;
    private GoogleSignInClient googleSignInClient;
    private FragmentActivity fragmentActivity;
    private AppModule appModule;
    private final AppExecutors appExecutors;

    public AdpterCaractersticas(ArrayList<CaracteristicasItems> arrayList, int reosurce, Context context, GoogleSignInClient mGoogleSignInClient, FragmentActivity activity) {
        this.caracteristicasItemsArrayList=arrayList;
        this.resource=reosurce;
        this.context=context;
        this.googleSignInClient=mGoogleSignInClient;
        this.fragmentActivity=activity;
        appModule = new AppModule(context);
        appExecutors = new AppExecutors();
    }

    class AdpterCaractersticasHolder extends RecyclerView.ViewHolder{
        ImageView icono;
        TextView item;
        ImageView siguiente;
        public AdpterCaractersticasHolder(@NonNull View v) {
            super(v);
            icono=v.findViewById(R.id.imageCaracteristica);
            item=v.findViewById(R.id.txtItem);
        }
    }
    @NonNull
    @Override
    public AdpterCaractersticasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
       AdpterCaractersticasHolder caracterisitcasHolder= new AdpterCaractersticasHolder(view);
        return caracterisitcasHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterCaractersticasHolder holder, int position) {
        CaracteristicasItems obtener= caracteristicasItemsArrayList.get(position);
        holder.icono.setImageResource(obtener.getImagen());
        holder.item.setText(obtener.getNombre());
        mostrarOpciones(holder, obtener);
    }

    private void mostrarOpciones(@NonNull AdpterCaractersticasHolder holder, CaracteristicasItems obtener) {
        holder.icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnClickItems(obtener, v);
            }
        });
    }

    private void showOnClickItems(CaracteristicasItems obtener, View v) {
        switch(obtener.getImagen()){
            case R.drawable.cerrarsesion:
                appModule.cleanDb(appExecutors.diskIO());
                signOut();
                break;
            case R.drawable.social:
                Navigation.findNavController(v).navigate(R.id.action_configuracion_to_socialFragment);
                break;
            case R.drawable.ic_stars_black_24dp:
                Toast.makeText(context,"staff",Toast.LENGTH_SHORT).show();
                break;
            case R.drawable.ic_baseline_star_24:
                Navigation.findNavController(v).navigate(R.id.action_configuracion_to_totalPointsFragment);
                break;

    }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        googleSignInClient.signOut()
                .addOnCompleteListener(fragmentActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,"Cerro sesión con éxito", Toast.LENGTH_LONG).show();
                        deleteCache(context);
                        closeApp(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    }
                });
    }

    private void closeApp(int i) {
        Intent intent = new Intent(context, AuthenticationActivity.class);
        intent.setFlags(i);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return caracteristicasItemsArrayList.size();
    }

    public static void deleteCache(Context context) {

        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
