#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "NativeLib"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C" {

JNIEXPORT void JNICALL
Java_com_thughan_jni_JniHelper_nativeHello(JNIEnv *env, jclass clazz) {
    LOGI("Hello from native C++!");
}

JNIEXPORT jint JNICALL
Java_com_thughan_jni_JniHelper_nativeAdd(JNIEnv *env, jclass clazz, jint a, jint b) {
    LOGI("nativeAdd: %d + %d = %d", a, b, a + b);
    return a + b;
}

JNIEXPORT jstring JNICALL
Java_com_thughan_jni_JniHelper_nativeGetString(JNIEnv *env, jclass clazz) {
    std::string hello = "Hello from C++ JNI!";
    LOGI("nativeGetString: %s", hello.c_str());
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT void JNICALL
Java_com_thughan_jni_JniHelper_nativePrintArray(JNIEnv *env, jclass clazz, jintArray array) {
    jint *elements = env->GetIntArrayElements(array, nullptr);
    jsize length = env->GetArrayLength(array);
    LOGI("nativePrintArray: length = %d", length);
    for (int i = 0; i < length; i++) {
        LOGI("  array[%d] = %d", i, elements[i]);
    }
    env->ReleaseIntArrayElements(array, elements, 0);
}

JNIEXPORT jlong JNICALL
Java_com_thughan_jni_JniHelper_nativeFactorial(JNIEnv *env, jclass clazz, jint n) {
    long long result = 1;
    for (int i = 2; i <= n; i++) {
        result *= i;
    }
    LOGI("nativeFactorial(%d) = %lld", n, result);
    return (jlong)result;
}

}
