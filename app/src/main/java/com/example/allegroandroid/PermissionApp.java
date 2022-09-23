package com.example.allegroandroid;

import static com.example.allegroandroid.constants.AppConstant.CODIGO_PERMISOS_CAMARA;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.allegroandroid.constants.AppConstant;

public class PermissionApp {

    private Activity activity;

    public PermissionApp(Activity activity) {
        this.activity = activity;
        int permissionCheck = ContextCompat.checkSelfPermission(
                activity, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CAMERA)) {
                showExplanation("Advertencia", "Si no concede los permisos no podra realizar la clase", Manifest.permission.CAMERA, CODIGO_PERMISOS_CAMARA);
            } else {
                requestPermission(Manifest.permission.CAMERA, CODIGO_PERMISOS_CAMARA);
            }
        } else {
           // Toast.makeText(activity, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
        }

    }

    public void processonRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISOS_CAMARA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    activity.onBackPressed();
                }
                return;
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permissionName}, permissionRequestCode);
    }
}
