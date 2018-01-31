package com.mursitaffandi.myfavoritemovie;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mursitaffandi.myfavoritemovie.util.Constant;
import com.mursitaffandi.myfavoritemovie.util.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tvSynopsis)
    TextView tvsynopsis;

    @BindView(R.id.tvRate)
    TextView tvuserRating;

    @BindView(R.id.tvReleaseDate)
    TextView tvreleaseDate;

    @BindView(R.id.ivPosterDetail)
    ImageView ivmoviePoster;

    @BindView(R.id.iv_detail_header_parallax)
    ImageView ivheaderBackdrop;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Result dataMovieDB;
    private String sharedTrailerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent hj = getIntent();
        dataMovieDB = hj.getParcelableExtra(Constant.TAG_DETAIL);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            Glide.with(this)
                    .load(Constant.BASE_URL_IMG + Constant.SIZE_IMAGE_BACKDROP + dataMovieDB.getPosterPath())
                    .into(ivheaderBackdrop);
        }
        sharedTrailerTitle = dataMovieDB.getOriginalTitle();

        collapsingToolbarLayout.setTitle(sharedTrailerTitle);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);

        Glide.with(this)
                .load(Constant.BASE_URL_IMG + Constant.SIZE_IMAGE_POSTER + dataMovieDB.getPosterPath())
                .into(ivmoviePoster);

        tvsynopsis.setText(dataMovieDB.getOverview());
        tvuserRating.setText(String.valueOf(dataMovieDB.getVoteAverage()));
        tvreleaseDate.setText(DateTime.getLongDate(dataMovieDB.getReleaseDate()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
