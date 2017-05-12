package com.jzhung.demos.view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 贝塞尔曲线
 * Created by Jzhung on 2017/4/17.
 */

public class BezierView extends View {
    private Paint mLinePaint;
    private Context mContext;
    private ArrayList<Point> points = new ArrayList<>();
    private Paint mPointPaint;
    private Path mPath;
    private Rect mPointRect;

    public BezierView(Context context) {
        super(context);
        mContext = context;
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //实例化曲线paint
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setColor(Color.BLUE);
        mLinePaint.setAntiAlias(true);
        //实例化点的paint
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setColor(Color.RED);
        //实例化用到的path,rect
        mPath = new Path();
        mPointRect = new Rect();
        mContext = context;
        //模拟数据
        points.add(new Point(10, 200));
        points.add(new Point(80, 100));
        points.add(new Point(150, 40));
        points.add(new Point(200, 200));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0, j = points.size() - 1; i < j; i++) {
            Point startPoint = points.get(i);
            Point endPoint = points.get(i + 1);
            int cpX = (startPoint.x + endPoint.x) >> 1;
            int cpY1 = startPoint.y, cpY2 = endPoint.y;
            mPath.reset();
            mPath.moveTo(startPoint.x, startPoint.y);
            //连接曲线
            mPath.cubicTo(cpX, cpY1, cpX, cpY2, endPoint.x, endPoint.y);
            canvas.drawPath(mPath, mLinePaint);
            //画点
            canvas.drawCircle(startPoint.x, startPoint.y, 8, mPointPaint);
        }
        Point point = points.get(points.size() - 1);
        canvas.drawCircle(point.x, point.y, 8, mPointPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            //处理touch事件
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                if (touchedPoint(x, y)) {
                    Toast.makeText(mContext, "Clicked a Point", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
        return super.onTouchEvent(event);
    }
    /**
     * @param x
     * @param y
     * @return 是否点击了图表上的点
     */
    private boolean touchedPoint(float x, float y) {
        for (Point point : points) {
            mPointRect.set(point.x - 20, point.y - 20, point.x + 20, point.y + 20);
            if (mPointRect.contains((int) x, (int) y)) {
                return true;
            }
        }
        return false;
    }
}
