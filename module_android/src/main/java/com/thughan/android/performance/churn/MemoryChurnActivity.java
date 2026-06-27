package com.thughan.android.performance.churn;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.thughan.android.R;

/**
 * 性能问题 #8：内存抖动（Memory Churn）
 */
public class MemoryChurnActivity extends Activity {

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

        tvTitle.setText("8. 内存抖动");

        tvProblem.setText("问题代码：\n" +
                "onDraw(Canvas canvas) {\n" +
                "    Paint paint = new Paint(); // 每帧\n" +
                "    Rect rect = new Rect(); // 每帧\n" +
                "    String text = \"Frame\" + count;\n" +
                "}\n\n" +
                "每帧3个对象→1秒180个→频繁GC");

        tvDetect.setText("检测方法：\n" +
                "1. Track Memory Consumption\n" +
                "2. 观察锯齿状曲线\n" +
                "3. 查看onDraw对象分配");

        tvFix.setText("修复方法：\n" +
                "1. 对象创建移到成员变量\n" +
                "2. onDraw中复用已有对象\n" +
                "3. 使用对象池");

        tvOptimize.setText("优化代码：\n" +
                "private Paint mPaint = new Paint();\n" +
                "private Rect mRect = new Rect();\n\n" +
                "onDraw(Canvas canvas) {\n" +
                "    mPaint.setColor(...); // 复用\n" +
                "    canvas.drawText(text, 0, 0, mPaint);\n" +
                "}\n\n" +
                "每帧零对象创建→零GC压力");

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
        for (int i = 0; i < 1000; i++) {
            String text = "Frame" + i;
            Integer count = new Integer(i);
            float[] values = new float[10];
        }
        long end = System.currentTimeMillis();

        tvResult.setText("已执行: 循环1000次\n\n" +
                "每次3个对象，共3000个\n\n" +
                "耗时: " + (end - start) + "ms\n\n" +
                "验证: Track Memory Consumption观察锯齿曲线");
    }

    private void runGoodCode() {
        long start = System.currentTimeMillis();
        float[] reusableValues = new float[10];
        for (int i = 0; i < 1000; i++) {
            String text = "Frame" + i;
            int count = i;
            reusableValues[0] = i;
        }
        long end = System.currentTimeMillis();

        tvResult.setText("已执行: 循环1000次\n\n" +
                "零临时对象，预分配复用\n\n" +
                "耗时: " + (end - start) + "ms\n\n" +
                "验证: Track Memory Consumption锯齿曲线消失");
    }

    private void resetState() {
        tvResult.setText("已重置\n\n点击按钮重新运行代码");
    }
}
