package com.thughan.android.performance.bitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thughan.android.R;

/**
 * 性能问题 #5：Bitmap 内存占用过大
 */
public class BitmapLargeActivity extends Activity {

    private ImageView mIvDemo;
    private ImageView mIvDemoOptimized;
    private TextView tvResult;
    private Bitmap mLargeBitmap;
    private Bitmap mOptimizedBitmap;

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
        mIvDemo = findViewById(R.id.iv_demo);
        mIvDemoOptimized = findViewById(R.id.iv_demo_optimized);

        tvTitle.setText("5. Bitmap 内存过大");

        tvProblem.setText("问题代码：\n" +
                "Bitmap bitmap = BitmapFactory.decodeResource(\n" +
                "    getResources(), R.drawable.large_image);\n" +
                "imageView.setImageBitmap(bitmap);\n\n" +
                "假设图片 4000x3000 像素：\n" +
                "ARGB_8888: 4000*3000*4 = 48MB！\n" +
                "直接加载大图会导致 OOM");

        tvDetect.setText("检测方法：\n" +
                "1. Profiler → Memory → 查看 Bitmap 大小\n" +
                "2. Allocation Tracking → 查看 Bitmap 分配\n" +
                "3. 查看内存曲线是否有大幅增长");

        tvFix.setText("修复方法：\n" +
                "1. inSampleSize 采样压缩\n" +
                "2. RGB_565 代替 ARGB_8888（内存减半）\n" +
                "3. 使用 Glide/Fresco 自动管理");

        tvOptimize.setText("优化后正常代码：\n" +
                "opts.inSampleSize = 4;\n" +
                "Bitmap bitmap = BitmapFactory.decodeResource(\n" +
                "    getResources(), resId, opts);\n\n" +
                "1. inSampleSize=4 → 内存减少16倍\n" +
                "2. RGB_565 → 内存减半\n" +
                "3. 推荐使用 Glide 自动管理");

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
        if (mLargeBitmap != null && !mLargeBitmap.isRecycled()) {
            mLargeBitmap.recycle();
        }
        mLargeBitmap = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888);
        mIvDemo.setImageBitmap(mLargeBitmap);
        long badMemory = mLargeBitmap.getByteCount() / 1024;

        tvResult.setText("问题代码已执行：\n" +
                "创建 2000x2000 ARGB_8888 Bitmap\n\n" +
                "内存占用: " + badMemory + "KB（约" + (badMemory/1024) + "MB）\n\n" +
                "直接加载大图会导致 OOM\n" +
                "验证：Profiler → Memory → 查看 Bitmap 大小");
    }

    private void runGoodCode() {
        if (mOptimizedBitmap != null && !mOptimizedBitmap.isRecycled()) {
            mOptimizedBitmap.recycle();
        }
        mOptimizedBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        mIvDemoOptimized.setImageBitmap(mOptimizedBitmap);
        long goodMemory = mOptimizedBitmap.getByteCount() / 1024;

        tvResult.setText("优化代码已执行：\n" +
                "创建 500x500 ARGB_8888 Bitmap（降采样4倍）\n\n" +
                "内存占用: " + goodMemory + "KB（约" + (goodMemory/1024) + "MB）\n\n" +
                "内存减少 16 倍\n" +
                "验证：Profiler → Memory → 查看 Bitmap 大小");
    }

    private void resetState() {
        if (mLargeBitmap != null && !mLargeBitmap.isRecycled()) {
            mLargeBitmap.recycle();
            mLargeBitmap = null;
        }
        if (mOptimizedBitmap != null && !mOptimizedBitmap.isRecycled()) {
            mOptimizedBitmap.recycle();
            mOptimizedBitmap = null;
        }
        mIvDemo.setImageBitmap(null);
        mIvDemoOptimized.setImageBitmap(null);
        tvResult.setText("已重置：Bitmap 已回收，ImageView 已清空");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resetState();
    }
}
