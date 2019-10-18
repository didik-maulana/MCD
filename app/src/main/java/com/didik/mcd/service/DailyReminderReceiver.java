package com.didik.mcd.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.didik.mcd.R;
import com.didik.mcd.ui.main.MainActivity;

import java.util.Calendar;

import androidx.core.app.NotificationCompat;

public class DailyReminderReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 100;
    private static final String NOTIFICATION_CHANNEL_NAME = "channel_name";
    private static final String NOTIFICATION_CHANNEL_ID = "daily_reminder_channel";

    private static PendingIntent getDailyReminderPendingIntent(Context context) {
        Intent reminderIntent = new Intent(context, DailyReminderReceiver.class);
        boolean isActiveReminder =
                (PendingIntent.getBroadcast(context, NOTIFICATION_ID, reminderIntent,
                        PendingIntent.FLAG_NO_CREATE) != null);
        Log.d("isActiveReminder", "" + isActiveReminder);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, reminderIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context,
                context.getString(R.string.app_name),
                context.getString(R.string.msg_daily_reminder),
                NOTIFICATION_ID);
    }

    private void showNotification(Context context,
                                  String title,
                                  String message,
                                  int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Uri reminderSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(false)
                .setSound(reminderSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            NotificationChannel notificationChannel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                            NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(notificationId, builder.build());
    }

    public void setRepeatingReminder(Context context) {
        dismissReminder(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    getDailyReminderPendingIntent(context));
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    getDailyReminderPendingIntent(context)
            );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager
                    .setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            getDailyReminderPendingIntent(context));
        }
    }

    public void dismissReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getDailyReminderPendingIntent(context));
    }
}
