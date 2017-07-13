package com.jzhung.demos.view.basic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jzhung on 2017/7/12.
 */

public class FreeDrawView extends View {
    private static final String TAG = "FreeDrawView";

    private Paint forePaint;
    private Paint paint;
    private PathMeasure mPathMeasure;
    private Path mPath;

    public FreeDrawView(Context context) {
        super(context);
        Log.i(TAG, "FreeDrawView: 构造器");
        init();
    }

    private void init() {
        forePaint = new Paint();
        forePaint.setColor(Color.RED);
        forePaint.setStrokeWidth(20);
        forePaint.setStyle(Paint.Style.STROKE);


        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);

        mPathMeasure = new PathMeasure(mPath, false);
        mPath.lineTo(600, 600);
    }

    public FreeDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "FreeDrawView: 构造器2");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: 绘制");
        canvas.drawColor(Color.GRAY);
        canvas.drawRect(100, 100, 800, 800, paint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG, "onAttachedToWindow: 依附到Window");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "onDetachedFromWindow: 脱离Window");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout: 布局完毕");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: 测量宽高");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: 触摸事件");
        return super.onTouchEvent(event);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        Log.i(TAG, "onDrawForeground: 绘制前景");
        super.onDrawForeground(canvas);
        canvas.drawRect(100, 100, 900, 900, forePaint);
    }

}
