package com.mursitaffandi.cataloguemovie.util.scheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.mursitaffandi.cataloguemovie.DetailActivity;
import com.mursitaffandi.cataloguemovie.R;
import com.mursitaffandi.cataloguemovie.model.MMovie;
import com.mursitaffandi.cataloguemovie.model.Result;
import com.mursitaffandi.cataloguemovie.network.ClientCall;
import com.mursitaffandi.cataloguemovie.util.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceNowPlaying extends GcmTaskService {

    final String TAG = ServiceNowPlaying.class.getSimpleName();
    static String TAG_TASK_MOVIE_LOG = "NowPlayingTask";
    private Call<MMovie> apiCall;
    private ClientCall apiClient = new ClientCall();

    private ArrayList<Result> list;

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        SchedulerTask mSchedulerTask = new SchedulerTask(this);
        mSchedulerTask.createPeriodicTask();
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_MOVIE_LOG)) {
            getNowPlaying();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    private void getNowPlaying() {
        Log.d(TAG, "getNowPlaying is Running");
        apiCall = apiClient.getService().getUpcomingMovie();
        apiCall.enqueue(new Callback<MMovie>() {
            @Override
            public void onResponse(Call<MMovie> call, Response<MMovie> response) {
                list = new ArrayList<>(response.body().getResults());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                String string_today = sdf.format(today);
                int notifId = 200;
                for (Result upcoming : list) {
                    String upcomingDate = upcoming.getReleaseDate();
                    if (upcomingDate.equals(string_today)) {
                        String title = upcoming.getTitle();
                        String message = String.format(getResources().getString(R.string.message_upcoming_release), title);

                        showNotification(getApplicationContext(), title, message, upcoming.getId(), notifId);
                        notifId++;
                        Log.d(TAG,   title + " = " + message + " " + notifId + " " + String.valueOf(upcoming.getId()));
                    }
                }
            }

            @Override
            public void onFailure(Call<MMovie> call, Throwable t) {

            }
        });
    }

    private void showNotification(Context context, String title, String message, int movieId, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constant.TAG_DETAIL, movieId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent)
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
