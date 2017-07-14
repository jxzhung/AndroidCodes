package com.jzhung.demos.view.clock;

import android.app.Activity;
import android.os.Bundle;

public class ClockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ClockView(this));
    }

}
