package com.mursitaffandi.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mursitaffandi.cataloguemovie.BuildConfig;
import com.mursitaffandi.cataloguemovie.DetailActivity;
import com.mursitaffandi.cataloguemovie.R;
import com.mursitaffandi.cataloguemovie.model.Result;
import com.mursitaffandi.cataloguemovie.util.Constant;
import com.mursitaffandi.cataloguemovie.util.DateTime;

/**
 * Created by mursitaffandi on 14/01/18.
 */

public class AdapterFavoriteMovie extends RecyclerView.Adapter<ResultVH>implements ResultVH.VHEvent {
    private Context mContext;
    private Cursor cursor;
    public AdapterFavoriteMovie(Context mContext) {
        this.mContext = mContext;
    }

    public void swapFavorite(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public ResultVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, null);
        return new ResultVH(view, this);
    }

    @Override
    public void onBindViewHolder(ResultVH holder, int position) {
        Result resultList = getItem(position);
        Glide.with(mContext).load(BuildConfig.BASE_URL_IMG + Constant.SIZE_IMAGE_POSTER + resultList.getPosterPath()).into(holder.imageView);
        holder.tv_title.setText(resultList.getOriginalTitle());
        holder.tv_overview.setText(resultList.getOverview());
        holder.tv_release.setText(DateTime.getLongDate(resultList.getReleaseDate()));
    }
    private Result getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Cursor Position invalid!");
        }
        return new Result(cursor);
    }
    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    @Override
    public void postItemClick(ResultVH resultVH) {
        final Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(Constant.TAG_DETAIL, getItem(resultVH.getAdapterPosition()).getId());
        mContext.startActivity(intent);
    }

    @Override
    public void shareItemMovie(ResultVH resultVH) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, getItem(resultVH.getAdapterPosition()).getTitle());
        intent.putExtra(Intent.EXTRA_SUBJECT, getItem(resultVH.getAdapterPosition()).getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, getItem(resultVH.getAdapterPosition()).getTitle() + "\n\n" + getItem(resultVH.getAdapterPosition()).getOverview());
        mContext.startActivity(Intent.createChooser(intent, mContext.getResources().getString(R.string.label_action_share)));

    }
}
