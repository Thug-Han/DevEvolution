package com.thughan.android.performance.thread;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.thughan.android.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 性能问题 #20：线程池 vs new Thread
 */
public class ThreadPoolActivity extends Activity {

    private TextView tvResult;
    private ExecutorService mPool;

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

        tvTitle.setText("20. 线程池 vs new Thread");

        tvProblem.setText("问题代码：\n" +
                "new Thread(runnable).start();\n\n" +
                "问题:\n" +
                "1. 每次创建/销毁有开销\n" +
                "2. 无法复用线程\n" +
                "3. 无法控制并发数\n" +
                "4. 过多线程导致OOM");

        tvDetect.setText("检测方法：\n" +
                "1. Find CPU Hotspots查看线程\n" +
                "2. 线程数持续增长→有问题\n" +
                "3. 线程数>50需关注");

        tvFix.setText("修复方法：\n" +
                "1. ExecutorService线程池\n" +
                "2. 自定义ThreadPoolExecutor\n" +
                "3. Kotlin协程");

        tvOptimize.setText("优化代码：\n" +
                "ExecutorService pool =\n" +
                "    Executors.newFixedThreadPool(4);\n" +
                "pool.execute(() -> doWork());\n\n" +
                "复用线程+控制并发+队列缓冲");

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
        int threadCount = 10;
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        tvResult.setText("已执行: 创建" + threadCount + "个独立线程\n\n" +
                "每个都有创建/销毁开销\n" +
                "无法复用，无法控制并发\n\n" +
                "验证: Find CPU Hotspots观察线程数");
    }

    private void runGoodCode() {
        mPool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            mPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        tvResult.setText("已执行: 固定4线程池+10个任务\n\n" +
                "复用4个线程处理10个任务\n" +
                "创建/销毁开销大幅降低\n\n" +
                "验证: Find CPU Hotspots线程数稳定");
    }

    private void resetState() {
        if (mPool != null) {
            mPool.shutdownNow();
            mPool = null;
        }
        tvResult.setText("已重置: 线程池已关闭");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resetState();
    }
}
