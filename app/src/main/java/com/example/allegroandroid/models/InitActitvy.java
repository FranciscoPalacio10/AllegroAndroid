package com.example.allegroandroid.models;

import android.content.Context;
import android.os.Bundle;

public class InitActitvy {
    private Context context;
    private Bundle bundle;

    public InitActitvy(Context context, Bundle bundle) {
        this.context = context;
        this.bundle = bundle;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
