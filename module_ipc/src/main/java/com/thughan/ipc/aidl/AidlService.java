package com.thughan.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.ipc.aidl.common.Book;
import com.thughan.ipc.aidl.custom.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 */
public class AidlService extends Service {

    public static final String TAG = AidlService.class.getSimpleName();
    private CopyOnWriteArrayList<Book> mBookList;

    public AidlService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList = new CopyOnWriteArrayList<>();
        initData();
    }

    private void initData() {
        Book book1 = new Book(1, "活着");
        Book book2 = new Book(2, "Android 组件化架构");
        Book book3 = new Book(3, "Android 插件化架构");
        Book book4 = new Book(4, "Android 开发艺术探索");
        Book book5 = new Book(5, "Android 进阶指南");
        Book book6 = new Book(6, "kotlin 进阶指南");
        Book book7 = new Book(7, "flutter 指北");
        mBookList.add(book1);
        mBookList.add(book2);
        mBookList.add(book3);
        mBookList.add(book4);
        mBookList.add(book5);
        mBookList.add(book6);
        mBookList.add(book7);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }

    private final IBookManager.Stub mStub = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (book != null) {
                mBookList.add(book);
                LogUtils.i(TAG, "接收到了一个正常对象");
            } else {
                LogUtils.i(TAG, "接收到了一个空对象");
            }
        }
    };
}