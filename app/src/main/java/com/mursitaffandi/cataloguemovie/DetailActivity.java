package com.mursitaffandi.cataloguemovie;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mursitaffandi.cataloguemovie.db.DBMovieContract;
import com.mursitaffandi.cataloguemovie.db.DBMovieHelper;
import com.mursitaffandi.cataloguemovie.model.movie_detail.MDetail;
import com.mursitaffandi.cataloguemovie.network.PulseDetail;
import com.mursitaffandi.cataloguemovie.presenter.GenerateMDetail;
import com.mursitaffandi.cataloguemovie.util.Constant;
import com.mursitaffandi.cataloguemovie.util.DateTime;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements PulseDetail {

    @BindView(R.id.iv_detail_header_parallax)
    ImageView ivheaderBackdrop;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_poster)
    ImageView ivmoviePoster;
    @BindView(R.id.iv_detail_fav)
    Switch sw_switchfav;
    @BindView(R.id.tv_release_date)
    TextView tvreleaseDate;
    @BindView(R.id.tvRate)
    TextView tvuserRating;
    @BindView(R.id.tv_genres)
    TextView tvgenre;
    @BindView(R.id.tv_budget)
    TextView tvbudget;
    @BindView(R.id.tv_revenue)
    TextView tvrevenue;
    @BindView(R.id.tv_companies)
    TextView tvcompanies;
    @BindView(R.id.tv_countries)
    TextView tvcountries;
    @BindView(R.id.tv_overview)
    TextView tvoverview;
    @BindView(R.id.tv_title_belongs)
    TextView tvBelongs;
    @BindView(R.id.img_poster_belongs)
    ImageView ivBelongs;


    private MDetail dataMovieDB;
    private String sharedTrailerTitle;
    private DBMovieHelper dbMovieHelper;
    private GenerateMDetail generateMDetail;
    private boolean isMovieFavotire = false;
    private boolean isMovieLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent hj = getIntent();
        int id_movie = hj.getIntExtra(Constant.TAG_DETAIL, 0);
        generateMDetail = new GenerateMDetail(this);

        dbMovieHelper = new DBMovieHelper(this);
        dbMovieHelper.open();
        if (dbMovieHelper.checkAvalableMovie(id_movie)) isMovieFavotire = true;
        else isMovieFavotire = false;
        generateMDetail.getMovie(id_movie);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);

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
        if (isMovieLoaded){
            if (sw_switchfav.isChecked()) {
                if (!dbMovieHelper.checkAvalableMovie(dataMovieDB.getId())) {
                    this.save_movie(dataMovieDB);
                }
            } else {
                if (dbMovieHelper.checkAvalableMovie(dataMovieDB.getId())) {
                    dbMovieHelper.deleteDBMovie(dataMovieDB.getId());
                }
            }
        } else {
            if (sw_switchfav.isChecked())
            Toast.makeText(this, getString(R.string.message_toast_detailmovie_unsuccess_load), Toast.LENGTH_SHORT).show();
        }
        dbMovieHelper.close();
        super.onDestroy();
    }

    private void save_movie(MDetail dataMovieDB) {
        ContentValues cv = new ContentValues();
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_ID, dataMovieDB.getId());
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_TITLE, dataMovieDB.getTitle());
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_RELEASEDATE, dataMovieDB.getReleaseDate());
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_OVERVIEW, dataMovieDB.getOverview());
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_POSTERPATH, dataMovieDB.getPosterPath());
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_ORIGINALTITLE, dataMovieDB.getOriginalTitle());
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_VOTEAVERAGE, dataMovieDB.getVoteAverage());
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_ORIGINALLANGUAGE, dataMovieDB.getOriginalLanguage());
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_ADULT, (dataMovieDB.getAdult()) ? 1 : 0);
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_BACKDROPPATH, dataMovieDB.getBackdropPath());
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_VOTECOUNT, dataMovieDB.getVoteCount());
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_VIDEO, (dataMovieDB.getVideo()) ? 1 : 0);
        cv.put(DBMovieContract.MovieColumns.COLUMN_MOVIE_POPULARITY, dataMovieDB.getPopularity());
        dbMovieHelper.insertDBMovie(cv);
    }

    @Override
    public void onReciveMovie(MDetail mResult) {
        this.dataMovieDB = mResult;
        if (isMovieFavotire) sw_switchfav.setChecked(true);
        sharedTrailerTitle = dataMovieDB.getOriginalTitle();
        collapsingToolbarLayout.setTitle(sharedTrailerTitle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            Glide.with(this)
                    .load(BuildConfig.BASE_URL_IMG + Constant.SIZE_IMAGE_BACKDROP + mResult.getPosterPath())
                    .into(ivheaderBackdrop);
        }

        Glide.with(this)
                .load(BuildConfig.BASE_URL_IMG + Constant.SIZE_IMAGE_POSTER + mResult.getPosterPath())
                .into(ivmoviePoster);
        if (mResult.getBelongsToCollection() != null) {
            Glide.with(DetailActivity.this)
                    .load(BuildConfig.BASE_URL_IMG + "w92" + mResult.getBelongsToCollection().getPosterPath())
                    .into(ivBelongs);

            tvBelongs.setText(mResult.getBelongsToCollection().getName());
        }

        tvoverview.setText(mResult.getOverview());
        tvuserRating.setText(String.valueOf(mResult.getVoteAverage()));
        tvreleaseDate.setText(DateTime.getLongDate(mResult.getReleaseDate()));

        String genres = "";
        int sizeGenre = mResult.getGenres().size();
        for (int i = 0; i < sizeGenre; i++) {
            genres += "- " + mResult.getGenres().get(i).getName() + (i + 1 < sizeGenre ? "\n" : "");
        }
        tvgenre.setText(genres);

        tvbudget.setText("$ " + NumberFormat.getIntegerInstance().format(mResult.getBudget()));
        tvrevenue.setText("$ " + NumberFormat.getIntegerInstance().format(mResult.getRevenue()));

        String companies = "";
        int sizeCompanies = mResult.getProductionCompanies().size();
        for (int i = 0; i < sizeCompanies; i++) {
            companies += "- " + mResult.getProductionCompanies().get(i).getName() + (i + 1 < sizeCompanies ? "\n" : "");
        }
        tvcompanies.setText(companies);

        String countries = "";
        int size_countries = mResult.getProductionCountries().size();
        for (int i = 0; i < size_countries; i++) {
            countries += "- " + mResult.getProductionCountries().get(i).getName() + (i + 1 < size_countries ? "\n" : "");
        }
        tvcountries.setText(countries);
        isMovieLoaded = true;
    }

    @Override
    public void onFailMovie(String fail_message) {
Log.e("retrofit", fail_message);
    }
}
