package com.example.allegrod.clases;

public class clasesEnCursoLista {
    private String estilo,nivel,profesora,numeroClase,
            minuto,uri,fecha;


    public String getMinuto() {
        return minuto;
    }

    public String getUri() {
        return uri;
    }

    public String getFecha() {
        return fecha;
    }

    public clasesEnCursoLista(String estilo, String nivel, String profesora, String numeroClase, String minuto, String uri, String fecha) {
        this.estilo=estilo;
        this.nivel=nivel;
        this.profesora=profesora;
        this.numeroClase=numeroClase;
        this.minuto=minuto;
        this.uri=uri;
        this.fecha=fecha;
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

    public String getNumeroClase() {
        return numeroClase;
    }

    public void setNumeroClase(String numeroClase) {
        this.numeroClase = numeroClase;
    }
}


