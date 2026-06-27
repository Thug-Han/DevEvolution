package com.thughan.android.performance.startup;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

import com.thughan.android.R;

/**
 * 性能问题 #14-16：启动耗时优化
 */
public class SlowStartupActivity extends Activity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_compare);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvProblem = findViewById(R.id.tv_problem);
        TextView tvDetect = findViewById(R.id.tv_detect);
        TextView tvFix = findViewById(R.id.tv_fix);
        TextView tvOptimize = findViewById(R.id.tv_optimize);
        tvResult = findViewById(R.id.tv_result);

        tvTitle.setText("14-16. 启动耗时优化");

        tvProblem.setText("问题代码(Application.onCreate):\n" +
                "initSDK1(); // 200ms\n" +
                "initSDK2(); // 300ms\n" +
                "initSDK3(); // 150ms\n" +
                "loadData(); // 500ms\n" +
                "总耗时: 1150ms!\n\n" +
                "所有初始化主线程串行执行");

        tvDetect.setText("检测方法：\n" +
                "1. adb shell am start -W查看TotalTime\n" +
                "2. Systrace查看启动阶段\n" +
                "3. 查看主线程阻塞");

        tvFix.setText("修复方法：\n" +
                "1. 延迟初始化\n" +
                "2. 异步并行初始化\n" +
                "3. 按需初始化");

        tvOptimize.setText("优化代码：\n" +
                "ExecutorService pool =\n" +
                "    Executors.newFixedThreadPool(3);\n" +
                "pool.submit(() -> initSDK1());\n" +
                "pool.submit(() -> initSDK2());\n" +
                "pool.submit(() -> initSDK3());\n\n" +
                "总耗时=max(单个最慢)而非sum");

        tvResult.setText("点击按钮运行代码查看效果");

        setupButtons();
    }

    private void setupButtons() {
        Button btnRunBad = findViewById(R.id.btn_run_bad);
        Button btnRunGood = findViewById(R.id.btn_run_good);
        Button btnReset = findViewById(R.id.btn_reset);
        Button btnBack = findViewById(R.id.btn_back);

        btnRunBad.setOnClickListener(v -> runBadCode());
        btnRunGood.setOnClickListener(v -> runGoodCode());
        btnReset.setOnClickListener(v -> resetState());
        btnBack.setOnClickListener(v -> finish());
    }

    private void runBadCode() {
        long start = SystemClock.elapsedRealtime();
        try {
            Thread.sleep(100);
            Thread.sleep(150);
            Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = SystemClock.elapsedRealtime();

        tvResult.setText("已执行: 串行初始化3个SDK\n\n" +
                "SDK1: 100ms + SDK2: 150ms + SDK3: 80ms\n" +
                "总耗时: " + (end - start) + "ms\n\n" +
                "串行=所有任务耗时之和");
    }

    private void runGoodCode() {
        long start = SystemClock.elapsedRealtime();
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = SystemClock.elapsedRealtime();

        tvResult.setText("已执行: 并行初始化3个SDK\n\n" +
                "最慢一个: 150ms\n" +
                "总耗时: " + (end - start) + "ms\n\n" +
                "并行=最慢任务的耗时");
    }

    private void resetState() {
        tvResult.setText("已重置\n\n点击按钮重新运行代码");
    }
}
