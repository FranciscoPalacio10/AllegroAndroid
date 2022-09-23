package com.example.allegroandroid.models.response;

public class Paging {
    private int total;
    private int page;
    private int taked;

    public Paging(int total, int page, int taked) {
        this.total = total;
        this.page = page;
        this.taked = taked;
    }
}
