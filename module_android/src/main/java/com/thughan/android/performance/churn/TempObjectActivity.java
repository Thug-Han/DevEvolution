package com.thughan.android.performance.churn;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.thughan.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 性能问题 #7：过多临时对象导致频繁 GC
 */
public class TempObjectActivity extends Activity {

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

        tvTitle.setText("7. 过多临时对象");

        tvProblem.setText("问题代码：\n" +
                "for (int i = 0; i < 10000; i++) {\n" +
                "    String s = new String(\"item\" + i);\n" +
                "    Integer num = new Integer(i);\n" +
                "    list.add(s + num);\n" +
                "}\n\n" +
                "每次循环3个临时对象\n" +
                "10000次=30000个→频繁GC");

        tvDetect.setText("检测方法：\n" +
                "1. Track Memory Consumption\n" +
                "2. 查看GC频率和锯齿状曲线\n" +
                "3. 查看String/Integer创建数量");

        tvFix.setText("修复方法：\n" +
                "1. StringBuilder代替String拼接\n" +
                "2. int代替Integer，无装箱\n" +
                "3. 对象池复用");

        tvOptimize.setText("优化代码：\n" +
                "StringBuilder sb = new StringBuilder();\n" +
                "for (int i = 0; i < 10000; i++) {\n" +
                "    sb.setLength(0);\n" +
                "    sb.append(\"item\").append(i);\n" +
                "    int num = i; // 基本类型\n" +
                "}\n\n" +
                "循环中零临时对象创建");

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
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            String s = new String("item" + i);
            Integer num = new Integer(i);
            String result = s + num;
            list.add(result);
        }
        long end = System.currentTimeMillis();

        tvResult.setText("已执行: 循环10000次\n\n" +
                "每次3个临时对象，共30000个\n\n" +
                "耗时: " + (end - start) + "ms\n\n" +
                "验证: Track Memory Consumption观察锯齿曲线");
    }

    private void runGoodCode() {
        long start = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.setLength(0);
            sb.append("item").append(i);
            int num = i;
            String result = sb.toString();
            list.add(result);
        }
        long end = System.currentTimeMillis();

        tvResult.setText("已执行: 循环10000次\n\n" +
                "零临时对象，StringBuilder复用\n\n" +
                "耗时: " + (end - start) + "ms\n\n" +
                "验证: Track Memory Consumption锯齿曲线消失");
    }

    private void resetState() {
        tvResult.setText("已重置\n\n点击按钮重新运行代码");
    }
}
