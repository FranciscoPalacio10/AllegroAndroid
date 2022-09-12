package com.example.allegroandroid.ui.core.nivelDanza;

public class nivelClase {
    private String nivel,estilo,leyenda;
    private int Imagen;

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    public nivelClase(String nivel, String estilo, int Imagen, String leyenda) {
        this.nivel=nivel;
        this.estilo=estilo;
        this.Imagen=Imagen;
        this.leyenda=leyenda;

    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }
}
