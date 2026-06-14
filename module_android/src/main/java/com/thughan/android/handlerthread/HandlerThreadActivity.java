package com.thughan.android.handlerthread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.android.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 学习自 https://blog.csdn.net/franky814/article/details/81387000
 * HandlerThread使用.
 *
 * 当系统有多个耗时任务需要执行时，每个任务都会开启个新线程去执行耗时任务，这样会导致系统多次创建和销毁线程，从而影响性能。
 * 为了解决这一问题，Google提出了HandlerThread，HandlerThread本质上是一个线程类，它继承了Thread。
 * HandlerThread有自己的内部Looper对象，可以进行loopr循环。通过获取HandlerThread的looper对象传递给Handler对象，
 * 可以在handleMessage()方法中执行异步任务。创建HandlerThread后必须先调用HandlerThread.start()方法，
 * Thread会先调用run方法，创建Looper对象。当有耗时任务进入队列时，则不需要开启新线程，在原有的线程中执行耗时任务即可，否则线程阻塞。
 * 它在Android中的一个具体的使用场景是IntentService。
 * 由于HandlerThread的run()方法是一个无限循环，因此当明确不需要再使用HandlerThread时，可以通过它的quit或者quitSafely方法来终止线程的执行。
 *
 * HandlerThread优点是异步不会堵塞，减少对性能的消耗。
 * HandlerThread缺点是不能同时继续进行多任务处理，要等待进行处理，处理效率较低。
 * HandlerThread与线程池不同，HandlerThread是一个串队列，背后只有一个线程。
 */

public class HandlerThreadActivity extends AppCompatActivity {

    private static String[] mUrl = {
            "https://img-blog.csdn.net/20160903083245762",
            "https://img-blog.csdn.net/20160903083252184",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083311972",
            "https://img-blog.csdn.net/20160903083319668",
            "https://img-blog.csdn.net/20160903083326871"
    };
    private static Bitmap[] mBitmaps = new Bitmap[7];
    private ImageView mIvImage;
    private HandlerThread mHandlerThread;
    private Handler mMainHandler;
    private Handler mSubHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        mIvImage = findViewById(R.id.iv_image);
        // 创建异步HandlerThread
        mHandlerThread = new HandlerThread("DownloadImage");
        // 必须先开启线程,调用start(),会回调里面的run()方法,最终将Looper.loop()将looper开启起来
        mHandlerThread.start();
        mMainHandler = new Handler(Looper.getMainLooper(), new MainCallback());
        mSubHandler = new Handler(mHandlerThread.getLooper(), new SubCallback());
        for (int i = 0; i < 6; i++) {
            mSubHandler.sendEmptyMessage(i);
        }
    }

    // 该Callback运行在子线程
    class SubCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            LogUtils.i("mHandlerThread.getThreadId() = " + mHandlerThread.getThreadId());
            LogUtils.i("msg.what = " + msg.what);
            Bitmap bitmap = downloadUrlBitmap(mUrl[msg.what]);
            mBitmaps[msg.what] = bitmap;
            mMainHandler.sendEmptyMessage(msg.what);
            return false;
        }
    }

    // 该Callback运行在主线程
    class MainCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            mIvImage.setImageBitmap(mBitmaps[msg.what]);
            LogUtils.i("msg.what = " + msg.what);
            return false;
        }
    }

    private static Bitmap downloadUrlBitmap(String netUrl) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        Bitmap bitmap = null;
        try {
            final URL url = new URL(netUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 调用该方法,Looper对象中的MessageQueue存储的Message将全部被清除
        // mHandlerThread.quitSafely();
        // 页面结束时,退出HandlerThread中无论是不是延迟消息都将被移除,如果想要保障非延迟消息执行的话,那么使用quitSafely()方法.
        mHandlerThread.quit();
    }
}