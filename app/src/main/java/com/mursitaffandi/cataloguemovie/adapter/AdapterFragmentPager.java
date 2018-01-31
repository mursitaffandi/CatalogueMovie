package com.mursitaffandi.cataloguemovie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mursitaffandi.cataloguemovie.fragment.FavoriteFragment;
import com.mursitaffandi.cataloguemovie.fragment.NowPlayingFragment;
import com.mursitaffandi.cataloguemovie.fragment.UpcomingFragment;

/**
 * Created by mursitaffandi on 11/01/18.
 */

public class AdapterFragmentPager extends FragmentPagerAdapter {
    public AdapterFragmentPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NowPlayingFragment();
            case 1:
                return new UpcomingFragment();
            case 2:
                return new FavoriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
