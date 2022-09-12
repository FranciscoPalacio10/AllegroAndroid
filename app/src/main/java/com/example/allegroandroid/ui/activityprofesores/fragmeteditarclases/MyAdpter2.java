package com.example.allegroandroid.ui.activityprofesores.fragmeteditarclases;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegroandroid.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyAdpter2 extends RecyclerView.Adapter<MyAdpter2.adapterClasesRegistradas> {

        private ArrayList<clasesRegistradas> clasesRegistradasArrayList;
        private Context context;
        private int resource;
        private DatabaseReference databaseReference;
        private ProgressBar progressBar;
    public MyAdpter2(ArrayList<clasesRegistradas> clasesRegistradasArrayList, int resource, Context context){
        this.clasesRegistradasArrayList=clasesRegistradasArrayList;
        this.resource=resource;
        this.context=context;
    }

    class adapterClasesRegistradas extends RecyclerView.ViewHolder {
        TextView txtNombreProfe, txtNroClase, txtFechaPublicacion;
        Button btnElimnarClase;
        ImageButton btnInfo;
        ProgressBar progressEliminar;
        public adapterClasesRegistradas(@NonNull View v) {
            super(v);
            txtNroClase=v.findViewById(R.id.txtNroClase);
            txtFechaPublicacion=v.findViewById(R.id.txtFechaPublicacion);
            txtNombreProfe=v.findViewById(R.id.txtNonbrePrfesora);
            btnElimnarClase=v.findViewById(R.id.btnElimarClase);
            btnInfo=v.findViewById(R.id.btnInfoClase);
            progressEliminar=v.findViewById(R.id.progressEliminar);
        }
    }
// //#8A07F6 color


        @NonNull
        @Override
        public adapterClasesRegistradas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
            MyAdpter2.adapterClasesRegistradas adapterClasesRegistradas = new MyAdpter2.adapterClasesRegistradas(view);

            return adapterClasesRegistradas;

        }

        @Override
        public void onBindViewHolder(@NonNull adapterClasesRegistradas holder, int position) {
            databaseReference= FirebaseDatabase.getInstance().getReference();

            clasesRegistradas clase = clasesRegistradasArrayList.get(position);
              holder.progressEliminar.setVisibility(View.GONE);
                holder.txtFechaPublicacion.setText(clase.getFechaPublicacion());
                holder.txtNombreProfe.setText(clase.getNombreProfesora());
                holder.txtNroClase.setText(clase.getNumerodeClase());
                holder.progressEliminar.setVisibility(View.INVISIBLE);


            holder.btnElimnarClase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("¿Quieres eliminar la profesora?")
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    holder.progressEliminar.setVisibility(View.VISIBLE);
                                    eliminarinStorage(holder, clase, position);
                                    removerClaseBasedeDatos(clase, position);
                                    Toast.makeText(context,"Borrado correctamente de la base de datos",Toast.LENGTH_SHORT).show();
                                    holder.progressEliminar.setVisibility(View.INVISIBLE);
                                }

                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            });

            holder.btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirMenuEmergente(clase);

                }
            });
        }

    private void eliminarinStorage(@NonNull adapterClasesRegistradas holder, clasesRegistradas clase, int position) {


        StorageReference clasesRef= FirebaseStorage.getInstance().getReference();
        String rutaArchivo=clase.obtenerRuta();
        StorageReference riversRef = clasesRef.child("Clases/"+rutaArchivo+"."+"mp4");
        riversRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"Video borrado correctamente",Toast.LENGTH_SHORT).show();
                                            }
        });
    }

    private void removerClaseBasedeDatos(clasesRegistradas clase, int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Clases registradas").
                child(clase.getEstiloDanza()).child(clase.getNivelDanza()).child(clase.getNumerodeClase());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.removeValue();
                removerClase(position);
                   }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void removerClase(int position) {
        clasesRegistradasArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,clasesRegistradasArrayList.size());
    }

    private void abrirMenuEmergente(clasesRegistradas clase) {
        Intent i = new Intent(context, dialogoInformación.class);
        i.putExtra("uriClase",clase.getUriVideo());
        i.putExtra("idClase",clase.getIdClase());
        context.startActivity(i);
    }


    @Override
        public int getItemCount() {
            return clasesRegistradasArrayList.size();
        }



    }





