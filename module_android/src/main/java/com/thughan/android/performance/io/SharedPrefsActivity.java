package com.thughan.android.performance.io;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.thughan.android.R;

/**
 * 性能问题 #19：SharedPreferences 主线程提交
 */
public class SharedPrefsActivity extends Activity {

    private TextView tvResult;
    private SharedPreferences mSp;

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

        mSp = getSharedPreferences("perf_test", Context.MODE_PRIVATE);

        tvTitle.setText("19. SharedPreferences 主线程提交");

        tvProblem.setText("问题代码：\n" +
                "editor.putString(\"key\", \"value\");\n" +
                "editor.commit(); // 同步写入磁盘！\n\n" +
                "commit()同步等待磁盘写入完成\n" +
                "数据量大时阻塞主线程，甚至ANR");

        tvDetect.setText("检测方法：\n" +
                "1. 开启StrictMode\n" +
                "2. Find CPU Hotspots查看主线程阻塞\n" +
                "3. logcat查看StrictMode违规日志");

        tvFix.setText("修复方法：\n" +
                "1. apply()代替commit()(异步写入)\n" +
                "2. 避免主线程读取大量数据\n" +
                "3. 使用DataStore代替");

        tvOptimize.setText("优化代码：\n" +
                "editor.apply(); // 异步写入，不阻塞\n\n" +
                "apply()加入队列立即返回\n" +
                "不阻塞主线程，无ANR风险");

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
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            SharedPreferences.Editor editor = mSp.edit();
            editor.putString("key_bad" + i, "value" + i);
            editor.commit();
        }
        long end = System.currentTimeMillis();

        tvResult.setText("已执行: commit() 100次\n\n" +
                "耗时: " + (end - start) + "ms\n\n" +
                "每次同步等待磁盘写入\n" +
                "主线程阻塞，可能ANR\n\n" +
                "验证: Find CPU Hotspots查看主线程");
    }

    private void runGoodCode() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            SharedPreferences.Editor editor = mSp.edit();
            editor.putString("key_good" + i, "value" + i);
            editor.apply();
        }
        long end = System.currentTimeMillis();

        tvResult.setText("已执行: apply() 100次\n\n" +
                "耗时: " + (end - start) + "ms\n\n" +
                "apply()异步写入，立即返回\n" +
                "主线程无阻塞\n\n" +
                "验证: Find CPU Hotspots主线程无阻塞");
    }

    private void resetState() {
        SharedPreferences.Editor editor = mSp.edit();
        editor.clear();
        editor.apply();
        tvResult.setText("已重置: SharedPreferences已清空");
    }
}
