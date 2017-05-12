package com.jzhung.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jzhung.rxjavademo.util.BuildUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.tv);

        tv.setText(BuildUtil.getAllBuildInfo());

//        String key = "e1";
//        String value = "ce";
//
//        CustomeShardUtil.setParam(getApplicationContext(), key, value);
////        String host = (String) CustomeShardUtil.getParam(getApplicationContext(), "host", "none");
////        Toast.makeText(this, "host:" + host, Toast.LENGTH_SHORT).show();
//
//        String host2 = (String) CustomeShardUtil.getParam(getApplicationContext(), key, "none");
//        Toast.makeText(this, key + ":" + host2, Toast.LENGTH_SHORT).show();
//
//        SharedPreferences sp = getSharedPreferences("default_share_data", MODE_PRIVATE);
//        String okey = sp.getString(key, "yu");
//        Toast.makeText(this, "原生API" + okey, Toast.LENGTH_SHORT).show();
    }

}
