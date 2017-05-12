package com.jzhung.demos.http.server;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jzhung.demos.R;

public class HttpServerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_server);
        Button startBtn = (Button) findViewById(R.id.serverStartBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpServer server = new HttpServer();
                server.startWithNewThread();
            }
        });


    }
}
