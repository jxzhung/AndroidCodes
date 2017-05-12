package com.jzhung.rxjavademo.util;

import android.os.Build;

import java.lang.reflect.Field;

/**
 * Created by Jzhung on 2016/11/22.
 */

public class BuildUtil {
    private BuildUtil(){
        throw new UnsupportedOperationException("can not instance!!");
    }

    /**
     * 获取构建信息
     * @return
     */
    public static String getAllBuildInfo(){
        Class buildClass = Build.class;
        Field[] fields = buildClass.getDeclaredFields();
        StringBuilder infoBuilder = new StringBuilder();
        try {
            for (Field field : fields) {
                infoBuilder.append(field.getName() + ": " + field.get(field.getName()) + "\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return infoBuilder.toString();
    }
}
