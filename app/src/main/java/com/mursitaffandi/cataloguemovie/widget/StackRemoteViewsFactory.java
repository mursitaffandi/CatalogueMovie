package com.mursitaffandi.cataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.mursitaffandi.cataloguemovie.BuildConfig;
import com.mursitaffandi.cataloguemovie.DetailActivity;
import com.mursitaffandi.cataloguemovie.R;
import com.mursitaffandi.cataloguemovie.db.DBMovieContract;
import com.mursitaffandi.cataloguemovie.model.Result;
import com.mursitaffandi.cataloguemovie.util.Constant;

import java.util.concurrent.ExecutionException;

/**
 * Created by mursitaffandi on 16/01/18.
 */

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private int mAppWidgetId;

    private Cursor list;

    public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        list = mContext.getContentResolver().query(
                DBMovieContract.MovieColumns.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Result item = getItem(i);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_favorite);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.BASE_URL_IMG + "w500" + item.getBackdropPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, i);
        extras.putInt(Constant.TAG_DETAIL, item.getId());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Result getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new Result(list);
    }
}
