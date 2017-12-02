package com.chenzicong.weichatclong;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by ChenZiCong on 2017/11/26.
 */

public class AdapterViewPager extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
