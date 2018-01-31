package com.mursitaffandi.cataloguemovie.util.scheduler;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;

/**
 * Created by sidiqpermana on 10/5/16.
 */

public class SchedulerTask {
    private GcmNetworkManager mGcmNetworkManager;

    public SchedulerTask(Context context){
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        com.google.android.gms.gcm.Task periodicTask = new PeriodicTask.Builder()
                .setService(ServiceNowPlaying.class)
                .setPeriod(60)
                .setFlex(10)
                .setTag(ServiceNowPlaying.TAG_TASK_MOVIE_LOG)
                .setPersisted(true)
                .build();

        mGcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask(){
        if (mGcmNetworkManager != null){
            mGcmNetworkManager.cancelTask(ServiceNowPlaying.TAG_TASK_MOVIE_LOG, ServiceNowPlaying.class);
        }
    }
}
