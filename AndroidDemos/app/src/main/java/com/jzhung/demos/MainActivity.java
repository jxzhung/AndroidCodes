package com.jzhung.demos;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private ActivityInfo[] infos;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);

        loadActivityInfos();
        ItemAdapter adapter = new ItemAdapter();
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //buildMenu();
    }

    class ViewHolder {
        LinearLayout listItem;
        ImageView img;
        TextView title;
    }

    class ItemAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return infos.length;
        }

        @Override
        public ActivityInfo getItem(int position) {
            return infos[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.list_item_main, null);
                holder.listItem = (LinearLayout) convertView.findViewById(R.id.list_item);
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.img.setImageResource(android.R.drawable.ic_menu_send);
            final ActivityInfo info = getItem(position);
            String text = (position + 1) + (" " + info.loadLabel(getPackageManager()).toString());
            holder.title.setText(text);
            holder.listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(info.name);
                }
            });

            return convertView;
        }
    }

    private void buildMenu() {
        final String packageName = getPackageName();

        for (ActivityInfo info : infos) {
            String actClazz = info.name;
            String tmpStr = actClazz.replace(packageName, "");

            Log.i(TAG, "======>" + tmpStr);
            int level = 1;
            while (tmpStr.contains(".")) {
                int index = tmpStr.indexOf(".");
                tmpStr = tmpStr.substring(index + 1);
                MenuItem item = new MenuItem();
                item.path = tmpStr;

                if (tmpStr.contains(".")) {
                    item.isActivity = false;
                    String nodeName = tmpStr.substring(0, tmpStr.indexOf("."));
                    item.name = nodeName;
                    Log.i(TAG, "buildMenu: CAT: " + nodeName + " level: " + level);
                } else {
                    item.isActivity = true;
                    Log.i(TAG, "buildMenu: ACT: " + tmpStr + " level: " + level);
                }
                level++;
            }
        }

    }

    class MenuItem {
        String name; //节点名
        boolean isActivity; //是否是Activity
        String path; // 全路径
    }

    private void loadActivityInfos() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            infos = packageInfo.activities;
            /*for (ActivityInfo info : infos) {
                activityList.add(info.name);
                Log.i(TAG, info.name);
            }*/
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startActivity(String actFullPath) {
        Log.i(TAG, "startActivity: " + actFullPath);
        final String curName = getClass().getName();
        if (curName.equals(actFullPath)) {
            Toast.makeText(this, "当前界面已启动", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setClassName(getPackageName(), actFullPath);
        startActivity(intent);
    }
}
