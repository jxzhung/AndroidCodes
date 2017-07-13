package com.jzhung.demos.view.basic;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Jzhung on 2017/7/12.
 */

public class FreeDrawActivty extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FreeDrawView(this));
    }
}
