package com.jzhung.demos.view.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * 时钟
 * Created by jzhung on 2017/7/14.
 */

public class ClockView extends View {
    private static final String TAG = "ClockView";
    private Paint mPaint;

    public ClockView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        int width = getWidth();

        int clockWidth = width / 2;
        int wyWidth = 600;
        int hourLen = 50;
        int minLen = 30;

        //画外层圆盘
        canvas.drawCircle(clockWidth, clockWidth, wyWidth, mPaint);

        //画针
        int top = clockWidth - wyWidth;
        for (int r = 0; r < 60; r++) {
            if (r % 5 == 0) {
                mPaint.setStrokeWidth(12);
                canvas.drawLine(clockWidth, top, clockWidth, top + hourLen + mPaint.getStrokeWidth(), mPaint);
            } else {
                mPaint.setStrokeWidth(8);
                canvas.drawLine(clockWidth, top, clockWidth, top + minLen + mPaint.getStrokeWidth(), mPaint);
            }
            canvas.rotate(6, clockWidth, clockWidth);
            Log.i(TAG, "onDraw: " + r);
            canvas.save();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(1400, 1400);
    }
}
