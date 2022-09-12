package com.example.allegrobackend.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class PopUpService {
   private static PopUpService popUpService;

    public static PopUpService getPopUpService( ) {
        if (popUpService == null){
            return new PopUpService();
        }
        return popUpService;
    }

    public void popupMessage(Context context,String text,String Title){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(text);
        alertDialogBuilder.setTitle(Title);
        alertDialogBuilder.setNegativeButton("ok", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // add these two lines, if you wish to close the app:
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
