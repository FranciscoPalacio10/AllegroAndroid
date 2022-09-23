package com.example.allegroandroid.ia.posedetector.classification;

import androidx.annotation.NonNull;

import java.util.Date;

public class Counter {
    private Integer repetition;
    private Date date;
    private Long secondLast;

    public Counter(Date date) {
        this.date = date;
        this.repetition=0;
        this.secondLast=0L;
    }

    private void setSeconds(){
        Date date = getDate();
        Long secondsNow = (date.getTime()-this.date.getTime())/1000;
        if(repetition == 0){
            this.date = date;
        }
        this.secondLast = secondsNow;
    }

    public void AddSecondsAndReps(){
        addRepetition();
        setSeconds();
    }

    public void RestartsecondsAndReps(){
        restartSecond();
        restartRepetition();
    }


    public Long GetSecondsLast() {
        return secondLast;

    }

    public void restartSecond() {
        this.date = getDate();
        this.secondLast  =0L;
    }

    @NonNull
    private Date getDate() {
        return new Date();
    }

    private void restartRepetition() {
        this.repetition =0;
    }

    public Integer getRepetition(){
        return repetition;
    }

    private void addRepetition(){
        repetition++;
    }

    private void substractRepetition(){
        repetition--;
    }

}