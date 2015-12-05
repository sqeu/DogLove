package com.love.dog.doglove.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.love.dog.doglove.MatchPatitaActivity;
import com.love.dog.doglove.R;

/**
 * Created by Hugo on 10/28/2015.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private static final String TAG = "GcmIntentService";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    String idDueno1,  idMascota1,  idDuenoPareja,  idMascotaPareja,idChat,idFoto1,idFoto2;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {

            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " +
                        extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());


                System.out.println(extras.getString("message"));//gettea partes individuales
                idDueno1 =extras.getString("iddueno1");
                idMascota1= extras.getString("idmascota1");

                idFoto1=extras.getString("idfoto1");
                idFoto2=extras.getString("idfoto2");

                idDuenoPareja=extras.getString("iddueno2");
                idMascotaPareja= extras.getString("idmascota2");
                idChat=extras.getString("idchat");
                sendNotification(extras.toString()); //esto es lo q se envia en notifiacione
                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
//intent crear uno nuevo y poner la data ahi
        Intent intent = new Intent(this, MatchPatitaActivity.class);
        intent.putExtra("iddueno1", idDueno1);
        intent.putExtra("idmascota1", idMascota1);
        intent.putExtra("idfoto1",idFoto1);
        intent.putExtra("iddueno2", idDuenoPareja);
        intent.putExtra("idmascota2", idMascotaPareja);
        intent.putExtra("idfoto2",idFoto2);
        intent.putExtra("idchat",idChat);
        //new Intent(this, MatchPatitaActivity.class)
        PendingIntent contentIntent = PendingIntent.getActivity(
                this.getApplicationContext(), (int) (Math.random() * 100),
               intent , PendingIntent.FLAG_UPDATE_CURRENT);//esto es lo q se abriar al tocar notifiaction
/*PendingIntent contentIntent = PendingIntent.getActivity(
        this.getApplicationContext(), 0,
                intent , 0)*/
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.dog_bone)
                        .setContentTitle("Match patita!")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Se encontro un match"))//msg
                        .setContentText("Se encontro un match");

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}