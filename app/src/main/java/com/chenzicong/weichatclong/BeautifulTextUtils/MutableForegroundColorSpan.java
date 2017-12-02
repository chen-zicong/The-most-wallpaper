package com.chenzicong.weichatclong.BeautifulTextUtils;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;


/**
 * 一个span
 */
public class MutableForegroundColorSpan extends CharacterStyle
        implements UpdateAppearance {
    public static final String TAG = "MutableForegroundColorSpan";
    private int mColor = Color.WHITE;
    private int mAlpha = 0 ;
    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setColor(mColor);
        tp.setAlpha(mAlpha);
    }
    public int getColor() {
        return mColor;
    }
    public void setColor(int color) {
        this.mColor = color;
    }
    public void setAlpha(int alpha) {
        mAlpha = alpha;
    }
}