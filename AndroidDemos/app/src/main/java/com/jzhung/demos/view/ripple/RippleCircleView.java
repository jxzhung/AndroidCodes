package com.jzhung.demos.view.ripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.socks.library.KLog;

/**
 * 带涟漪波纹的按钮
 * Created by Jzhung on 2017/4/17.
 */

public class RippleCircleView extends View {
    private Context mContext;
    private Paint mCriclePaint;
    private Paint mRipplePaint;
    private int mRadius; //半径
    private int maxRadius;//
    private int centerX;
    private int centerY;

    public RippleCircleView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public RippleCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }


    private void init() {
        mCriclePaint = new Paint();
        mCriclePaint.setColor(Color.BLUE);
        mCriclePaint.setStrokeWidth(2);
        mCriclePaint.setStyle(Paint.Style.FILL);
        mCriclePaint.setAntiAlias(true);

        mRipplePaint = new Paint();
        mRipplePaint.setColor(Color.parseColor("#8d4afb"));
        mRipplePaint.setStrokeWidth(15);
        mRipplePaint.setStyle(Paint.Style.STROKE);
        mRipplePaint.setAntiAlias(true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        KLog.d("width:" + getWidth() + " Height:" + getHeight());
        //半径
        maxRadius = getWidth() / 2;

        //圆心坐标
        centerX = getLeft() + maxRadius;
        centerY = getTop() + (getBottom() / 2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //重置半径
        mRadius = mRadius + 10;
        if (mRadius > maxRadius) {
            mRadius = 0;
        }

        canvas.drawColor(Color.BLACK);


        //画圆
        for (int i = 0; i < 8; i++) {
            int curRadius = mRadius - i * 60;
            //更新透明度
            int alpha = (int) (255 * (1 - (float) curRadius / maxRadius));
            mRipplePaint.setAlpha(alpha);
            canvas.drawCircle(centerX, centerY, curRadius, mRipplePaint);

        }

        //重复执行
        postInvalidateDelayed(100);
    }
}
