package com.mursitaffandi.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mursitaffandi.cataloguemovie.BuildConfig;
import com.mursitaffandi.cataloguemovie.DetailActivity;
import com.mursitaffandi.cataloguemovie.util.Constant;
import com.mursitaffandi.cataloguemovie.R;
import com.mursitaffandi.cataloguemovie.model.Result;
import com.mursitaffandi.cataloguemovie.util.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterResultMovies extends RecyclerView.Adapter<ResultVH> implements ResultVH.VHEvent{
    private Context mContext;
    private List<Result> resultList;

    public AdapterResultMovies(Context mContext, List<Result> resultList) {
        this.mContext = mContext;
        this.resultList = resultList;
    }

    @Override
    public ResultVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, null);
        return new ResultVH(view, this);
    }

    @Override
    public void onBindViewHolder(ResultVH holder, int position) {
        //if (resultList == null && resultList.size() == 0) return;
        Glide.with(mContext).load(BuildConfig.BASE_URL_IMG + Constant.SIZE_IMAGE_POSTER + resultList.get(position).getPosterPath()).into(holder.imageView);
        holder.tv_title.setText(resultList.get(position).getOriginalTitle());
        holder.tv_overview.setText(resultList.get(position).getOverview());
        holder.tv_release.setText(DateTime.getLongDate(resultList.get(position).getReleaseDate()));

    }

    @Override
    public int getItemCount() {
        return (resultList != null) ? resultList.size() : 0;
    }


    public void swap(List<Result> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (resultList != null && resultList.size() > 0)
            resultList.clear();
        this.resultList = datas;
        notifyDataSetChanged();
    }

    @Override
    public void postItemClick(ResultVH resultVH) {
        final Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(Constant.TAG_DETAIL, resultList.get(resultVH.getAdapterPosition()).getId());
        mContext.startActivity(intent);
    }

    @Override
    public void shareItemMovie(ResultVH resultVH) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, resultList.get(resultVH.getAdapterPosition()).getTitle());
        intent.putExtra(Intent.EXTRA_SUBJECT, resultList.get(resultVH.getAdapterPosition()).getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, resultList.get(resultVH.getAdapterPosition()).getTitle() + "\n\n" + resultList.get(resultVH.getAdapterPosition()).getOverview());
        mContext.startActivity(Intent.createChooser(intent, mContext.getResources().getString(R.string.label_action_share)));
    }

}
