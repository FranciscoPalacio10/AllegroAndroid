package com.example.allegrod.services;

public class StringService {

    public static String upperFirstLetter(String string){
        return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
    }

}
