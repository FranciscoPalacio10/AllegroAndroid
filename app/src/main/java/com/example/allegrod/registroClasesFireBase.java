package com.example.allegrod;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registroClasesFireBase {
    private String usuario,estilo,nivel,profesora,uri,minuto,window;
    private String estado,nroClase;
    private DatabaseReference firebaseDatabase;
    private HashMap<Object,String> caracteristicas;

    public registroClasesFireBase(String usuario,String estilo,String nivel,String profesora,String uri, String minuto,String estado,String window,String nroClase){
        this.usuario=usuario;
        this.estilo=estilo;
        this.minuto=minuto;
        this.nivel=nivel;
        this.profesora=profesora;
        this.uri=uri;
        this.estado=estado;
        this.window=window;
        this.nroClase=nroClase;
    }

    public void hasMap(){
        obtenerFecha obtener= obtenerFecha.getFecha();
        String e=obtener.getFechaSistema();

        caracteristicas=new HashMap<>();
        caracteristicas.clear();
        caracteristicas.put("estilo",estilo);
        caracteristicas.put("nivel",nivel);
        caracteristicas.put("nroClase",nroClase);
        caracteristicas.put("profesora",profesora);
        caracteristicas.put("uri",uri);
        caracteristicas.put("minuto",minuto);
        caracteristicas.put("estado",estado);
        caracteristicas.put("window",window);
        caracteristicas.put("fecha",e);
            }
    public void guardarClaseenCurso(){
        hasMap();
        firebaseDatabase=FirebaseDatabase.getInstance().getReference();
        firebaseDatabase.child("Historial Clases").child(usuario).child(estilo).child(nivel).child(nroClase).setValue(caracteristicas);
    }



    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getProfesora() {
        return profesora;
    }

    public void setProfesora(String profesora) {
        this.profesora = profesora;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }
}
