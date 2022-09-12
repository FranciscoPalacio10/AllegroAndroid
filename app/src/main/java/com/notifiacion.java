package com;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.allegrobackend.R;
import com.example.allegrobackend.reprodcutor.reproductorClase;

import static androidx.core.content.ContextCompat.getSystemService;

public class notifiacion {

    private Context context;
    private String CHANNEL_ID,profesora,tiempo;
    private int notificationId;
    private PendingIntent pendingIntent;
    private String url,estilo,nivel,nroClase;


    public notifiacion(Context context, String CHANNEL_ID, int notificationId, String url, String estilo, String nivel, String nroClase, String profesora)  {
    this.context=context;
    this.CHANNEL_ID=CHANNEL_ID;
    this.notificationId=notificationId;
    this.url=url;
    this.estilo=estilo;
    this.nivel=nivel;
    this.nroClase=nroClase;
  this.profesora=profesora;

    }


    public void cerrarNotificacion(){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notificationId);
    }

    public void pendingIntent(){
        final Intent notificationIntent = new Intent(context, reproductorClase.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent=PendingIntent.getActivity(context, 0, notificationIntent, 0);
    }

    public void crearNotificacion(int image, String titulo, String texto,String tituloBotn){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(image)
                .setContentTitle(titulo)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(texto))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true)
                .addAction(R.drawable.exo_icon_play,"Volver a clase",pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());

            }

    public void createNotificationChannel(CharSequence names, String channel_description) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = names;
            String description =channel_description;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(context,NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }




}
