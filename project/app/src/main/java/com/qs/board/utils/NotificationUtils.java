package com.qs.board.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.qs.board.BoardActivity;
import com.qs.board.R;

public class NotificationUtils {

    private static final String DISMISS_ACTION = "DISMISS";
    private static final int NOT_ID = 1;
    private static String NOTIFICATION_TITLE;

    public static void addNotification(Context context) {

        final String CHANNEL_ID = "";

        NOTIFICATION_TITLE = context.getString(R.string.app_name);
        
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);

        Intent notificationIntent = new Intent(context, BoardActivity.class);

        Intent cancelReceive = new Intent();
        cancelReceive.setAction(DISMISS_ACTION);

        PendingIntent dismissIntent = PendingIntent.getBroadcast(context, 2588, cancelReceive, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setSmallIcon(R.drawable.ic_add_circle)
                .setTicker(NOTIFICATION_TITLE)
                .setContentTitle(NOTIFICATION_TITLE)
                .setOngoing(true)
                .addAction(R.drawable.ic_close_black, context.getString(android.R.string.cancel), dismissIntent)
                .setContentText(context.getString(R.string.notification_content));

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        notificationBuilder.setContentIntent(intent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        // Create notification channel on Android O
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_TITLE, NOTIFICATION_TITLE, notificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        }

        if (notificationManager != null) {
            notificationManager.notify(NOT_ID, notificationBuilder.build());
        }
    }

    public static class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();

            if (action != null && DISMISS_ACTION.equals(action)) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                if (notificationManager != null) {
                    notificationManager.cancel(NOT_ID);
                }
            }
        }
    }
}
