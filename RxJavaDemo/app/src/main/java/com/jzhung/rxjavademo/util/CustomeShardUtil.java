package com.jzhung.rxjavademo.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Field;

/**
 * 自定义Sharedpreference保存位置
 */

public class CustomeShardUtil {
    private static final String TAG = "CustomeShardUtil";
    public static final String SHARED_DIR = Environment.getExternalStorageDirectory() + "/padStudy01/UserHome";
    private static boolean isChanged;//是否已改变

    /**
     * 保存在手机里面的文件名
     */
    private static final String DEFAULT_FILE_NAME = "default_share_data";


    /**
     * 保存参数, 默认文件是 SHARED_DIR + DEFAULT_FILE_NAME
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {
        setParam(DEFAULT_FILE_NAME, context, key, object);
    }

    /**
     * 获取数据, 默认文件是 SHARED_DIR + DEFAULT_FILE_NAME
     *
     * @param context
     * @param key
     * @param object
     */
    public static Object getParam(Context context, String key, Object object) {
        return getParam(DEFAULT_FILE_NAME, context, key, object);
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param configFile
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(String configFile, Context context, String key, Object object) {
        if (!isChanged) {
            changePreferencesDir(context);
        }

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(configFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param configFile
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(String configFile, Context context, String key, Object defaultObject) {
        if (!isChanged) {
            changePreferencesDir(context);
        }

        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(configFile, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    public static boolean changePreferencesDir(Context context) {
        if (isChanged) {
            return true;
        }
        try {
            Field baseContextImpl;
            // 获取ContextWrapper对象中的mBase变量。该变量保存了ContextImpl对象
            baseContextImpl = ContextWrapper.class.getDeclaredField("mBase");
            baseContextImpl.setAccessible(true);
            // 获取mBase变量
            Object obj = baseContextImpl.get(context);
            // 获取ContextImpl。mPreferencesDir变量，该变量保存了数据文件的保存路径
            Field mPrefercecdsDirField = obj.getClass().getDeclaredField("mPreferencesDir");
            mPrefercecdsDirField.setAccessible(true);
            // 创建自定义路径
            File sdir = new File(SHARED_DIR);
            sdir.getParentFile().mkdirs();
            // 修改mPreferencesDir变量的值
            mPrefercecdsDirField.set(obj, sdir);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
