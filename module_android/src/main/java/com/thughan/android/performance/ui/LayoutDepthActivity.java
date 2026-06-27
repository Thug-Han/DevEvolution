package com.thughan.android.performance.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.thughan.android.R;

/**
 * 性能问题 #10：布局层级过深
 */
public class LayoutDepthActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_depth_compare);

        TextView tvTitle = findViewById(R.id.tv_title);
        tvResult = findViewById(R.id.tv_result);

        if (tvTitle != null) {
            tvTitle.setText("10. 布局层级对比");
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
                "嵌套 4 层 LinearLayout\n\n" +
                "LinearLayout → LinearLayout → LinearLayout → LinearLayout\n\n" +
                "每层嵌套都会触发一次 measure 和 layout\n" +
                "4 层嵌套 = 4 次 measure + 4 次 layout\n" +
                "层级越深，性能越差\n\n" +
                "验证：Layout Inspector → 查看层级深度");
    }

    private void runGoodCode() {
        tvResult.setText("优化代码场景：\n" +
                "使用 1 层 ConstraintLayout 扁平化\n\n" +
                "只有 1 层嵌套\n" +
                "measure/layout 次数最少\n\n" +
                "ConstraintLayout 可以实现多层嵌套的效果\n" +
                "但内部只需 1 次 measure + 1 次 layout\n\n" +
                "验证：Layout Inspector → 层级深度减少");
    }

    private void resetState() {
        tvResult.setText("已重置\n\n点击按钮运行代码查看效果");
    }
}
