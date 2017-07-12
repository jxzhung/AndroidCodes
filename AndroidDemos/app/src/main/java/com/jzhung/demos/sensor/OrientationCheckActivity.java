package com.jzhung.demos.sensor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.jzhung.demos.R;
import com.jzhung.demos.utils.ScreenSwitchUtils;

/**
 * Created by Jzhung on 2017/6/19.
 */

public class OrientationCheckActivity extends Activity{
    private ScreenSwitchUtils mSwitchUtils;
    private TextView gravityTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);

        gravityTv = (TextView) findViewById(R.id.gravityTv);
        gravityTv.setText("HELLO");
        gravityTv.setTextColor(Color.WHITE);

        mSwitchUtils = ScreenSwitchUtils.init(getApplicationContext());
        mSwitchUtils.setScreenOrChangeListener(new ScreenSwitchUtils.ScreenOrChangeListener() {
            @Override
            public void onOrientationChange(int orientation) {
                switch (orientation){
                    case ScreenSwitchUtils.OR_BOTTOM_ON_BOTTOM:
                        gravityTv.setText("正常");
                        break;
                    case ScreenSwitchUtils.OR_RIGHT_ON_BOTTOM:
                        gravityTv.setText("右在下");
                        break;
                    case ScreenSwitchUtils.OR_TOP_ON_BOTTOM:
                        gravityTv.setText("顶在下");
                        break;
                    case ScreenSwitchUtils.OR_LEFT_ON_BOTTOM:
                        gravityTv.setText("左在下");
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSwitchUtils.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSwitchUtils.stop();
    }
}
