package com.example.allegrobackend.services;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;

import com.example.allegrobackend.ui.MainActivity;
import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.ui.ErrorActivity;
import com.example.allegrobackend.ui.start.AutenticacionActivity;
import com.example.allegrobackend.ui.start.CompleteProfileActivity;

public class ActivitiesInitiator {

    public static void initCompleteProfileActivity(InitActitvy init) {
        Intent intent = new Intent(init.getContext(), CompleteProfileActivity.class);
        startActivity(init.getContext(),intent,init.getBundle());
    }

    public static void initErrorActivity(InitActitvy init)  {
        Intent intent = new Intent(init.getContext(), ErrorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(init.getBundle());
        startActivity(init.getContext(),intent,init.getBundle());
    }

    public static void initMainActivity(InitActitvy init){
        Intent intent = new Intent(init.getContext(), MainActivity.class);
        startActivity(init.getContext(),intent,init.getBundle());
    }

    public static void initAutenticationActivity(InitActitvy init){
        Intent intent = new Intent(init.getContext(), AutenticacionActivity.class);
        startActivity(init.getContext(),intent,init.getBundle());
    }

}
