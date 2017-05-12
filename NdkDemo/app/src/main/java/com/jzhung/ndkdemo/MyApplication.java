package com.jzhung.ndkdemo;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Jzhung on 2017/2/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
