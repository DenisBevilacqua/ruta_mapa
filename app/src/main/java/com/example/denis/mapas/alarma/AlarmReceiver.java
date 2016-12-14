package com.example.denis.mapas.alarma;

/**
 * Created by denis on 10/10/2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.denis.mapas.R;
import com.example.denis.mapas.dao.ProyectoApiRest;
import com.example.denis.mapas.modelo.Recorrido;
import com.example.denis.mapas.recorridos.ListadoRecorridosActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;


public class AlarmReceiver extends BroadcastReceiver {

    private Recorrido r1 = new Recorrido();
    private Recorrido r2 = new Recorrido();
    private Recorrido r3 = new Recorrido();
    private Recorrido r4 = new Recorrido();

    @Override

    public void onReceive(Context context, Intent intent) {

        r1.setOrigen(new LatLng(-31.63906, -60.7149269));
        r1.setOrigen_latitud(-31.63906);
        r1.setOrigen_longitud(-60.7149269);
        r1.setDestino_latitud(-31.6441904);
        r1.setDestino_longitud(-60.7058614);
        r1.setDestino(new LatLng(-31.6441904, -60.7058614));
        r1.setNombre_origen("Francia 3039, S3000FCR Santa Fe");
        r1.setNombre_destino("Rivadavia 2763, Santa Fe");
        r2.setOrigen(new LatLng(-31.6390265, -60.6965438));
        r2.setOrigen_latitud(-31.6390265);
        r2.setOrigen_longitud(-60.6965438);
        r2.setDestino_latitud(-31.6435474);
        r2.setDestino_longitud(-60.7153396);
        r2.setDestino(new LatLng(-31.6435474, -60.7153396));
        r2.setNombre_origen("Ituzaingó 1942, Santa Fe");
        r2.setNombre_destino("La Rioja 3300, S3000BXV Santa Fe");
        r3.setOrigen(new LatLng(-31.6304919, -60.6906802));
        r3.setOrigen_latitud(-31.6304919);
        r3.setOrigen_longitud(-60.6906802);
        r3.setDestino_latitud(-31.6531722);
        r3.setDestino_longitud(-60.7116439);
        r3.setDestino(new LatLng(-31.6531722, -60.7116439));
        r3.setNombre_origen("Córdoba 1661, S3002BMT Santa Fe");
        r3.setNombre_destino("Corrientes 2801-2843, S3000JDL Santa Fe");
        r4.setOrigen(new LatLng(-31.6386309, -60.7111247));
        r4.setOrigen_latitud(-31.6386309);
        r4.setOrigen_longitud(-60.7111247);
        r4.setDestino_latitud(-31.6450437);
        r4.setDestino_longitud(-60.7233633);
        r4.setDestino(new LatLng(-31.6450437, -60.7233633));
        r4.setNombre_origen("Suipacha 3042, 3000 Santa Fe");
        r4.setNombre_destino("Mendoza 3801-3899, Santa Fe");

        // For our recurring task, we'll just display a message

        /*int milisegundos = (int) System.currentTimeMillis();
        Integer resto = milisegundos % 4;*/

        Random r = new Random();
        int Low = 0;
        int High = 3;
        int resto = r.nextInt(High-Low) + Low;

        if(resto == 0) {
            new GetRecorridoAleatorio(resto).execute("");
            showNotification(context, r1.getNombre_origen(), r1.getNombre_destino());
        }else if(resto == 1) {
            new GetRecorridoAleatorio(resto).execute("");
            showNotification(context, r2.getNombre_origen(), r2.getNombre_destino());
        }else if(resto == 2) {
            new GetRecorridoAleatorio(resto).execute("");
            showNotification(context, r3.getNombre_origen(), r3.getNombre_destino());
        }else if(resto == 3) {
            new GetRecorridoAleatorio(resto).execute("");
            showNotification(context, r4.getNombre_origen(), r4.getNombre_destino());
        } else

        {
            Toast.makeText(context, "No se generará notificación.", Toast.LENGTH_SHORT).show();
        }

    }

    private void showNotification(Context context, String origen, String destino) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, ListadoRecorridosActivity.class), 0);

        // Invoking the default notification service
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);


        mBuilder.setContentTitle("Nuevo Recorrido");
        mBuilder.setContentText("Origen: " + origen);
        mBuilder.setTicker("Se ha generado un Recorrido");
        mBuilder.setSmallIcon(R.drawable.location);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Origen: " + origen + "\nDestino: " + destino);
        bigText.setBigContentTitle("Nuevo Recorrido");
        bigText.setSummaryText("Se ha generado un nuevo Recorrido");

        mBuilder.setStyle(bigText);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        mBuilder.setContentIntent(contentIntent);
        //mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        //PreferenciasActivity p = new PreferenciasActivity();

        //Usuario user = new Usuario();
        //Toast.makeText(context, user.getUri().toString() , Toast.LENGTH_SHORT).show();


        /*String uriString = reserva.getUsuario().getUriString();
        Uri uri = Uri.parse(uriString);
        mBuilder.setSound(uri);*/

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        String strRingtonePreference = preference.getString("pref_tone", "DEFAULT_SOUND");

        Uri uri = Uri.parse(strRingtonePreference);
        mBuilder.setSound(uri);
        //notification.sound = Uri.parse(strRingtonePreference);


        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }

    private class GetRecorridoAleatorio extends AsyncTask<Object, Object, Integer> {
        Integer resto;
        public GetRecorridoAleatorio(Integer r) {
            resto = r;
        }

        @Override
        protected Integer doInBackground(Object... params) {
            ProyectoApiRest rest = new ProyectoApiRest();

            Log.d("TAG RESTO", ""+ resto);
            if(resto == 0) rest.crearRecorrido(r1);
            if(resto == 1) rest.crearRecorrido(r2);
            if(resto == 2) rest.crearRecorrido(r3);
            if(resto == 3) rest.crearRecorrido(r4);

            return 1;
        }

    }
}