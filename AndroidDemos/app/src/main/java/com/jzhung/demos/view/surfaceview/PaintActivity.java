package com.jzhung.demos.view.surfaceview;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Jzhung on 2017/5/16.
 */

public class PaintActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PaintSurfaceView(this));
    }
}
