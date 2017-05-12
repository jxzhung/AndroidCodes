package com.jzhung.demos.view.ripple;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.jzhung.demos.R;

/**
 * 水波纹圆形
 */
public class RippleCircleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ripple_circle);
         addToContent();
        //addToWindow();
    }

    private void addToContent() {
        RippleCircleView rippleCircleView = new RippleCircleView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(rippleCircleView, params);
    }

    private void addToWindow() {
        RippleCircleView rippleCircleView = new RippleCircleView(this);
        WindowManager.LayoutParams params1 = new WindowManager.LayoutParams();
        getWindow().getWindowManager().addView(rippleCircleView, params1);
    }
}
