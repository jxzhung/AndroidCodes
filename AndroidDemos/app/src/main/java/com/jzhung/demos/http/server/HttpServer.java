package com.jzhung.demos.http.server;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/** 简易HTTP服务器
 * Created by Jzhung on 2017/5/11.
 */

public class HttpServer {
    private static final String TAG = "HttpServer";
    public boolean run = true;
    public static Map<String, String> mimeMap = new HashMap<>();
    static {
        mimeMap.put("png", "image/png");
        mimeMap.put("gif", "image/gif");
        mimeMap.put("js", "application/javascript");
        mimeMap.put("css", "text/css");
        mimeMap.put("html", "text/html");
        mimeMap.put("txt", "text/plain");
        mimeMap.put("ico", "image/x-icon");
        mimeMap.put("jpg", "image/jpeg");
    }

    public void startWithNewThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }).start();
    }

    public void start() {
        try {
            ServerSocket server = new ServerSocket(8888);
            Log.i(TAG, "服务器已启动");
            while(run){
                final Socket socket = server.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        processRequest(socket);
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processRequest(Socket socket) {
        Log.i(TAG, "处理请求：" + socket.getRemoteSocketAddress().toString());
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = br.readLine();
            /*if(line.equals("")){
                socket.close();
                return;
            }*/

            //Log.i(TAG, "请求资源: " + line);

            StringTokenizer st = new StringTokenizer(line, " ");
            st.nextToken();
            String url = st.nextToken();
            Log.i(TAG, "请求资源: " + url);

            /*InputStream is = getClass().getResourceAsStream("/assets" + url);
            BufferedInputStream bin = new BufferedInputStream(is);*/

            PrintStream pw = new PrintStream(socket.getOutputStream());
            pw.println("HTTP/1.1 200 OK");
            //pw.println("Content-type:" + mimeMap.get(url.substring(url.lastIndexOf(".") + 1)));
            pw.println("Content-type: text/html; charset=utf-8");
            pw.println();
            pw.write("Server Started!".getBytes("utf-8"));
            /*int len;
            byte[] buffer = new byte[1024];
            while ((len = bin.read(buffer)) != -1){
                pw.write(buffer, 0, len);
            }*/
            pw.flush();
            pw.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
