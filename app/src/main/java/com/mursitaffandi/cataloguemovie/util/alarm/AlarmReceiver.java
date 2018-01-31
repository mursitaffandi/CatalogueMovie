package com.mursitaffandi.cataloguemovie.util.alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.mursitaffandi.cataloguemovie.MainActivity;
import com.mursitaffandi.cataloguemovie.R;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String EXTRA_TYPE = "type";

    private final int NOTIF_ID_REPEATING = 101;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = context.getResources().getString(R.string.app_name);
        int notifId = NOTIF_ID_REPEATING;

        Log.v("ON RECIEVE",title+" "+notifId);
        showAlarmNotification(context, title, notifId);
    }

    private void showAlarmNotification(Context context, String title, int notifId){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_access_alarm_black_36dp)
                .setContentTitle(title)
                .setContentText(context.getResources().getString(R.string.text_body_notif_daily_reminder))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(contentIntent)
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }


    public void setRepeatingAlarm(Context context, int hour, int minute){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        Log.d("REPEAT",String.valueOf(minute));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, context.getString(R.string.message_set_daily_reminder_on), Toast.LENGTH_SHORT).show();
    }


    public void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);

        Toast.makeText(context, context.getString(R.string.message_set_daily_reminder_off), Toast.LENGTH_SHORT).show();
    }
}
