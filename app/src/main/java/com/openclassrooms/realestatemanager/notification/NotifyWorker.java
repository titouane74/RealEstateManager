package com.openclassrooms.realestatemanager.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.openclassrooms.realestatemanager.R;

/**
 * Created by Florence LE BOURNOT on 14/12/2020
 */

    public class NotifyWorker {

    public static final String NOTIF_CHANNEL_ID = "NOTIF_CHANNEL_ID";
    public static final String NOTIF_CHANNEL_NAME = "NOTIF_CHANNEL_NAME";
    public static final int NOTIF_ID = 100;

    public static void createNotification(Context pContext, boolean pIsMandatoryDataComplete) {

        NotificationCompat.Builder lBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager lManager = pContext.getSystemService(NotificationManager.class);

            NotificationChannel lChannel1 = new NotificationChannel(
                    NOTIF_CHANNEL_ID, NOTIF_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);

            if (lManager != null) {
                lManager.createNotificationChannel(lChannel1);
            }
        }

        String lTitle = pContext.getString(R.string.app_name);
        String lMessage ;
        if (pIsMandatoryDataComplete) {
            lMessage = pContext.getString(R.string.default_txt_notification_message_successful);
        } else {
            lMessage = pContext.getString(R.string.default_txt_notification_message_incomplete);
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            lBuilder = new NotificationCompat.Builder(pContext, NOTIF_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setColor(ContextCompat.getColor(pContext, R.color.colorPrimary))
                    .setContentTitle(lTitle)
                    .setContentText(lMessage)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText(lMessage + " " + pContext.getString(R.string.default_txt_notification_message_to_complete)))
                    .setAutoCancel(true)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        } else {
            lBuilder = new NotificationCompat.Builder(pContext, NOTIF_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setColor(ContextCompat.getColor(pContext, R.color.colorPrimary))
                    .setContentTitle(lTitle)
                    .setContentText(lMessage)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText(lMessage + " " + pContext.getString(R.string.default_txt_notification_message_to_complete)))
                    .setAutoCancel(true)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        }

        lBuilder.setOnlyAlertOnce(true);

        NotificationManagerCompat lNotificationManager = NotificationManagerCompat.from(pContext);
        lNotificationManager.notify(NOTIF_ID, lBuilder.build());
    }
}
