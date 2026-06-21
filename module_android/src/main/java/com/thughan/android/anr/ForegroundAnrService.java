package com.thughan.android.anr;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.thughan.android.R;

public class ForegroundAnrService extends Service {

    private static final String TAG = "ForegroundAnrService";
    private static final String CHANNEL_ID = "anr_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "ForegroundAnrService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "开始触发前台Service ANR，主线程将阻塞25秒...");

        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("前台Service ANR示例")
                .setContentText("Service正在运行，主线程阻塞中...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        startForeground(1, notification);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        long startTime = SystemClock.elapsedRealtime();
                        SystemClock.sleep(25000);
                        long elapsed = SystemClock.elapsedRealtime() - startTime;
                        Log.e(TAG, "前台Service主线程阻塞结束，耗时: " + elapsed + "ms");
                    }
                });
            }
        }).start();

        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "ANR示例",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "ForegroundAnrService onDestroy");
        stopForeground(true);
    }
}
