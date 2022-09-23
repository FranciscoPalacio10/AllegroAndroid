package com.example.allegroandroid.ia;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class Speaker implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;

    private boolean ready = false;

    private boolean allowed = false;

    public Speaker(Context context){
        tts = new TextToSpeech(context, this);
    }

    public TextToSpeech getTts() {
        return tts;
    }

    public boolean isAllowed(){
        return allowed;
    }

    public void allow(boolean allowed){
        this.allowed = allowed;
    }

    private  Long lastSecond = 0l;

    private Boolean isStart = false;
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            Locale locSpanish = new Locale("spa", "MEX");
            tts.setLanguage(locSpanish);
            tts.setPitch(1);
            ready = true;
        }else{
            ready = false;
        }
    }

    public void speakSuggest(String suggest) {

        // Speak only if the TTS is ready
        // and the user has allowed speech
        if (suggest != null) {
            if (ready) {
                if (!tts.isSpeaking()) {
                    tts.speak(suggest, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
    }

    public void speakStart(String suggest) {

        // Speak only if the TTS is ready
        // and the user has allowed speech
        if (suggest != null) {
            if (ready) {
                if (!isStart && !tts.isSpeaking()) {
                    tts.speak(suggest, TextToSpeech.QUEUE_FLUSH, null);
                    isStart = true;
                    try {
                        // le damos tiempo de terminar de hablar antes de iniciar con la evaluacion
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void speakSecond(Long seconds) {
        // Speak only if the TTS is ready
        // and the user has allowed speech
        if (seconds != null) {
            if (ready) {
                // reinicia el contador
                if(lastSecond > seconds){
                    lastSecond = seconds;
                }

                if (!tts.isSpeaking() && lastSecond !=  seconds && seconds > 0) {
                    lastSecond = seconds;
                    tts.speak(seconds.toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
    }




    public void destroy(){
        tts.shutdown();
    }
}