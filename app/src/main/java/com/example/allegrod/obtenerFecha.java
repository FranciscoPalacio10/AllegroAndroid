package com.example.allegrod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class obtenerFecha {
    private static obtenerFecha fecha;

    private obtenerFecha(){

    }

    public static obtenerFecha getFecha(){
        if(fecha==null){
            fecha=new obtenerFecha();
        }
        return fecha;
    }

    public String getFechaSistema(){
        Calendar calendar = Calendar.getInstance();
        String formato = "yyyy-MM-dd";
        String zonaHoraria= "GMT-3";
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(formato);
        sdf.setTimeZone(TimeZone.getTimeZone(zonaHoraria));
        return sdf.format(date);
    }
}
