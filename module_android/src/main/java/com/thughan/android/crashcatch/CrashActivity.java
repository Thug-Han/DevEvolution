package com.thughan.android.crashcatch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.thughan.android.R;
import com.thughan.android.crashcatch.crashmaker.CrashFactory;
import com.thughan.android.crashcatch.crashmaker.IProduct;
import com.thughan.android.crashcatch.crashmaker.crash.ConcurrentCrash;
import com.thughan.android.crashcatch.crashmaker.crash.NPECrash;
import com.thughan.android.crashcatch.crashmaker.crash.OutOfBoundCrash;

import java.util.Random;

/**
 * Android 崩溃捕获机制.
 */
public class CrashActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = CrashActivity.class.getSimpleName();
    private Button mNullPoint;
    private Button mOutOfBound;
    private Button mConcurrent;
    private Button mShowRandomBtn;
    private TextView mShowRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        CrashDefense.getInstance().init();

        mNullPoint = findViewById(R.id.null_point);
        mOutOfBound = findViewById(R.id.array_out_of_bound);
        mConcurrent = findViewById(R.id.concurrent);
        mShowRandomBtn = findViewById(R.id.show_random_btn);
        mShowRandom = findViewById(R.id.show_random);

        mNullPoint.setOnClickListener(this);
        mOutOfBound.setOnClickListener(this);
        mConcurrent.setOnClickListener(this);
        mShowRandomBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CrashDefense.getInstance().dispose();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.null_point) {
            Toast.makeText(this, "应用崩溃了,但是依然可以使用", Toast.LENGTH_SHORT).show();
            CrashFactory crashFactory = new CrashFactory();
            IProduct crash = crashFactory.createProduct(NPECrash.class);
            crash.triggerCrash();
        } else if (id == R.id.array_out_of_bound) {
            CrashFactory crashFactory = new CrashFactory();
            IProduct crash = crashFactory.createProduct(OutOfBoundCrash.class);
            crash.triggerCrash();
        } else if (id == R.id.concurrent) {
            CrashFactory crashFactory = new CrashFactory();
            IProduct crash = crashFactory.createProduct(ConcurrentCrash.class);
            crash.triggerCrash();
        } else if (id == R.id.show_random_btn) {
            Random random = new Random();
            random.nextInt(1000);
            mShowRandom.setText(random.nextInt(1000) + "");
        }
    }

}