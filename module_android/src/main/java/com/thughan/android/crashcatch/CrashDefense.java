package com.thughan.android.crashcatch;

import android.os.Handler;
import android.os.Looper;

import com.blankj.utilcode.util.LogUtils;

/**
 * @Date : 2024/3/21
 * @Desc : crash defense
 **/
public class CrashDefense {

    public static final String TAG = CrashDefense.class.getSimpleName();

    private CrashDefense() {
    }

    public static CrashDefense getInstance() {
        return SingletonHolder.SINGLETON;
    }

    private static class SingletonHolder {
        private static final CrashDefense SINGLETON = new CrashDefense();
    }

    public void init() {
        LogUtils.i(TAG, "init");
        Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LogUtils.i(TAG, e.getStackTrace());
            }
        });

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        LogUtils.i(TAG, e.toString());
                    }
                }
            }
        });
    }

    public void dispose() {
        LogUtils.i(TAG, "dispose");
    }

}
