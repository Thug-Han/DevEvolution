package com.thughan.android.performance.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.thughan.android.R;

/**
 * 性能问题 #9：过度绘制（Overdraw）
 */
public class OverdrawActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overdraw_compare);

        TextView tvTitle = findViewById(R.id.tv_title);
        tvResult = findViewById(R.id.tv_result);

        if (tvTitle != null) {
            tvTitle.setText("9. 过度绘制对比");
        }

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
        tvResult.setText("问题代码场景：\n" +
                "多层布局都设置了背景色\n\n" +
                "FrameLayout(白色) → LinearLayout(白色) → FrameLayout(黄色) → LinearLayout(橙色)\n\n" +
                "同一像素被绘制 3-4 次\n" +
                "GPU 负载高，耗电增加\n\n" +
                "验证：开发者选项 → 调试GPU过度绘制 → 观察蓝色/绿色区域");
    }

    private void runGoodCode() {
        tvResult.setText("优化代码场景：\n" +
                "移除不必要的背景色\n\n" +
                "只保留最内层 TextView 的背景\n" +
                "父容器背景设为 transparent\n\n" +
                "每个像素只绘制 1 次\n" +
                "GPU 负载最低\n\n" +
                "验证：开发者选项 → 调试GPU过度绘制 → 颜色从蓝/绿变为无色");
    }

    private void resetState() {
        tvResult.setText("已重置\n\n点击按钮运行代码查看效果");
    }
}
