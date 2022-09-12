package com.example.allegrobackend.ui.activityprofesores.fragmentregistrarprofesores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allegrobackend.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Myadapter extends RecyclerView.Adapter<Myadapter.editarProfesoraViewHolder>{
    private DatabaseReference Database;
    private ArrayList<profesora> profesoraList;
    private int resource;
    private int i;


    public Myadapter(ArrayList<profesora> profesoras,int resoucr){
        profesoraList=profesoras;
        resource=resoucr;

    }

    class editarProfesoraViewHolder extends RecyclerView.ViewHolder{
        TextView id;
        TextView nombreProfesora;
        public View v;
        Button btnEditar,btnElimnar;
        CardView tarjeta;
        public editarProfesoraViewHolder(@NonNull View v) {
            super(v);
            nombreProfesora=v.findViewById(R.id.txtProfesora);
            btnEditar=v.findViewById(R.id.bnEditarProfe);
            btnElimnar=v.findViewById(R.id.bntElimarProfe);
            id=v.findViewById(R.id.id);
            tarjeta=v.findViewById(R.id.trajeta);

        }
    }

    public void removeItem(int position){
            profesoraList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,profesoraList.size());
    }

    @NonNull
    @Override
    public editarProfesoraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        editarProfesoraViewHolder editarProfesoraViewHolder = new editarProfesoraViewHolder(view);
        return editarProfesoraViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final editarProfesoraViewHolder holder, final int position) {
        final profesora nombres= profesoraList.get(position);
        i=position;
        holder.nombreProfesora.setText(nombres.getNombre());
        holder.id.setText("NOMBRE PROFESORA");
        holder.btnElimnar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(nombres.getContext());
                    builder.setMessage("¿Quieres eliminar la profesora?")
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if(profesoraList.size()==0){
                                        new profesora("no hay profesoras","0",nombres.getContext());

                                    } else if (profesoraList.size()>0){
                                        removerProfesora(nombres, holder, position);
                                    }

                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
        });

    }

    private void removerProfesora(final profesora nombres, @NonNull final editarProfesoraViewHolder holder, final int position) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profesora").child(nombres.getId());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> meter= new HashMap<>();
                meter.put("Id",nombres.getId());
                meter.put("Nombre",holder.nombreProfesora.getText().toString());
                databaseReference.removeValue();
                removeItem(position);

                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
            return profesoraList.size();

    }


    @Override
    protected void finalize() throws Throwable {
            super.finalize();

    }
}
