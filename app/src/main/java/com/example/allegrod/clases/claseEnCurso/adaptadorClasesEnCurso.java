package com.example.allegrod.clases.claseEnCurso;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegrod.R;
import com.example.allegrod.clases.claseFinalizada.adaptadorClasesFinalizadas;
import com.example.allegrod.clases.clasesEnCursoLista;
import com.example.allegrod.clases.clasesRegistradasLista;
import com.example.allegrod.reprodcutor.reproductorClase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.SortedMap;

public class adaptadorClasesEnCurso extends  RecyclerView.Adapter<adaptadorClasesEnCurso.adaptadorClases>{
    private ArrayList<clasesEnCursoLista> clasesRegistradasListaArrayList;
    private Context context;
    private int resource;
    public adaptadorClasesEnCurso(ArrayList<clasesEnCursoLista> clasesRegistradasListas, int resource, Context context) {
        this.clasesRegistradasListaArrayList=clasesRegistradasListas;
        this.context=context;
        this.resource=resource;
    }
    class  adaptadorClases extends RecyclerView.ViewHolder{
        TextView txtEstilo, txtNivel, txtProfesora, txtNroClase,txtFecha,txtTiempo;
        Button btnContinuarClase;
        ImageButton btndescargar;
        public adaptadorClases(@NonNull View v) {
            super(v);
            txtEstilo=v.findViewById(R.id.txtEstiloEnCurso);
            txtNivel=v.findViewById(R.id.txtNivelEnCurso);
            txtProfesora=v.findViewById(R.id.txtProfesoraEnCurso);
            txtNroClase=v.findViewById(R.id.txtNroClaseEnCurso);
            txtFecha=v.findViewById(R.id.txtFechaEnCurso);
            txtTiempo=v.findViewById(R.id.txtTiempoEnCurso);
            btnContinuarClase=v.findViewById(R.id.btnContinuarClase);
            btndescargar=v.findViewById(R.id.btnDescargarClase);
        }
    }
    @NonNull
    @Override
    public adaptadorClases onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        adaptadorClasesEnCurso.adaptadorClases adaptadorClasesC = new  adaptadorClasesEnCurso.adaptadorClases(view);
        return adaptadorClasesC;
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorClases holder, int position) {
        clasesEnCursoLista obtener= clasesRegistradasListaArrayList.get(position);
        StorageReference storageReference;
        storageReference=FirebaseStorage.getInstance().getReferenceFromUrl(obtener.getUri());


        holder.txtEstilo.setText(obtener.getEstilo());
        holder.txtNivel.setText(obtener.getNivel());
        holder.txtProfesora.setText(obtener.getProfesora());
        holder.txtNroClase.setText(obtener.getNumeroClase());
        holder.txtFecha.setText(obtener.getFecha());
        long Tiempo = Long.parseLong(obtener.getMinuto());
        String date= new SimpleDateFormat("mm:ss:SS").format(Tiempo);
        holder.txtTiempo.setText(date);
        String titulo= obtener.getEstilo().concat(" ").concat(obtener.getNivel()).concat(" ").concat(obtener.getNumeroClase());
        Bundle bundle= getBundle(obtener);
        holder.btnContinuarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_clases_to_reproductorClase,bundle);
            }
        });
        holder.btndescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("¿Quiere descargar la clase "+titulo+" ?");
                builder.setMessage("En la seccion DOWNLOAD podras ver el video");
                builder.setPositiveButton("Descargar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"Descarga en segundo plano...",Toast.LENGTH_SHORT).show();
                        descargarClase(obtener, titulo);
                    }
                }).setNegativeButton("Vovler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
});


    }

    private void descargarClase(clasesEnCursoLista obtener, String titulo) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(obtener.getUri()));
        request.setDescription("Descargando...");
        request.setTitle(""+titulo);

// in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+titulo+".mp4");

// get download service and enqueue file
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @NotNull
    private Bundle getBundle(clasesEnCursoLista clasesRegistradasLista) {
        Bundle bundle= new Bundle();
        bundle.putString("url",clasesRegistradasLista.getUri());
        bundle.putString("estilo",clasesRegistradasLista.getEstilo());
        bundle.putString("nivel",clasesRegistradasLista.getNivel());
        bundle.putString("nroClase",clasesRegistradasLista.getNumeroClase());
        bundle.putString("profesora",clasesRegistradasLista.getProfesora());
        bundle.putLong("tiempo",Long.valueOf(clasesRegistradasLista.getMinuto()));
        return bundle;
    }
    @Override
    public int getItemCount() {
        return clasesRegistradasListaArrayList.size();
    }




}
