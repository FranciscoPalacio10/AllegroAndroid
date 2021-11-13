package com.example.allegrod.caractersticas;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegrod.R;
import com.example.allegrod.home.clasesRegistradas.adaptadorClasesRegistradas;
import com.example.allegrod.ingreso.autenticacion;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class adaptadorCaractersticas extends  RecyclerView.Adapter<adaptadorCaractersticas.caracterisitcasHolder> {
    private ArrayList<caracteristicasItems> caracteristicasItemsArrayList;
    private int resource;
    private Context context;
    private GoogleSignInClient googleSignInClient;
    private FragmentActivity fragmentActivity;


    public adaptadorCaractersticas(ArrayList<caracteristicasItems> arrayList, int reosurce, Context context, GoogleSignInClient mGoogleSignInClient, FragmentActivity activity) {
        this.caracteristicasItemsArrayList=arrayList;
        this.resource=reosurce;
        this.context=context;
        this.googleSignInClient=mGoogleSignInClient;
        this.fragmentActivity=activity;
    }

    class caracterisitcasHolder extends RecyclerView.ViewHolder{
        ImageView icono;
        TextView item;
        ImageView siguiente;
        public caracterisitcasHolder(@NonNull View v) {
            super(v);
            icono=v.findViewById(R.id.imageCaracteristica);
            item=v.findViewById(R.id.txtItem);
        }
    }
    @NonNull
    @Override
    public caracterisitcasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
       adaptadorCaractersticas.caracterisitcasHolder caracterisitcasHolder= new  adaptadorCaractersticas.caracterisitcasHolder(view);
        return caracterisitcasHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull caracterisitcasHolder holder, int position) {
        caracteristicasItems obtener= caracteristicasItemsArrayList.get(position);
        holder.icono.setImageResource(obtener.getImagen());
        holder.item.setText(obtener.getNombre());
        mostrarOpciones(holder, obtener);
    }

    private void mostrarOpciones(@NonNull caracterisitcasHolder holder, caracteristicasItems obtener) {
        holder.icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verOpciones(obtener);
            }
        });
    }

    private void verOpciones(caracteristicasItems obtener) {
        switch(obtener.getImagen()){
            case R.drawable.cerrarsesion:
                signOut();
                break;
            case R.drawable.social:
                Toast.makeText(context,"Social",Toast.LENGTH_SHORT).show();
                break;
            case R.drawable.ic_phone_black_24dp:
                Toast.makeText(context,"Contacto",Toast.LENGTH_SHORT).show();
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
                        salir(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    }
                });
    }

    private void salir(int i) {
        Intent intent = new Intent(context, autenticacion.class);
        intent.setFlags(i);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return caracteristicasItemsArrayList.size();
    }


}
