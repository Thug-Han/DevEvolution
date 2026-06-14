package com.thughan.android.leakcanary;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.thughan.android.ModuleConstants;
import com.thughan.android.R;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

@Route(path = ModuleConstants.ACTIVITY_PATH)
public class LeakCanaryActivity extends AppCompatActivity {

    public static final String TAG = LeakCanaryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);

        main();
    }

    ReferenceQueue<Object> mQueue = new ReferenceQueue<>();

    private void main() {
        Log.e(TAG, "main: ");
        Object obj = new Object() {
            @Override
            protected void finalize() throws Throwable {
                super.finalize();
                printlnQueue("after:");
            }
        };

        WeakReference<Object> reference = new WeakReference<>(obj, mQueue);

        LogUtils.i("这个弱引用是:" + reference);

        Runtime.getRuntime().gc();

        printlnQueue("before:");

        obj = null;

        Runtime.getRuntime().gc();

    }

    private void printlnQueue(String tag) {
        String message;
        message = tag;
        Object obj;
        while ((obj = mQueue.poll()) != null) {
            message = message + obj;
        }
        LogUtils.i(message + "...");
    }

}