package com.chenzicong.weichatclong.Adapter;

import android.content.Context;
import android.icu.util.ULocale;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ChenZiCong on 2017/12/1.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<ULocale.Category> mCategoryList;
    private Context mContext;
    private Fragment mFragment;

    public ViewPagerAdapter(FragmentManager fm , Context context) {
        super(fm);
        mContext =context;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
