package com.chenzicong.weichatclong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

/**
 * Created by ChenZiCong on 2017/11/27.
 */

public class FrameSpan extends ReplacementSpan {
    private final Paint mPaint;
    private  int mWidth;
    public FrameSpan() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true); //设置抗锯齿
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, @Nullable Paint.FontMetricsInt fm) {
        mWidth = (int) paint.measureText(text,start,end);
        return mWidth;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        canvas.drawRect(x,top,x+mWidth,bottom,mPaint);
        canvas.drawText(text,start,end,x,y,paint);
    }
}
