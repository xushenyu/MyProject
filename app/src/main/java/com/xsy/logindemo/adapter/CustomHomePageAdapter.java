package com.xsy.logindemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class CustomHomePageAdapter extends FragmentStatePagerAdapter {

    List<Fragment> data;
    List<String> titles;

    public CustomHomePageAdapter(FragmentManager fm, List<Fragment> dataFragment, List<String> aTitles) {
        super(fm);
        data = dataFragment;
        titles = aTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
