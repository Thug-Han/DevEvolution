package com.thughan.ipc.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.ipc.R;
import com.thughan.ipc.aidl.common.Book;
import com.thughan.ipc.aidl.custom.IBookManager;

import java.util.List;

public class AidlActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = AidlActivity.class.getSimpleName();

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookManager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Button mButtonAdd;
    private Button mButtonFetch;
    private IBookManager mBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        mButtonAdd = findViewById(R.id.button_add);
        mButtonFetch = findViewById(R.id.button_fetch_aidl);

        mButtonAdd.setOnClickListener(this);
        mButtonFetch.setOnClickListener(this);

        Intent intent = new Intent(this, AidlService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_add){
            try {
                mBookManager.addBook(new Book(9,"buy a new book kotlin 89元"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else if (id == R.id.button_fetch_aidl){
            try {
                List<Book> list = mBookManager.getBookList();
                LogUtils.i(TAG, list);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}