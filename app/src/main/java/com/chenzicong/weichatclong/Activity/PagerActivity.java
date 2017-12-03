package com.chenzicong.weichatclong.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Window;

import com.chenzicong.weichatclong.Adapter.SimpleViewPagerAdapter;
import com.chenzicong.weichatclong.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class PagerActivity extends AppCompatActivity {
    private ViewPager pager = null;
    private SimpleViewPagerAdapter adapter = null;
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private String [] mTitlesStr ={"性感美女","丝袜美女","美女写真","外国美女","内衣美女","街拍美女","美女自拍","美女模特"};
    private String [] mImageTypes ={"xingganmeinv","siwameinv","meinvxiezhen","waiguomeinv","nayimeinv","jiepaimeinv","meinvzipai","meinvmote"};
    private PagerTabStrip mPagerTabStrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pager);
        initView();
        adapter = new SimpleViewPagerAdapter(getSupportFragmentManager(),mFragments,mTitles);
        pager.setAdapter(adapter);

    }
    private void initView() {
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        pager = (ViewPager) findViewById(R.id.ViewPager);
        mPagerTabStrip = (PagerTabStrip) findViewById(R.id.PagerTabStrip);
        //添加fragment
        for (String ImageType:mImageTypes) {
            mFragments.add(new PagerItemFragment(ImageType));
        }
        //添加title
        Collections.addAll(mTitles, mTitlesStr);
           mPagerTabStrip.setDrawFullUnderline(false);
        mPagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mPagerTabStrip.setPadding(20, 5, 20, 5);
        mPagerTabStrip.setBackgroundColor(Color.parseColor("#86BA9C"));

    }
}
