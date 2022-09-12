package com.example.allegroandroid.ui.core.adapters;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegroandroid.R;

import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.ia.posedetector.ConstantPoseToEvalute;
import com.example.allegroandroid.models.clase.ClaseResponse;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.services.DateService;


import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.Locale;

public class AdapterClasesNoFinalizadas extends RecyclerView.Adapter<AdapterClasesNoFinalizadas.AdaptadorClases> {
    private ArrayList<HistorialDeClaseResponse> historialDeClaseResponseArrayList;

    private Context context;
    private int resource;
    private DateService dateService;

    public AdapterClasesNoFinalizadas(ArrayList<HistorialDeClaseResponse> clasesRegistradasListas, Context context, int resource) {
        this.historialDeClaseResponseArrayList = clasesRegistradasListas;
        this.context = context;
        this.resource = resource;
        dateService = DateService.getInstance();
    }

    class AdaptadorClases extends RecyclerView.ViewHolder {
        TextView txtClase, txtMateria, txtProfesora, txtNroClase, txtFecha, txtTiempo;
        Button btnContinuarClase;
        ImageButton btndescargar;

        public AdaptadorClases(@NonNull View v) {
            super(v);
            txtClase = v.findViewById(R.id.txtClaseNoFinalizada);
            txtMateria = v.findViewById(R.id.txtMateriaNoFinalizada);
            //  txtProfesora = v.findViewById(R.id.txtProfesoraEnCurso);
            txtNroClase = v.findViewById(R.id.txtNroClaseEnCurso);
            txtFecha = v.findViewById(R.id.txtFechaEnCurso);
            txtTiempo = v.findViewById(R.id.txtTiempoEnCurso);
            btnContinuarClase = v.findViewById(R.id.btnContinuarClase);
            btndescargar = v.findViewById(R.id.btnDescargarClase);
        }
    }

    @NonNull
    @Override
    public AdaptadorClases onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        AdaptadorClases adaptadorClasesC = new AdaptadorClases(view);
        return adaptadorClasesC;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorClases holder, int position) {
        HistorialDeClaseResponse historialDeClaseResponse = historialDeClaseResponseArrayList.get(position);

        holder.txtClase.setText(historialDeClaseResponse.clase.name.toUpperCase(Locale.ROOT));
        holder.txtMateria.setText(historialDeClaseResponse.clase.materia.getName());
        holder.txtNroClase.setText(historialDeClaseResponse.clase.claseId.toString());
        holder.txtFecha.setText(dateService.convertStringToDateString(historialDeClaseResponse.dateModief));
        holder.txtTiempo.setText(dateService.convertSecondsToMinutes(historialDeClaseResponse.timeLeaveVideo));

        String titulo = historialDeClaseResponse.clase.name.concat(" ").concat("numero ").concat(historialDeClaseResponse.clase.claseId.toString());

        holder.btnContinuarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                if (historialDeClaseResponse.clase.tipo.toLowerCase().equals(AppConstant.VIDEO.toLowerCase())) {
                    String jsonStr = gson.toJson(historialDeClaseResponse);
                    bundle.putString(AppConstant.HISTORIAL_DE_CLASE_RESPONSE, jsonStr);
                    Navigation.findNavController(v).navigate(R.id.action_fragmentHistorialDeClase_to_youtubeReproducerActivity, bundle);
                } else {
                    bundle.putString(ConstantPoseToEvalute.POSE_SELECTED, historialDeClaseResponse.clase.name);
                    //    Navigation.findNavController(v).navigate(R.id.action_fragmentHistorialDeClase_to_youtubeReproducerActivity, bundle);
                }
            }
        });


        holder.btndescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("¿Quiere descargar la  " + titulo + " ?");
                builder.setMessage("En la seccion DOWNLOAD podras ver el video");
                builder.setPositiveButton("Descargar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Descarga en segundo plano...", Toast.LENGTH_SHORT).show();
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



    private void descargarClase(ClaseResponse clase, String titulo) {
        String yotubeBaseUrl = "https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%" + clase.url;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(yotubeBaseUrl));
        request.setDescription("Descargando...");
        request.setTitle("" + titulo);

        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Allegro/" + clase.materia.getName() + "/" + titulo + ".mp4");

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return historialDeClaseResponseArrayList.size();
    }


}
