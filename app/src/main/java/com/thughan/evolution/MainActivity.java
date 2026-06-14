package com.thughan.evolution;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.thughan.android.ModuleConstants;
import com.thughan.designmode.DesignConstants;
import com.thughan.kotlin.KotlinConstants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mDesignMode;
    private Button mLeakCanary;
    private Button mCompose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ARouter.init(getApplication());
        ARouter.openDebug();
        ARouter.openLog();

        mDesignMode = findViewById(R.id.btn_design_mode);
        mLeakCanary = findViewById(R.id.btn_leak_canary);
        mCompose = findViewById(R.id.btn_compose);

        mLeakCanary.setOnClickListener(this);
        mDesignMode.setOnClickListener(this);
        mCompose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_design_mode) {
            ARouter.getInstance().build(DesignConstants.ACTIVITY_PATH).navigation();
        } else if (id == R.id.btn_leak_canary) {
            ARouter.getInstance().build(ModuleConstants.ACTIVITY_PATH).navigation();
        } else if (id == R.id.btn_compose) {
            ARouter.getInstance().build(KotlinConstants.ACTIVITY_COMPOSE_PATH).navigation();
        }
    }
}