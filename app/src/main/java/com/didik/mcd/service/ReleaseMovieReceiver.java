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
import com.didik.mcd.data.entity.Movie;
import com.didik.mcd.ui.movie.detail.MovieDetailActivity;

import java.util.Calendar;
import java.util.List;

import androidx.core.app.NotificationCompat;

public class ReleaseMovieReceiver extends BroadcastReceiver {
    private static final String NOTIFICATION_CHANNEL_NAME = "release_channel_name";
    private static final String NOTIFICATION_CHANNEL_ID = "release_reminder_channel";
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_ID = "key_id";
    private static final String KEY_NOTIF_ID = "key_notif_id";
    private static final int NOTIFICATION_ID = 110;
    private static int notifId = 1100;

    private static PendingIntent getReleaseMoviePendingIntent(Context context) {
        Intent reminderIntent = new Intent(context, ReleaseMovieReceiver.class);
        boolean isActiveReminder = (PendingIntent.getBroadcast(context, notifId, reminderIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        Log.d("isActiveReminder", "" + isActiveReminder);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, reminderIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }

    private void showNotification(Context context, int notifId, String title) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Uri reminderSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MovieDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText("Ada film baru, judulnya " + title)
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
        notificationManager.notify(notifId, builder.build());
    }

    public void setRepeatingReminder(Context context, List<Movie> movies) {
        int delay = 0;

        for (Movie movie : movies) {
            dismissReminder(context);
            AlarmManager alarmManager =
                    (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(context, ReleaseMovieReceiver.class);
            intent.putExtra(KEY_TITLE, movie.getTitle());
            intent.putExtra(KEY_ID, movie.getId());
            intent.putExtra(KEY_NOTIF_ID, notifId);

            PendingIntent pendingIntent = PendingIntent
                    .getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + delay,
                        pendingIntent);
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay, pendingIntent);
            }

            notifId += 2;
            delay += 6000;
        }
    }

    public void dismissReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getReleaseMoviePendingIntent(context));
    }
}
