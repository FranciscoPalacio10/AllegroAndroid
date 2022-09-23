package com.example.allegroandroid.ui.activityprofesores.fragmentregistrarprofesores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allegroandroid.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class registrarProfesora extends Fragment {
    private View v;
    private TableLayout tablaProfesoras;
    private EditText nombreProfe,apellidoProfe,edadProfe;
    private Button btnSubirProfe,btnVerProfe;
    private String id,profesora;
    profesora obtener;
    private  String nombre,apellido,edad;
    private DatabaseReference Database;
    private RecyclerView recyclerView;
    private TableRow TableRow;
    private Context getContext;
    private EditText nombreProfesora;
    private TextView nombreProseroaparaTabla;
    private  TableRow tbProfe;
    private   int posicion;
    private Myadapter myadapter;
    private ArrayList<profesora> profesoraArrayList= new ArrayList<>();
    private ArrayList<profesora> profesoraArrayList1= new ArrayList<>();
    private  int positon=0;
    private boolean tocado=false;
   private static Map<String, Object> conseguir;





    // Always call the superclass so it can save the view hierarchy state




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
              getChildFragmentManager();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            v=inflater.inflate(R.layout.fragment_registrar_profesora, container, false);
        getContext= getContext();
           //recylcer
            recyclerView=v.findViewById(R.id.recycler);
            //
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        btnVerProfe=v.findViewById(R.id.btnvProfesora);
        nombreProfe=v.findViewById(R.id.txtNombre);
        apellidoProfe=v.findViewById(R.id.txtApelldio);
        btnSubirProfe=v.findViewById(R.id.btnSubirProfesora);
        Database= FirebaseDatabase.getInstance().getReference();


        btnVerProfe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerTodasProfesoras();
                btnVerProfe.setEnabled(false);
                profesoraArrayList.clear();
                profesoraArrayList1.clear();
            }
        });
        btnSubirProfe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirProfesora();
                obtnerProfesoraAñadidaDataBase();
                btnVerProfe.setEnabled(true);
                profesoraArrayList.clear();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void obtenerTodasProfesoras(){
        Database.child("Profesora").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    id = dataSnapshot1.child("Id").getValue().toString();
                    profesora = dataSnapshot1.child("Nombre").getValue().toString();
                    profesoraArrayList.add(new profesora(profesora, id, getContext));
                    myadapter = new Myadapter(profesoraArrayList, R.layout.viewprofesora);
                    recyclerView.setAdapter(myadapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void obtnerProfesoraAñadidaDataBase(){
            Database.child("Profesora").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {
                        id = dataSnapshot.child("Id").getValue().toString();
                       profesora = dataSnapshot.child("Nombre").getValue().toString();
                       adaptadorProfesora();
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }


    private void adaptadorProfesora() {
        profesoraArrayList1.clear();
        profesoraArrayList1.add(new profesora(profesora, id, getContext));
        myadapter = new Myadapter(profesoraArrayList1, R.layout.viewprofesora);
        recyclerView.setAdapter(myadapter);

    }


    // private View.OnClickListener tablerowOnClickListener = new View.OnClickListener() {
       /*
        public void onClick(View v) {
            for (int i = 0; i < tablaProfesoras.getChildCount(); i++)
            {
                View row = tablaProfesoras.getChildAt(i);
                String nombres = tablaProfesoras.getChildAt(i).toString();
                if (row == v)
                {
                    AlertDialog.Builder mibuilder = new AlertDialog.Builder(getActivity());
                    View nuevo = getLayoutInflater().inflate(R.layout.editarprofesora,null);
                    EditText nombre=nuevo.findViewById(R.id.editarProfesora);
                    mibuilder.setView(nuevo)
                            .setTitle("Cambiar Nombre Profesora")
                            .setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    nombre.setText(nombres);
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = mibuilder.create();
                    dialog.show();
                }

                else
                {
                    //Change this to your normal background color.
                    row.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
            }
        }

        */


    private void subirProfesora(){
        nombre= nombreProfe.getText().toString().toUpperCase();
         apellido= apellidoProfe.getText().toString().toUpperCase();

        if(!nombre.isEmpty()){
            if(!apellido.isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Quieres registrar la profesora?")
                        .setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                subiraDataBase();
                                limpiarCampos();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }  else{
                    Toast.makeText(getContext(),"Debe ingresar Apellido",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getContext(),"Debe ingresar Nombre",Toast.LENGTH_LONG).show();
        }





    }

    private void limpiarCampos() {
        nombreProfe.setText("");
        nombreProfe.setHint(getResources().getString(R.string.txtNombre));
        apellidoProfe.setText("");
        apellidoProfe.clearFocus();
        apellidoProfe.setHint(getResources().getString(R.string.txtApellido));
        Toast.makeText(getContext(),"Se registro con éxito",Toast.LENGTH_LONG).show();
    }



    private void subiraDataBase() {
       conseguir=new HashMap<>();
        String nombreCompleto=nombreProfe.getText().toString().concat(" ").concat(apellidoProfe.getText().toString());
        String key= Database.child("Profesora").push().getKey();
            obtener= new profesora(nombreCompleto,key,getContext);
         conseguir.put("Id",obtener.getId());
         conseguir.put("Nombre",obtener.getNombre().toUpperCase());
         Database.child("Profesora").child(key).setValue(conseguir);


                }


    @Override
    public void onPause() {
        super.onPause();
        profesoraArrayList1.clear();
        profesoraArrayList.clear();
    }

    @Override
    public void onResume() {
        super.onResume();


        }

    }


