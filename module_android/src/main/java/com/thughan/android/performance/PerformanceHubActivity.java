package com.thughan.android.performance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.thughan.android.R;
import com.thughan.android.performance.bitmap.BitmapLargeActivity;
import com.thughan.android.performance.bitmap.BitmapRecycleActivity;
import com.thughan.android.performance.churn.MemoryChurnActivity;
import com.thughan.android.performance.churn.TempObjectActivity;
import com.thughan.android.performance.io.SharedPrefsActivity;
import com.thughan.android.performance.memory.AnonymousClassLeakActivity;
import com.thughan.android.performance.memory.HandlerLeakActivity;
import com.thughan.android.performance.memory.SingletonLeakActivity;
import com.thughan.android.performance.memory.StaticRefLeakActivity;
import com.thughan.android.performance.startup.SlowStartupActivity;
import com.thughan.android.performance.thread.ThreadPoolActivity;
import com.thughan.android.performance.ui.LayoutDepthActivity;
import com.thughan.android.performance.ui.OverdrawActivity;

@Route(path = "/module/performance")
public class PerformanceHubActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_hub);

        int[] btnIds = {
                R.id.btn_static_ref_leak, R.id.btn_handler_leak,
                R.id.btn_anonymous_leak, R.id.btn_singleton_leak,
                R.id.btn_bitmap_large, R.id.btn_bitmap_recycle,
                R.id.btn_temp_object, R.id.btn_memory_churn,
                R.id.btn_overdraw, R.id.btn_layout_depth,
                R.id.btn_slow_startup, R.id.btn_thread_pool,
                R.id.btn_shared_prefs
        };

        for (int id : btnIds) {
            findViewById(id).setOnClickListener(this);
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_static_ref_leak) {
            startActivity(new Intent(this, StaticRefLeakActivity.class));
        } else if (id == R.id.btn_handler_leak) {
            startActivity(new Intent(this, HandlerLeakActivity.class));
        } else if (id == R.id.btn_anonymous_leak) {
            startActivity(new Intent(this, AnonymousClassLeakActivity.class));
        } else if (id == R.id.btn_singleton_leak) {
            startActivity(new Intent(this, SingletonLeakActivity.class));
        } else if (id == R.id.btn_bitmap_large) {
            startActivity(new Intent(this, BitmapLargeActivity.class));
        } else if (id == R.id.btn_bitmap_recycle) {
            startActivity(new Intent(this, BitmapRecycleActivity.class));
        } else if (id == R.id.btn_temp_object) {
            startActivity(new Intent(this, TempObjectActivity.class));
        } else if (id == R.id.btn_memory_churn) {
            startActivity(new Intent(this, MemoryChurnActivity.class));
        } else if (id == R.id.btn_overdraw) {
            startActivity(new Intent(this, OverdrawActivity.class));
        } else if (id == R.id.btn_layout_depth) {
            startActivity(new Intent(this, LayoutDepthActivity.class));
        } else if (id == R.id.btn_slow_startup) {
            startActivity(new Intent(this, SlowStartupActivity.class));
        } else if (id == R.id.btn_thread_pool) {
            startActivity(new Intent(this, ThreadPoolActivity.class));
        } else if (id == R.id.btn_shared_prefs) {
            startActivity(new Intent(this, SharedPrefsActivity.class));
        }
    }
}
