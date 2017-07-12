package com.jzhung.demos.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Jzhung on 2017/6/20.
 */

public class ScreenSwitchUtils {
    private static final String TAG = ScreenSwitchUtils.class.getSimpleName();
    public static final int OR_BOTTOM_ON_BOTTOM = 0;
    public static final int OR_RIGHT_ON_BOTTOM = 1;
    public static final int OR_TOP_ON_BOTTOM = 2;
    public static final int OR_LEFT_ON_BOTTOM = 3;

    private volatile static ScreenSwitchUtils mInstance;
    private SensorManager sm;
    private OrientationSensorListener listener;
    private Sensor sensor;
    private ScreenOrChangeListener mScreenOrChangeListener;
    private int currentOrientation;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 888:
                    int orientation = msg.arg1;
                    Log.d(TAG, "handleMessage: orientation:" + orientation);

                    if (orientation > 45 && orientation < 135) {
                        if (currentOrientation != 1) {
                            Log.e("test", "右向下");
                            if (mScreenOrChangeListener != null) {
                                mScreenOrChangeListener.onOrientationChange(OR_RIGHT_ON_BOTTOM);
                            }
                            currentOrientation = 1;
                        }
                    } else if (orientation > 135 && orientation < 225) {
                        if (currentOrientation != 2) {
                            Log.e("test", "顶部向下");
                            if (mScreenOrChangeListener != null) {
                                mScreenOrChangeListener.onOrientationChange(OR_TOP_ON_BOTTOM);
                            }
                            currentOrientation = 2;
                        }
                    } else if (orientation > 225 && orientation < 315) {
                        if (currentOrientation != 3) {
                            Log.e("test", "左向下 切换成横屏");
                            if (mScreenOrChangeListener != null) {
                                mScreenOrChangeListener.onOrientationChange(OR_LEFT_ON_BOTTOM);
                            }
                            currentOrientation = 3;
                        }
                    } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {
                        if (currentOrientation != 0) {
                            Log.e("test", "正常 切换成竖屏");
                            if (mScreenOrChangeListener != null) {
                                mScreenOrChangeListener.onOrientationChange(OR_BOTTOM_ON_BOTTOM);
                            }
                            currentOrientation = 0;
                        }
                    }
                    break;
                default:
                    break;
            }

        }
    };

    public interface ScreenOrChangeListener {
        void onOrientationChange(int orientation);
    }

    /**
     * 返回ScreenSwitchUtils单例
     **/
    public static ScreenSwitchUtils init(Context context) {
        if (mInstance == null) {
            synchronized (ScreenSwitchUtils.class) {
                if (mInstance == null) {
                    mInstance = new ScreenSwitchUtils(context);
                }
            }
        }
        return mInstance;
    }

    private ScreenSwitchUtils(Context context) {
        Log.d(TAG, "init orientation listener.");
        // 注册重力感应器,监听屏幕旋转
        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new OrientationSensorListener(mHandler);
    }

    /**
     * 开始监听
     */
    public void start() {
        Log.d(TAG, "start orientation listener.");
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * 停止监听
     */
    public void stop() {
        Log.d(TAG, "stop orientation listener.");
        sm.unregisterListener(listener);
    }

    /**
     * 重力感应监听者
     */
    public class OrientationSensorListener implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        public static final int ORIENTATION_UNKNOWN = -1;

        private Handler rotateHandler;

        public OrientationSensorListener(Handler handler) {
            rotateHandler = handler;
        }

        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y
            // value
            if (magnitude * 4 >= Z * Z) {
                // 屏幕旋转时
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            if (rotateHandler != null) {
                rotateHandler.obtainMessage(888, orientation, 0).sendToTarget();
            }
        }
    }

    public ScreenOrChangeListener getScreenOrChangeListener() {
        return mScreenOrChangeListener;
    }

    public void setScreenOrChangeListener(ScreenOrChangeListener screenOrChangeListener) {
        mScreenOrChangeListener = screenOrChangeListener;
    }
}
