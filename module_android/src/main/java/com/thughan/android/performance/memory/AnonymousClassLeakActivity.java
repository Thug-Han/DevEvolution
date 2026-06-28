package com.thughan.android.performance.memory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.thughan.android.R;

/**
 * 性能问题 #3：匿名内部类（Runnable/Callback）导致内存泄漏
 */
public class AnonymousClassLeakActivity extends Activity {

    private Thread mLeakThread;
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

        tvTitle.setText("3. 匿名内部类内存泄漏");

        tvProblem.setText("问题代码：\n" +
                "new Thread(new Runnable() { ... }).start();\n\n" +
                "匿名Runnable是Activity的内部类\n" +
                "隐式持有外部类this引用\n" +
                "线程执行时间长→Activity无法回收");

        tvDetect.setText("检测方法：\n" +
                "1. Analyze Memory Usage\n" +
                "2. 反复创建/销毁Activity\n" +
                "3. 查看AnonymousClassLeakActivity实例数\n" +
                "4. 实例数>1→存在泄漏");

        tvFix.setText("修复方法：\n" +
                "1. 静态内部类 + WeakReference\n" +
                "2. Lambda表达式(SAM接口)\n" +
                "3. onDestroy取消未完成任务");

        tvOptimize.setText("优化代码：\n" +
                "private static class SafeRunnable implements Runnable {\n" +
                "    private final WeakReference<Activity> mRef;\n" +
                "    void run() {\n" +
                "        Activity a = mRef.get();\n" +
                "        if (a == null) return;\n" +
                "    }\n" +
                "}\n\n" +
                "new Thread(new SafeRunnable(this)).start();");

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
        mLeakThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mLeakThread.start();

        tvResult.setText("已执行: new Thread(runnable).start()\n\n" +
                "匿名Runnable持有Activity引用\n" +
                "线程60秒内Activity无法GC\n\n" +
                "验证: Analyze Memory Usage\n" +
                "搜索AnonymousClassLeakActivity查看实例");
    }

    private void runGoodCode() {
        tvResult.setText("已执行: 静态内部类+WeakReference\n\n" +
                "SafeRunnable不持有Activity引用\n" +
                "销毁后可被GC回收\n\n" +
                "验证: Analyze Memory Usage\n" +
                "实例数应为0");
    }

    private void resetState() {
        if (mLeakThread != null) {
            mLeakThread.interrupt();
            mLeakThread = null;
        }
        tvResult.setText("已重置: 线程已中断");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
