package com.thughan.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.thughan.android.anr.ANRActivity;
import com.thughan.android.crashcatch.CrashActivity;
import com.thughan.android.handlerthread.HandlerThreadActivity;
import com.thughan.android.leakcanary.LeakCanaryActivity;
import com.thughan.android.performance.PerformanceHubActivity;

@Route(path = ModuleConstants.ACTIVITY_PATH)
public class AndroidHubActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_hub);

        Button btnLeakCanary = findViewById(R.id.btn_leak_canary);
        Button btnHandlerThread = findViewById(R.id.btn_handler_thread);
        Button btnCrash = findViewById(R.id.btn_crash);
        Button btnAnr = findViewById(R.id.btn_anr);
        Button btnPerformance = findViewById(R.id.btn_performance);

        btnLeakCanary.setOnClickListener(this);
        btnHandlerThread.setOnClickListener(this);
        btnCrash.setOnClickListener(this);
        btnAnr.setOnClickListener(this);
        btnPerformance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_leak_canary) {
            startActivity(new Intent(this, LeakCanaryActivity.class));
        } else if (id == R.id.btn_handler_thread) {
            startActivity(new Intent(this, HandlerThreadActivity.class));
        } else if (id == R.id.btn_crash) {
            startActivity(new Intent(this, CrashActivity.class));
        } else if (id == R.id.btn_anr) {
            startActivity(new Intent(this, ANRActivity.class));
        } else if (id == R.id.btn_performance) {
            startActivity(new Intent(this, PerformanceHubActivity.class));
        }
    }
}
