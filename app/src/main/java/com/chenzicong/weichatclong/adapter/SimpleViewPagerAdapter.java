package com.chenzicong.weichatclong.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ChenZiCong on 2017/12/1.
 */

public class SimpleViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public SimpleViewPagerAdapter(FragmentManager fm , List<Fragment> Fragments, List<String> Titles) {
        super(fm);
        mFragments = Fragments;
        mTitles  = Titles;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
