# 匿名内部类内存泄漏演示

## 演示内容

匿名内部类（Runnable/Callback）隐式持有外部 Activity 引用，线程执行期间 Activity 无法被 GC 回收。

## 执行步骤

1. 进入「性能优化」→「匿名内部类内存泄漏」界面
2. 点击「运行问题代码」按钮
3. **立即**（60秒内）按返回键退出界面
4. 在 Android Studio 中打开 Profiler → Analyze Memory Usage (Heap Dump)

## 如何验证泄漏

1. 在 Heap Dump 搜索框输入 `AnonymousClassLeakActivity`
2. 如果存在泄漏，会看到实例带 ⚠️ 警告
3. 点击该实例，查看 References 标签页

### 预期引用链

```
AnonymousClassLeakActivity@xxx
  └─ this$0 in AnonymousClassLeakActivity$1  ← 匿名 Runnable 持有 Activity 引用
```

### 关键点

- `AnonymousClassLeakActivity$1` 是 `runBadCode()` 中匿名 Runnable 的编译名
- `this$0` 是编译器为匿名内部类自动生成的字段，指向外部 Activity
- 线程 sleep 60 秒期间，Runnable 被 Thread 持有 → Runnable 持有 Activity → Activity 无法回收
- **必须在 60 秒内抓 Dump**，超时后线程执行完毕，引用链断裂，Activity 可被 GC 回收

## 泄漏原因

```java
// 匿名 Runnable 是 Activity 的内部类 → 隐式持有外部 Activity 引用
mLeakThread = new Thread(new Runnable() {
    @Override
    public void run() {
        Thread.sleep(60000);  // 60秒内 Activity 无法回收
    }
});
mLeakThread.start();
```

## 为什么 60 秒后泄漏消失

```
Thread 执行中: Thread → mRunnable → this$0 → Activity  ← 持有
Thread 完成后: Thread 本身无 GC Root 指向，整个循环不可达  ← 释放
```

循环引用 `Activity → mLeakThread → Runnable → Activity` 在 Thread 完成后变得不可达，GC 可以回收。

## 修复方法

```java
// 1. 静态内部类 + WeakReference
private static class SafeRunnable implements Runnable {
    private final WeakReference<Activity> mRef;

    SafeRunnable(Activity activity) {
        mRef = new WeakReference<>(activity);
    }

    @Override
    public void run() {
        Activity a = mRef.get();
        if (a == null) return;
        // 安全使用 activity
    }
}

// 2. 使用 Lambda 表达式（SAM 接口不持有外部引用）
new Thread(() -> {
    // Lambda 不会隐式持有 Activity 引用
}).start();

// 3. onDestroy 取消未完成任务
@Override
protected void onDestroy() {
    super.onDestroy();
    if (mLeakThread != null) {
        mLeakThread.interrupt();
    }
}
```

## 对比说明

| | 问题代码 | 修复代码 |
|--|---------|---------|
| Runnable 类型 | 匿名内部类 | 静态内部类 / Lambda |
| Activity 引用 | 隐式持有 (this$0) | WeakReference / 无 |
| 泄漏时长 | 线程执行期间（60秒） | 无泄漏 |
| 抓 Dump 时机 | 必须在 60 秒内 | 随时 |
