package com.example.allegrobackend.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateService {
    private static DateService fecha;
    public static final String DATE_FORMAT_8 = "yyyy-MM-dd";
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

    public Date getDateNow(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public String parseDate(int year,int month,int day){
        int monthPlusOne= month+1;
        return year+"-"+twoDigits(monthPlusOne).trim()+"-"+day;
    }

    public String convertStringToDateString(String date) throws ParseException {
        Date mParsedDate;
        String mOutputDateString;
        SimpleDateFormat mInputDateFormat =
                new SimpleDateFormat(formato, java.util.Locale.getDefault());
        SimpleDateFormat mOutputDateFormat =
                new SimpleDateFormat(DATE_FORMAT_8, java.util.Locale.getDefault());
        mParsedDate = mInputDateFormat.parse(date);
        mOutputDateString = mOutputDateFormat.format(mParsedDate);
        return mOutputDateString;
    }


    public Date convertStringToDate(String date) throws ParseException {
        Date mParsedDate;
        String mOutputDateString;
        SimpleDateFormat mInputDateFormat =
                new SimpleDateFormat(formato, java.util.Locale.getDefault());
        SimpleDateFormat mOutputDateFormat =
                new SimpleDateFormat(DATE_FORMAT_8, java.util.Locale.getDefault());
        mParsedDate = mInputDateFormat.parse(date);
        return mParsedDate;
    }

    public int getYearsBetweenTwoDates(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) &&
                        a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static String convertSecondsToMinutes(long seconds) {
        long minute = TimeUnit.SECONDS.toMinutes(seconds) -
                (TimeUnit.SECONDS.toHours(seconds)* 60);

        long second = TimeUnit.SECONDS.toSeconds(seconds) -
                (TimeUnit.SECONDS.toMinutes(seconds) *60);

        return minute+ ":" +second;
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}