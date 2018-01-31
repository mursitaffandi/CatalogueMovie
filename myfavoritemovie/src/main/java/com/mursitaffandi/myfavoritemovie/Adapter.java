package com.mursitaffandi.myfavoritemovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mursitaffandi.myfavoritemovie.util.Constant;
import com.mursitaffandi.myfavoritemovie.util.DateTime;

/**
 * Created by mursitaffandi on 15/01/18.
 */

public class Adapter extends RecyclerView.Adapter<VHFavorite> implements VHFavorite.VHEvent{
    private Context mContext;
    private Cursor cursor;
    public Adapter(Context mContext) {
        this.mContext = mContext;
    }

    public void swapFavorite(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public VHFavorite onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, null);
        return new VHFavorite(view, this);
    }

    @Override
    public void onBindViewHolder(VHFavorite holder, int position) {
        Result resultList = getItem(position);
        Glide.with(mContext).load(Constant.BASE_URL_IMG + Constant.SIZE_IMAGE_POSTER + resultList.getPosterPath()).into(holder.imageView);
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
    public void postItemClick(VHFavorite VHFavorite) {
        final Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(Constant.TAG_DETAIL, getItem(VHFavorite.getAdapterPosition()));
        mContext.startActivity(intent);
    }

    @Override
    public void shareItemMovie(VHFavorite VHFavorite) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, getItem(VHFavorite.getAdapterPosition()).getTitle());
        intent.putExtra(Intent.EXTRA_SUBJECT, getItem(VHFavorite.getAdapterPosition()).getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, getItem(VHFavorite.getAdapterPosition()).getTitle() + "\n\n" + getItem(VHFavorite.getAdapterPosition()).getOverview());
        mContext.startActivity(Intent.createChooser(intent, mContext.getResources().getString(R.string.label_action_share)));

    }
}
