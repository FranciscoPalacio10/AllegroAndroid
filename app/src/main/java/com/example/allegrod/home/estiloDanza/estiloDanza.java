package com.example.allegrod.home.estiloDanza;

import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class estiloDanza {
    private int imageView;
    private String nombreEstilo,leyenda,nombreParanivel;
private FragmentManager fragmentTransaction;
    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getNombreEstilo() {
        return nombreEstilo;
    }

    public void setNombreEstilo(String nombreEstilo) {
        this.nombreEstilo = nombreEstilo;
    }

    public estiloDanza(int imageView, String nombreEstilo,String leyenda,String nombreParanivel){
        this.imageView=imageView;
        this.nombreEstilo=nombreEstilo;
        this.leyenda=leyenda;
        this.nombreParanivel=nombreParanivel;

    }

    public String getNombreParanivel() {
        return nombreParanivel;
    }

    public void setNombreParanivel(String nombreParanivel) {
        this.nombreParanivel = nombreParanivel;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }
}
