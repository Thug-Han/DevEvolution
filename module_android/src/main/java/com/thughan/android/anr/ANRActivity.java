package com.thughan.android.anr;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.thughan.android.R;

/**
 * ANR (Application Not Responding) 示例
 * 演示三种ANR触发方式：
 * 1. Activity ANR：主线程阻塞超过5秒
 * 2. 前台Service ANR：主线程阻塞超过20秒
 * 3. 后台Service ANR：主线程阻塞超过200秒，进程直接被杀
 */
public class ANRActivity extends AppCompatActivity {

    private static final String TAG = "ANRActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

        // Activity ANR 按钮
        Button btnActivityAnr = findViewById(R.id.btn_activity_anr);
        btnActivityAnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ANRActivity.this, "触发Activity ANR，主线程阻塞15秒...", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "触发Activity ANR，开始阻塞主线程...");
                
                // Activity ANR超时时间：5秒（Input Dispatching Timeout）
                long startTime = SystemClock.elapsedRealtime();
                SystemClock.sleep(15000);
                long elapsed = SystemClock.elapsedRealtime() - startTime;
                
                Log.e(TAG, "Activity ANR主线程阻塞结束，耗时: " + elapsed + "ms");
            }
        });

        // 前台Service ANR 按钮
        Button btnForegroundServiceAnr = findViewById(R.id.btn_foreground_service_anr);
        btnForegroundServiceAnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ANRActivity.this, "启动前台Service，等待25秒触发ANR...", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "启动前台Service ANR...");
                
                Intent intent = new Intent(ANRActivity.this, ForegroundAnrService.class);
                startForegroundService(intent);
            }
        });

        // 后台Service ANR 按钮
        Button btnBackgroundServiceAnr = findViewById(R.id.btn_background_service_anr);
        btnBackgroundServiceAnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ANRActivity.this, "启动后台Service，等待250秒触发ANR...", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "启动后台Service ANR...");
                
                Intent intent = new Intent(ANRActivity.this, BackgroundAnrService.class);
                startService(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止所有Service
        stopService(new Intent(this, ForegroundAnrService.class));
        stopService(new Intent(this, BackgroundAnrService.class));
    }
}
