package com.example.allegroandroid.models.historialdeclase;

import com.example.allegroandroid.models.user.User;

public class HistorialDeClaseRequestGet {
   public User user;
    public int taken;
    public  int pages;

    public HistorialDeClaseRequestGet(User user, int taken, int pages) {
        this.user = user;
        this.taken = taken;
        this.pages = pages;
    }
}
