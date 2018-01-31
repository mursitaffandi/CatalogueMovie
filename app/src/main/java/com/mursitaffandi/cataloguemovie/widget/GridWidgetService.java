package com.mursitaffandi.cataloguemovie.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by mursitaffandi on 15/01/18.
 */

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
