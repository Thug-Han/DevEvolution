package com.thughan.jni;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = JniConstants.ACTIVITY_PATH)
public class JniActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "JniActivity";
    private TextView mResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        mResultText = findViewById(R.id.tv_result);

        Button btnHello = findViewById(R.id.btn_hello);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnString = findViewById(R.id.btn_string);
        Button btnArray = findViewById(R.id.btn_array);
        Button btnFactorial = findViewById(R.id.btn_factorial);

        btnHello.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnString.setOnClickListener(this);
        btnArray.setOnClickListener(this);
        btnFactorial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_hello) {
            JniHelper.nativeHello();
            mResultText.setText("nativeHello() called, check logcat");
        } else if (id == R.id.btn_add) {
            int result = JniHelper.nativeAdd(10, 20);
            String msg = "nativeAdd(10, 20) = " + result;
            Log.d(TAG, msg);
            mResultText.setText(msg);
        } else if (id == R.id.btn_string) {
            String result = JniHelper.nativeGetString();
            String msg = "nativeGetString() = " + result;
            Log.d(TAG, msg);
            mResultText.setText(msg);
        } else if (id == R.id.btn_array) {
            int[] array = {1, 2, 3, 4, 5};
            JniHelper.nativePrintArray(array);
            mResultText.setText("nativePrintArray([1,2,3,4,5]), check logcat");
        } else if (id == R.id.btn_factorial) {
            long result = JniHelper.nativeFactorial(5);
            String msg = "nativeFactorial(5) = " + result;
            Log.d(TAG, msg);
            mResultText.setText(msg);
        }
    }
}
