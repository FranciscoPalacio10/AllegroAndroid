package com.example.allegrobackend.ui.activityprofesores.fragmentregistrarclase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.allegrobackend.R;
import com.example.allegrobackend.ui.activityprofesores.metodos;
import com.example.allegrobackend.services.DateService;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rutaVideo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class registrarClaseFragment extends Fragment {
        View v;
    private StorageReference clasesRef;
    private  String urlVideoSubir;
    private TextView nombre;
    private final int VALOR_RETORNO = 1;
    private Uri uriVideoClase;
    private Spinner tipoDanza,nro_clase;
    private Spinner nivelDanza;
    public   Spinner spinnerprofesora;
    private DatabaseReference Database;
    private TextView subidoexitoso, txtPorcentaje;
    private ProgressBar cargando;
    private int i=0;
    metodos m = new metodos();
    private Button btnSubirVideo;
    private String filePath,rutaArchivo;
    private VideoView video;
   private Boolean tocado= false;
   private ImageView playVideo, pauseVideo;
    private FirebaseFirestore firestore;
    private RadioButton btnBasedeDatos, btnYoutbe;
    private boolean seleccionado=false;
    private boolean cliqueado=false;
    private long total,cargado;
    private ImageButton btnPausa, btnStart, btnCancelar;
    private  UploadTask uploadTask;
    public registrarClaseFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_registrar_clases, container, false);
        //constructor metodos
        //textView Subio con exito
        tvSubidoExitoso();
        //Base de datos
        Database= FirebaseDatabase.getInstance().getReference();
        clasesRef = FirebaseStorage.getInstance().getReference();
        llenarSpinner();



        //traer archivos computadora
        btnBasedeDatos=v.findViewById(R.id.btnSubirVideo);
        obtenerDatos();
        direccionArchivo();

        //boton Subir video
        btnSubirVideo= v.findViewById(R.id.btnSubirProfesora);
        btnSubirVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirVideo();
            }
        });
        firestore= FirebaseFirestore.getInstance();
        //progresbarr
        cargando= v.findViewById(R.id.progressBar2);
        cargando.setVisibility(View.INVISIBLE);

         //imagen pausa y reaunado
        btnPausa= v.findViewById(R.id.btnPause);
        txtPorcentaje= v.findViewById(R.id.txtPorcentaje);
        txtPorcentaje.setVisibility(View.GONE);
        btnPausa.setVisibility(View.GONE);
        btnCancelar= v.findViewById(R.id.btnCancelar);
        btnCancelar.setVisibility(View.GONE);
        //video
        video= v.findViewById(R.id.video);
        playVideo= v.findViewById(R.id.imgPlay);
        pauseVideo= v.findViewById(R.id.imgPause);

        pauseVideo.setVisibility(View.GONE);
        return v;
    }

    private void direccionArchivo() {
        nombre= v.findViewById(R.id.archivo);
        nombre.setVisibility(View.GONE);

    }

    private void tvSubidoExitoso() {
        subidoexitoso= v.findViewById(R.id.txtSubidoExitoso);
        subidoexitoso.setVisibility(View.GONE);
    }

    private void llenarSpinner() {
        //sipner tipo
        tipoDanza= v.findViewById(R.id.tipodedanza);
        m.llenarSpinnerTipoDeDanza(tipoDanza,getContext());
        //declaracion y llenado spiner nivel
        nivelDanza= v.findViewById(R.id.niveldedanza);
        m.llenarSpinnerNivelDanza(tipoDanza,nivelDanza,getContext(),subidoexitoso);
        //declaracion y llenado nroclase
        nro_clase= v.findViewById(R.id.nro_clase);
        m.setNroClase(nivelDanza,nro_clase,getContext());
        //obtener Profesora
        spinnerprofesora= v.findViewById(R.id.s_Profesora);
        m.llenarProfesora(Database,spinnerprofesora,getContext());
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e("estado", "pausa");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("estado", "start");
       }



    //obtener videos desde celular

    private void obtenerDatos(){
              btnBasedeDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Elgir video"), VALOR_RETORNO);
                           }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case VALOR_RETORNO:
                if(resultCode == RESULT_OK){
                    nombre.setVisibility(View.VISIBLE);
                    filePath= data.getData().getPath();
                    uriVideoClase= data.getData(); //obtener el uri content
                    nombre.setText(filePath);
                    videoManejo(uriVideoClase,video);
                }
                break;
        }

    }

    private void videoManejo(Uri uriVideoClase, final VideoView video){
        playVideo.setVisibility(View.GONE);
        video.setVideoURI(uriVideoClase);
        video.start();

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tocado){
                    video.start();
                    tocado=true;
                    playVideo.setVisibility(View.GONE);
                }else{
                   video.pause();
                   tocado=false;
                   playVideo.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    private void subirVideo(){

        Date d = new Date(); CharSequence s = DateFormat.format("MMMM d, yyyy ", d.getTime());
                    btnSubirVideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(tipoDeDanzaisSelected()==true){
                                if(nivelDeDanzaisSelected()==true){
                                    if(profesoraisSelect()==true){
                                        if(uriVideoClase==null){
                                            Toast.makeText(getContext(),"Debe seleccionar un archivo", Toast.LENGTH_SHORT).show();
                                    }else{
                                            verifcarExistenciadeClase();
                                        }
                                        }else{

                                    }
                            }else{
                                    Toast.makeText(getContext(),"Debe seleccionar un nivel", Toast.LENGTH_SHORT).show();
                            }
                            } else{
                                Toast.makeText(getContext(),"Debe seleccionar un estilo", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



           }

    private void limpiarSubida() {
        btnBasedeDatos.setSelected(false);
        filePath="";
        uriVideoClase=null;
    }



    private void subirVideoStorage() {
        rutaVideo rutaVideo = new rutaVideo(tipoDanza,nivelDanza,nro_clase,spinnerprofesora);
        rutaArchivo= rutaVideo.getRutaArchivo();
        StorageReference riversRef = clasesRef.child("Clases/"+rutaArchivo+"."+"mp4");
       uploadTask = clasesRef.child("Clases/"+rutaArchivo+"."+"mp4").putFile(uriVideoClase);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Quieres cancelar la descarga?")
                        .setPositiveButton("Suspender Subida", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                uploadTask.cancel();
                            }
                        })
                        .setNegativeButton("Continuar Subida", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

         uploadTask
                 .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                         pausarReanudarDescarga(uploadTask);
                         cargando.setVisibility(View.VISIBLE);
                         btnCancelar.setVisibility(View.VISIBLE);
                         txtPorcentaje.setVisibility(View.VISIBLE);
                         btnPausa.setVisibility(View.VISIBLE);
                         total=   taskSnapshot.getTotalByteCount();
                         cargado= taskSnapshot.getBytesTransferred();
                         double   i = (100.0 * taskSnapshot.getBytesTransferred())/ taskSnapshot.getTotalByteCount();
                         txtPorcentaje.setText(String.format("%.2f", i)+" %");

                         cargando.setMax((int) total);
                         cargando.setProgress((int) cargado);

                                       }
                 })
                 .addOnCanceledListener(new OnCanceledListener() {
                     @Override
                     public void onCanceled() {
                         ocultarPanelSubida();
                         limpiarCampos();
                         limpiarSubida();
                         Toast.makeText(getContext(),"Se cancelo la subida del video",Toast.LENGTH_LONG).show();
                     }
                 })

                .addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(),"Subida Pausada", Toast.LENGTH_SHORT).show();
                        cargado= taskSnapshot.getBytesTransferred();
                        cargando.setProgress((int) cargado);
                        double   i = (100.0 * taskSnapshot.getBytesTransferred())/ taskSnapshot.getTotalByteCount();
                        txtPorcentaje.setText(String.format("%.2f", i)+" %");
                    }
                })

    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        DateService obtener= DateService.getInstance();
                        ocultarPanelSubida();
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urlVideoSubir=uri.toString();
                                Toast.makeText(getContext(),urlVideoSubir, Toast.LENGTH_LONG).show();
                                registrarEnDataBase(obtener.getFechaSistema(),urlVideoSubir);
                                Toast.makeText(getContext(), "Clase Guardada", Toast.LENGTH_LONG).show();
                                subidoexitoso.setText("Se registro la clase con éxito");
                                subidoexitoso.setVisibility(View.VISIBLE);
                                limpiarCampos();
                                limpiarSubida();
                            }
                        });
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        cargando.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "No se pudo registrar la clase", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void pausarReanudarDescarga(final UploadTask uploadTask) {
        btnPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cliqueado){
                    uploadTask.pause();
                    btnPausa.setImageResource(R.drawable.exo_controls_play);
                    Toast.makeText(getContext(),"Subida Pasuada",Toast.LENGTH_LONG).show();
                    cliqueado=true;
                }else {
                    uploadTask.resume();
                    btnPausa.setImageResource(R.drawable.exo_icon_pause);
                    Toast.makeText(getContext(),"Subida Reanudada",Toast.LENGTH_LONG).show();
                    cliqueado=false;
                }
            }

        });
    }

    private void ocultarPanelSubida() {
        cargando.setVisibility(View.GONE);
        btnCancelar.setVisibility(View.GONE);
        txtPorcentaje.setVisibility(View.GONE);
        btnPausa.setVisibility(View.GONE);
    }

    private void registrarEnDataBase(String d, String Url) {
        String tipoDnaza=tipoDanza.getSelectedItem().toString();
        String nivel=nivelDanza.getSelectedItem().toString();
        String nrodeClase=nro_clase.getSelectedItem().toString();
        Map<String,Object> map = new HashMap<>();
        map.put("nro clase",nrodeClase);
        map.put("id clase:",rutaArchivo);
        map.put("profesora:",spinnerprofesora.getSelectedItem().toString());
        map.put("link-uri",Url);
        map.put("link-youtbe","");
        map.put("Fecha publicación: ",d);
        Database.child("Clases registradas").child(tipoDnaza).child(nivel).child(nrodeClase).setValue(map);
        Database.onDisconnect();
    }

    private void limpiarCampos(){
        nombre.setText("ruta del archivo");
        m.llenarSpinnerTipoDeDanza(tipoDanza,getContext());
        m.llenarSpinnerNivelDanza(tipoDanza,nivelDanza,getContext(),subidoexitoso);
        m.setNroClase(nivelDanza,nro_clase,getContext());
        video.suspend();


}



    private boolean tipoDeDanzaisSelected(){
        if(tipoDanza.getSelectedItemPosition()==0){
            seleccionado=false;
            Toast.makeText(getContext(),"Debe seleccionar tipo de Danza", Toast.LENGTH_SHORT).show();

        }else{
            seleccionado=true;
        }



        return seleccionado;
    }
    private  boolean nivelDeDanzaisSelected(){
        if(nivelDanza.getSelectedItemPosition()==0){
            seleccionado=false;
            Toast.makeText(getContext(),"Debe seleccionar nivel de Danza", Toast.LENGTH_SHORT).show();

        }else{
            seleccionado=true;
        }

        return seleccionado;
    }
    private  boolean profesoraisSelect(){

        if(spinnerprofesora.getItemAtPosition(0)==null || spinnerprofesora.getAdapter().isEmpty() )
        {
                    Toast.makeText(getContext(),"Debe registrar Profesora", Toast.LENGTH_SHORT).show();
                    seleccionado=false;

                }else{
                    seleccionado=true;
                }
        return seleccionado;
    }

    private void verifcarExistenciadeClase(){
        String estiloSeleccionado=tipoDanza.getSelectedItem().toString();
        String  nivelSeleccionado=nivelDanza.getSelectedItem().toString();
        String nroClase=nro_clase.getSelectedItem().toString();
        Database.child("Clases registradas").child(estiloSeleccionado).child(nivelSeleccionado).child(nroClase).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(getContext(),"Esta clase ya se encuentra registrada",Toast.LENGTH_LONG).show();
                        } else{
                            subirVideoStorage();
        }
            }

                @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



        }


        });
        }
    }









