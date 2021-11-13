package com.example.allegrod.home.clasesRegistradas;

public class clases {
    private String estilo,nroClase,profesora;
    private String nivel,uriCLase;

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getNroClase() {
        return nroClase;
    }

    public void setNroClase(String nroClase) {
        this.nroClase = nroClase;
    }

    public String getUriCLase() {
        return uriCLase;
    }

    public void setUriCLase(String uriCLase) {
        this.uriCLase = uriCLase;
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

    public clases(String estilo, String nivel, String nroClase, String uriClase, String profesora) {
        this.estilo=estilo;
        this.nivel=nivel;
        this.nroClase=nroClase;
        this.uriCLase=uriClase;
        this.profesora=profesora;
    }
}
