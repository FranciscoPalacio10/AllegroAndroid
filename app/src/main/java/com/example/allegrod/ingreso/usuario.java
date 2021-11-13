package com.example.allegrod.ingreso;

public class usuario {
    private String id;
    private String nombreCompleto;
    private String estilo;
    private String nivel;


    public String getId() {
        return id;
    }


    public String getEstilo() {
        return estilo;
    }



    public String getNivel() {
        return nivel;
    }


    public String getNombreCompleto() {
        return nombreCompleto;
    }



    public usuario(String id,String nombreCompleto,String estilo,String nivel){
          this.id=id;
          this.nombreCompleto=nombreCompleto;
          this.estilo=estilo;
          this.nivel=nivel;
    }
}
