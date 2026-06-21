package com.thughan.android.anr;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

public class BackgroundAnrService extends Service {

    private static final String TAG = "BackgroundAnrService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "BackgroundAnrService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "开始触发后台Service ANR，主线程将阻塞250秒...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        long startTime = SystemClock.elapsedRealtime();
                        SystemClock.sleep(250000);
                        long elapsed = SystemClock.elapsedRealtime() - startTime;
                        Log.e(TAG, "后台Service主线程阻塞结束，耗时: " + elapsed + "ms");
                    }
                });
            }
        }).start();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "BackgroundAnrService onDestroy");
    }
}
