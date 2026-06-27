package com.thughan.android.performance.memory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.thughan.android.R;

import java.lang.ref.WeakReference;

/**
 * 性能问题 #1：静态引用导致内存泄漏
 */
public class StaticRefLeakActivity extends Activity {

    private static Activity sActivity;
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

        tvTitle.setText("1. 静态引用内存泄漏");

        tvProblem.setText("问题代码：\n" +
                "private static Activity sActivity;\n" +
                "onCreate: sActivity = this;\n\n" +
                "静态变量生命周期=应用生命周期\n" +
                "Activity销毁后sActivity仍持有它");

        tvDetect.setText("检测方法：\n" +
                "1. 点击 Analyze Memory Usage\n" +
                "2. 搜索 StaticRefLeakActivity\n" +
                "3. 查看 GC Root 引用链\n" +
                "4. 销毁后仍有实例→泄漏");

        tvFix.setText("修复方法：\n" +
                "1. 不要用 static 持有 Activity\n" +
                "2. 用 WeakReference<Activity>\n" +
                "3. onDestroy: sActivity = null");

        tvOptimize.setText("优化代码：\n" +
                "private static WeakReference<Activity> sRef;\n\n" +
                "onCreate: sRef = new WeakReference<>(this);\n" +
                "onDestroy: sRef.clear();\n\n" +
                "WeakReference不阻止GC回收\n" +
                "get()可能返回null，需判空");

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
        sActivity = this;
        tvResult.setText("已执行: sActivity = this\n\n" +
                "静态变量持有Activity引用\n" +
                "销毁后GC无法回收\n\n" +
                "验证: Analyze Memory Usage\n" +
                "搜索StaticRefLeakActivity查看实例");
    }

    private void runGoodCode() {
        WeakReference<Activity> safeRef = new WeakReference<>(this);
        tvResult.setText("已执行: WeakReference<Activity>\n\n" +
                "WeakReference不阻止GC回收\n" +
                "Activity销毁后可被正常回收\n\n" +
                "验证: Analyze Memory Usage\n" +
                "实例数应为0");
    }

    private void resetState() {
        sActivity = null;
        tvResult.setText("已重置: sActivity = null\n\n" +
                "静态引用已清空，Activity可被GC回收");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
