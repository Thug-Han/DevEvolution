package com.thughan.android.performance.memory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import com.thughan.android.R;

/**
 * 性能问题 #2：非静态内部类 Handler 导致内存泄漏
 */
public class HandlerLeakActivity extends Activity {

    private LeakyHandler mBadHandler;
    private TextView tvResult;

    private class LeakyHandler extends Handler {
        LeakyHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(android.os.Message msg) {
        }
    }

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

        tvTitle.setText("2. Handler 内存泄漏");

        tvProblem.setText("问题代码：\n" +
                "private Handler mHandler = new Handler() { ... };\n\n" +
                "匿名内部类隐式持有Activity引用\n" +
                "postDelayed消息在MessageQueue排队\n" +
                "销毁时消息未处理→阻止GC回收");

        tvDetect.setText("检测方法：\n" +
                "1. Analyze Memory Usage\n" +
                "2. 搜索 HandlerLeakActivity\n" +
                "3. 查看引用链: MessageQueue→Handler→Activity");

        tvFix.setText("修复方法：\n" +
                "1. 静态内部类 + WeakReference\n" +
                "2. onDestroy: removeCallbacksAndMessages(null)");

        tvOptimize.setText("优化代码：\n" +
                "private static class SafeHandler extends Handler {\n" +
                "    private final WeakReference<Activity> mRef;\n" +
                "    void handleMessage(Message msg) {\n" +
                "        Activity a = mRef.get();\n" +
                "        if (a == null) return;\n" +
                "    }\n" +
                "}\n\n" +
                "onDestroy: mHandler.removeCallbacksAndMessages(null);");

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
        if (mBadHandler == null) {
            mBadHandler = new LeakyHandler(Looper.getMainLooper());
        }
        mBadHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvResult.setText("Runnable执行完毕");
            }
        }, 30000);

        tvResult.setText("已执行: postDelayed(runnable, 30000)\n\n" +
                "LeakyHandler是非静态内部类\n" +
                "消息排队30秒，销毁时阻止GC\n\n" +
                "验证: Analyze Memory Usage\n" +
                "搜索HandlerLeakActivity查看实例");
    }

    private void runGoodCode() {
        tvResult.setText("已执行: 静态内部类+WeakReference\n\n" +
                "SafeHandler不持有Activity引用\n" +
                "销毁后可被GC回收\n\n" +
                "验证: Analyze Memory Usage\n" +
                "实例数应为0");
    }

    private void resetState() {
        if (mBadHandler != null) {
            mBadHandler.removeCallbacksAndMessages(null);
            mBadHandler = null;
        }
        tvResult.setText("已重置: Handler消息已清除");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
