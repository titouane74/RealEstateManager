package com.openclassrooms.realestatemanager.workmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.openclassrooms.realestatemanager.R;

/**
 * Created by Florence LE BOURNOT on 14/12/2020
 */
//public class NotifyWorker  extends Worker {
    public class NotifyWorker {

    public static final String NOTIF_CHANNEL_ID = "NOTIF_CHANNEL_ID";
    public static final String NOTIF_CHANNEL_NAME = "NOTIF_CHANNEL_NAME";
    public static final int NOTIF_ID = 100;
    //private final Context mContext;

//    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
//        super(context, workerParams);
//        mContext = context;
//    }

    public static void createNotification(Context pContext) {

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
        String lMessage = pContext.getString(R.string.default_txt_notification_message);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            lBuilder = new NotificationCompat.Builder(pContext, NOTIF_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setColor(ContextCompat.getColor(pContext, R.color.colorPrimary))
                    .setContentTitle(lTitle)
                    .setContentText(lMessage)
                    .setAutoCancel(true)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        } else {
            lBuilder = new NotificationCompat.Builder(pContext, NOTIF_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setColor(ContextCompat.getColor(pContext, R.color.colorPrimary))
                    .setContentTitle(lTitle)
                    .setContentText(lMessage)
                    .setAutoCancel(true)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        }

        lBuilder.setOnlyAlertOnce(true);

        NotificationManagerCompat lNotificationManager = NotificationManagerCompat.from(pContext);
        lNotificationManager.notify(NOTIF_ID, lBuilder.build());
    }

/*    @NonNull
    @Override
    public Result doWork() {

        createNotification();

        return Result.success();
    }*/
}
