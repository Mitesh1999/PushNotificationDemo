package com.example.makwanamitesh.pushnotificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived( remoteMessage );

        Log.e( "Msg :", " -=> " + remoteMessage.getData() );

        Map<String, String> data = remoteMessage.getData();

        Log.e( "Title :", " -=> " + data.get( "title" ) );

        createNotification( data.get( "title" ) );

    }

    private void createNotification(String msg){

        Intent intent = new Intent( this, MainActivity.class );

        PendingIntent pendingIntent = PendingIntent.getActivity( this, 100, intent, PendingIntent.FLAG_ONE_SHOT );

        Uri ring = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );


        //notification buliding
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, "0" )
                .setContentText( msg )
                .setContentTitle( "Demo" )
                .setSmallIcon( R.drawable.ic_launcher_background )
                .setContentIntent( pendingIntent )
                .setSound( ring );

       NotificationManager manager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );


        //notification setup
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel( "0", "Demo", NotificationManager.IMPORTANCE_DEFAULT );
            manager.createNotificationChannel( channel );
        }

        manager.notify( 0, builder.build() );


    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken( s );
        Log.e( "Token :", " -=> " + s );
    }
}
