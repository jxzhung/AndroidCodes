package com.jzhung.demos.view.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jzhung on 2017/5/16.
 */

public class PaintSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private static final String TAG = "CameraSurfaceView";
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private boolean mLoop;//继续刷新
    private boolean mRefresh;//是否刷新
    private Canvas mCanvas;

    private Path mPath;
    private float mX;
    private float mY;

    public PaintSurfaceView(Context context) {
        super(context);
        init();
    }

    public PaintSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mPath = new Path();
        mHolder = getHolder();
        mHolder.addCallback(this); // 实现SurfaceHolder.Callback 回调 surfaceCreated surfaceChanged surfaceDestroyed
        mHolder.setFormat(PixelFormat.OPAQUE); //不透明
        this.setFocusable(true);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND); //圆头
        mPaint.setDither(true); //消除拉动，使画面圓滑
        mPaint.setStrokeJoin(Paint.Join.ROUND); //结合方式，平滑

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated: ");
        mLoop = true;
        mRefresh = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged: ");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed: ");
        mLoop = false;
    }

    @Override
    public void run() {
        while (mLoop) {
            if(mRefresh){
                drawView();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 绘制内容
     */
    public void drawView() {
        Log.i(TAG, "drawView: ");
        try {
            mCanvas = mHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
            mRefresh = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                onDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(event);
                break;
            case MotionEvent.ACTION_UP:
                onUp(event);
                break;
            default:
                break;
        }
        //更新绘制
        invalidate();
        return true;
    }

    private void onUp(MotionEvent event) {

    }

    private void onMove(MotionEvent event) {
        mRefresh = true;
        final float x = event.getX();
        final float y = event.getY();

        final float previousX = mX;
        final float previousY = mY;

        final float dx = Math.abs(x - previousX);
        final float dy = Math.abs(y - previousY);

        //两点之间的距离大于等于3时，生成贝塞尔绘制曲线
        if (dx >= 3 || dy >= 3)
        {
            //设置贝塞尔曲线的操作点为起点和终点的一半
            float cX = (x + previousX) / 2;
            float cY = (y + previousY) / 2;

            //二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
            mPath.quadTo(previousX, previousY, cX, cY);

            //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
            mX = x;
            mY = y;
        }

    }

    private void onDown(MotionEvent event) {
        mRefresh = true;
        //隐藏之前的绘制
//        mPath.reset();  //
        float x = event.getX();
        float y = event.getY();

        mX = x;
        mY = y;
        //mPath绘制的绘制起点
        mPath.moveTo(x, y);
    }
}
