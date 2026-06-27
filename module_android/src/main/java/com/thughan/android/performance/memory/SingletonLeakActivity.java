package com.thughan.android.performance.memory;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.thughan.android.R;

/**
 * 性能问题 #4：单例持有 Activity Context 导致内存泄漏
 */
public class SingletonLeakActivity extends AppCompatActivity {

    private static class ProblematicSingleton {
        private static ProblematicSingleton instance;
        private Context mContext;

        private ProblematicSingleton() {}

        static ProblematicSingleton getInstance() {
            if (instance == null) {
                instance = new ProblematicSingleton();
            }
            return instance;
        }

        void init(Context context) {
            this.mContext = context;
        }

        void clear() {
            this.mContext = null;
        }
    }

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

        tvTitle.setText("4. 单例持有 Context 内存泄漏");

        tvProblem.setText("问题代码：\n" +
                "private static Singleton instance;\n" +
                "private Context mContext;\n\n" +
                "void init(Context ctx) { mContext = ctx; }\n" +
                "Singleton.init(this); // 传入Activity Context\n\n" +
                "单例生命周期=应用生命周期\n" +
                "Activity销毁后单例仍持有它");

        tvDetect.setText("检测方法：\n" +
                "1. Analyze Memory Usage\n" +
                "2. 搜索 ProblematicSingleton\n" +
                "3. 查看mContext指向的对象\n" +
                "4. 指向已销毁Activity→泄漏");

        tvFix.setText("修复方法：\n" +
                "1. 单例中使用ApplicationContext\n" +
                "2. 或提供clear()方法释放");

        tvOptimize.setText("优化代码：\n" +
                "void init(Context ctx) {\n" +
                "    mContext = ctx.getApplicationContext();\n" +
                "}\n\n" +
                "Singleton.init(this);\n" +
                "→ 存储Application Context\n" +
                "→ Activity可被正常GC回收");

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
        ProblematicSingleton.getInstance().init(this);
        tvResult.setText("已执行: Singleton.init(this)\n\n" +
                "单例持有Activity Context引用\n" +
                "销毁后单例仍持有它\n\n" +
                "验证: Analyze Memory Usage\n" +
                "搜索ProblematicSingleton查看mContext");
    }

    private void runGoodCode() {
        ProblematicSingleton.getInstance().init(getApplicationContext());
        tvResult.setText("已执行: init()内部用getApplicationContext()\n\n" +
                "单例持有Application Context\n" +
                "Activity可被正常GC回收\n\n" +
                "验证: Analyze Memory Usage\n" +
                "mContext指向Application");
    }

    private void resetState() {
        ProblematicSingleton.getInstance().clear();
        tvResult.setText("已重置: Singleton.clear()已调用\n\n" +
                "Context引用已清空");
    }
}
