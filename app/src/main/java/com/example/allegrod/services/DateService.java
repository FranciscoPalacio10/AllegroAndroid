package com.example.allegrod.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateService {
    private static DateService fecha;
    private String formato = "yyyy-MM-dd";
    private SimpleDateFormat sdf;
    private String zonaHoraria= "GMT-3";;

    private DateService(){
        sdf = new SimpleDateFormat(formato);
        sdf.setTimeZone(TimeZone.getTimeZone(zonaHoraria));
    }

    public static DateService getInstance(){
        if(fecha==null){
            fecha=new DateService();
        }
        return fecha;
    }

    public String getFechaSistema(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    public String parseDate(int year,int month,int day){
        int monthPlusOne= month+1;
        return year+"-"+twoDigits(monthPlusOne)+"-"+day;
    }

    public Date convertStringToDate(String date) throws ParseException {
        return sdf.parse(date);
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}
