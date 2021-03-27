package com.pulsario.attitudeshayariwhatsappstatus;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MassagingService extends FirebaseMessagingService {
    public static final String TITLE = "Title";
    public static final String DESC = "Desc";
    public static final String MyPref2 = "MySecPref";
    SharedPreferences sharedPreferences;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification().getTitle().contains("Quote") || remoteMessage.getNotification().getTitle().contains("Shayri") || remoteMessage.getNotification().getTitle().contains("Attitude")){
            sharedPreferences = getSharedPreferences(MyPref2, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(TITLE, remoteMessage.getNotification().getTitle());
            editor.putString(DESC, remoteMessage.getNotification().getBody());
            editor.apply();
        }
    }
}
