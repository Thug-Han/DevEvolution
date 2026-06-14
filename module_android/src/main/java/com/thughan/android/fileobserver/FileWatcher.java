package com.thughan.android.fileobserver;

import android.os.FileObserver;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件监听,这个可以实现有新创建的文件夹也加入监听.
 */
public class FileWatcher {

    private static final String TAG = FileWatcher.class.getSimpleName();
    private final SingleFileObserver mFileObserver;

    private IEventCallback mEventCallback;
    private String mWatchPath;
    private Map<String, SingleFileObserver> mObservers;

    public FileWatcher(String path) {
        mWatchPath = path;
        mFileObserver = new SingleFileObserver(mWatchPath);
        mObservers = new HashMap<>();
    }

    public void startWatching() {
        mObservers.put(mWatchPath, mFileObserver);
        for (SingleFileObserver observer : mObservers.values()) {
            observer.startWatching();
        }
    }

    public void stopWatching() {
        for (SingleFileObserver observer : mObservers.values()) {
            observer.stopWatching();
        }
        mObservers.clear();
        mObservers = null;
    }

    public void setEventCallback(IEventCallback eventCallback) {
        this.mEventCallback = eventCallback;
    }

    public interface IEventCallback {
        void onEvent(int action, String path);
    }

    class SingleFileObserver extends FileObserver {

        private final String mPath;

        public SingleFileObserver(String path) {
            super(path);
            mPath = path;
        }

        @Override
        public void onEvent(int event, @Nullable String path) {
            if (TextUtils.isEmpty(path)) {
                return;
            }
            int action = event & FileObserver.ALL_EVENTS;
            String absolutePath = mPath + "/" + path;
            File file = new File(absolutePath);
            if (action == CLOSE_WRITE && file.isFile() && mEventCallback != null) {
                LogUtils.i(TAG, "onEvent: action = CLOSE_WRITE ;absolutePath= " + absolutePath);
                mEventCallback.onEvent(action, absolutePath);
            } else if (action == CREATE && file.isDirectory()) {
                LogUtils.i(TAG, "onEvent: action = CREATE ;absolutePath= " + absolutePath);
                SingleFileObserver fileObserver = new SingleFileObserver(absolutePath);
                fileObserver.startWatching();
                mObservers.put(absolutePath, fileObserver);
                LogUtils.i(TAG, "onEvent: CREATE = " + mObservers.size());
            } else if (action == DELETE) {
                LogUtils.i(TAG, "onEvent: action = DELETE ;absolutePath= " + absolutePath + ";file.isDirectory()=" + file.isDirectory());
                SingleFileObserver fileObserver = mObservers.get(absolutePath);
                if (fileObserver != null) {
                    fileObserver.stopWatching();
                    mObservers.remove(absolutePath);
                }
                LogUtils.i(TAG, "onEvent: DELETE = " + mObservers.size());
            }
        }
    }

}