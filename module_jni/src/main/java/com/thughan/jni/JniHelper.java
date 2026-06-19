package com.thughan.jni;

import android.util.Log;

public class JniHelper {

    private static final String TAG = "JniHelper";

    static {
        System.loadLibrary("native-lib");
    }

    public static native void nativeHello();

    public static native int nativeAdd(int a, int b);

    public static native String nativeGetString();

    public static native void nativePrintArray(int[] array);

    public static native long nativeFactorial(int n);

    public static void javaMethod() {
        Log.d(TAG, "This is a Java method that can call JNI methods");
        nativeHello();
    }
}
