package com.thughan.android.performance.bitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thughan.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 性能问题 #6：Bitmap 未回收导致内存泄漏
 */
public class BitmapRecycleActivity extends Activity {

    private ImageView mImageView;
    private Bitmap mCurrentBitmap;
    private List<Bitmap> mBitmapList = new ArrayList<>();
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_compare);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvProblem = findViewById(R.id.tv_problem);
        TextView tvDetect = findViewById(R.id.tv_detect);
        TextView tvFix = findViewById(R.id.tv_fix);
        TextView tvOptimize = findViewById(R.id.tv_optimize);
        tvResult = findViewById(R.id.tv_result);
        mImageView = findViewById(R.id.iv_demo);

        tvTitle.setText("6. Bitmap 未回收");

        tvProblem.setText("问题代码：\n" +
                "// 切换图片时，旧的 Bitmap 没有 recycle\n" +
                "Bitmap bmp2 = BitmapFactory.decodeResource(...);\n" +
                "imageView.setImageBitmap(bmp2);\n\n" +
                "bmp1 仍占用内存，导致内存中同时存在多张大图");

        tvDetect.setText("检测方法：\n" +
                "1. Profiler → Memory\n" +
                "2. 反复切换图片\n" +
                "3. 观察内存曲线是否持续增长");

        tvFix.setText("修复方法：\n" +
                "1. 切换前 recycle 旧 Bitmap\n" +
                "2. 使用图片库自动管理（推荐）");

        tvOptimize.setText("优化后正常代码：\n" +
                "if (mCurrentBitmap != null\n" +
                "        && !mCurrentBitmap.isRecycled()) {\n" +
                "    mCurrentBitmap.recycle();\n" +
                "}\n" +
                "mCurrentBitmap = BitmapFactory\n" +
                "    .decodeResource(...);\n" +
                "imageView.setImageBitmap(mCurrentBitmap);");

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
        // 不回收旧的，直接创建新的，但用 List 保存所有引用阻止 GC
        mCurrentBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        mBitmapList.add(mCurrentBitmap);
        mImageView.setImageBitmap(mCurrentBitmap);
        long memory = mCurrentBitmap.getByteCount() / 1024;

        tvResult.setText("问题代码已执行：\n" +
                "创建新 Bitmap，未回收旧的\n\n" +
                "当前 Bitmap: " + memory + "KB\n" +
                "已创建 " + mBitmapList.size() + " 个，共占用: " + (memory * mBitmapList.size()) + "KB\n\n" +
                "旧 Bitmap 仍占用内存\n" +
                "验证：Profiler → Memory → 观察内存持续增长");
    }

    private void runGoodCode() {
        // 先回收旧的
        if (mCurrentBitmap != null && !mCurrentBitmap.isRecycled()) {
            mCurrentBitmap.recycle();
        }
        mBitmapList.clear();
        mCurrentBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        mBitmapList.add(mCurrentBitmap);
        mImageView.setImageBitmap(mCurrentBitmap);
        long memory = mCurrentBitmap.getByteCount() / 1024;

        tvResult.setText("优化代码已执行：\n" +
                "先回收旧 Bitmap，再创建新的\n\n" +
                "当前 Bitmap: " + memory + "KB\n" +
                "内存中只有 " + mBitmapList.size() + " 个 Bitmap\n\n" +
                "内存稳定，不会持续增长\n" +
                "验证：Profiler → Memory → 内存稳定");
    }

    private void resetState() {
        for (Bitmap bitmap : mBitmapList) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        mBitmapList.clear();
        mCurrentBitmap = null;
        mImageView.setImageBitmap(null);
        tvResult.setText("已重置：所有 Bitmap 已回收，ImageView 已清空");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resetState();
    }
}
