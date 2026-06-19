package com.thughan.evolution;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mKotlin;
    private Button mDesignMode;
    private Button mIpc;
    private Button mAndroid;
    private Button mJni;

    private static final String PATH_KOTLIN = "/kotlin/activity";
    private static final String PATH_DESIGN = "/design/activity";
    private static final String PATH_IPC = "/ipc/activity";
    private static final String PATH_ANDROID = "/module/activity";
    private static final String PATH_JNI = "/jni/activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ARouter.init(getApplication());
        ARouter.openDebug();
        ARouter.openLog();

        mKotlin = findViewById(R.id.btn_kotlin);
        mDesignMode = findViewById(R.id.btn_design_mode);
        mIpc = findViewById(R.id.btn_ipc);
        mAndroid = findViewById(R.id.btn_android);
        mJni = findViewById(R.id.btn_jni);

        mKotlin.setOnClickListener(this);
        mDesignMode.setOnClickListener(this);
        mIpc.setOnClickListener(this);
        mAndroid.setOnClickListener(this);
        mJni.setOnClickListener(this);

        mKotlin.setVisibility(!BuildConfig.MODULE_KOTLIN_DEBUG ? View.VISIBLE : View.GONE);
        mDesignMode.setVisibility(!BuildConfig.MODULE_DESIGN_MODE_DEBUG ? View.VISIBLE : View.GONE);
        mIpc.setVisibility(!BuildConfig.MODULE_IPC_DEBUG ? View.VISIBLE : View.GONE);
        mAndroid.setVisibility(!BuildConfig.MODULE_ANDROID_DEBUG ? View.VISIBLE : View.GONE);
        mJni.setVisibility(!BuildConfig.MODULE_JNI_DEBUG ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_kotlin) {
            ARouter.getInstance().build(PATH_KOTLIN).navigation();
        } else if (id == R.id.btn_design_mode) {
            ARouter.getInstance().build(PATH_DESIGN).navigation();
        } else if (id == R.id.btn_ipc) {
            ARouter.getInstance().build(PATH_IPC).navigation();
        } else if (id == R.id.btn_android) {
            ARouter.getInstance().build(PATH_ANDROID).navigation();
        } else if (id == R.id.btn_jni) {
            ARouter.getInstance().build(PATH_JNI).navigation();
        }
    }
}
