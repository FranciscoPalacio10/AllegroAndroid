package com.example.allegroandroid.services;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;

import com.example.allegroandroid.ui.MainActivity;
import com.example.allegroandroid.models.InitActitvy;
import com.example.allegroandroid.ui.ErrorActivity;
import com.example.allegroandroid.ui.core.clases.ResultActivity;
import com.example.allegroandroid.ui.start.AuthenticationActivity;
import com.example.allegroandroid.ui.start.CompleteProfileActivity;

public class ActivitiesInitiator {

    public static void initCompleteProfileActivity(InitActitvy init) {
        Intent intent = new Intent(init.getContext(), CompleteProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(init.getContext(), intent, init.getBundle());
    }

    public static void initErrorActivity(InitActitvy init) {
        Intent intent = new Intent(init.getContext(), ErrorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(init.getBundle());
        startActivity(init.getContext(), intent, init.getBundle());
    }

    public static void initMainActivity(InitActitvy init) {
        Intent intent = new Intent(init.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(init.getContext(), intent, init.getBundle());
    }

    public static void initAutenticationActivity(InitActitvy init) {
        Intent intent = new Intent(init.getContext(), AuthenticationActivity.class);
        startActivity(init.getContext(), intent, init.getBundle());
    }

    public static void initResultActivity(InitActitvy init) {
        Intent intent = new Intent(init.getContext(), ResultActivity.class);
        intent.putExtras(init.getBundle());
        startActivity(init.getContext(), intent, init.getBundle());
    }


}
