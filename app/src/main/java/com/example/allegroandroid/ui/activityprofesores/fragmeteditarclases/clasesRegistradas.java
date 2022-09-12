package com.example.allegroandroid.ui.activityprofesores.fragmeteditarclases;

public class clasesRegistradas {
        private String estiloDanza;
        private String nivelDanza;
        private String numerodeClase;
        private String nombreProfesora;
        private String fechaPublicacion;
        private String uriVideo;
        private String idClase;

        public clasesRegistradas(String fechaPublicacion,String idClase,String uriVideo,String nroClase,String nombreProfesora,String estiloDanza,String nivelDanza){
            this.fechaPublicacion=fechaPublicacion;
            this.idClase=idClase;
            this.numerodeClase=nroClase;
            this.uriVideo=uriVideo;
            this.nombreProfesora=nombreProfesora;
            this.estiloDanza=estiloDanza;
            this.nivelDanza=nivelDanza;
        }

        public String obtenerRuta(){
            String ruta;
            String estilo= this.estiloDanza.substring(0,3);
            String nivel= this.nivelDanza.substring(0,3);
            String clase=this.numerodeClase;
            String nombre=this.nombreProfesora.substring(0,3);
            ruta=estilo.concat(nivel).concat(clase).concat(nombre);


            return ruta;
        }

    public String getEstiloDanza() {
        return estiloDanza;
    }

    public void setEstiloDanza(String estiloDanza) {
        this.estiloDanza = estiloDanza;
    }

    public String getNivelDanza() {
        return nivelDanza;
    }

    public void setNivelDanza(String nivelDanza) {
        this.nivelDanza = nivelDanza;
    }

    public String getNumerodeClase() {
        return numerodeClase;
    }

    public void setNumerodeClase(String numerodeClase) {
        this.numerodeClase = numerodeClase;
    }

    public String getNombreProfesora() {
        return nombreProfesora;
    }

    public void setNombreProfesora(String nombreProfesora) {
        this.nombreProfesora = nombreProfesora;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getUriVideo() {
        return uriVideo;
    }

    public void setUriVideo(String uriVideo) {
        this.uriVideo = uriVideo;
    }

    public String getIdClase() {
        return idClase;
    }

    public void setIdClase(String idClase) {
        this.idClase = idClase;
    }
}
