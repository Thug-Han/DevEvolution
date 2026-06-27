# Handler 内存泄漏演示

## 演示内容

非静态内部类 Handler 隐式持有外部 Activity 引用，当 MessageQueue 中有未处理的消息时，Activity 无法被 GC 回收。

## 执行步骤

1. 进入「性能优化」→「Handler 内存泄漏」界面
2. 点击「运行问题代码」按钮
3. 立即按返回键退出界面
4. 在 Android Studio 中打开 Profiler → Analyze Memory Usage (Heap Dump)

## 如何验证泄漏

1. 在 Heap Dump 搜索框输入 `HandlerLeakActivity`
2. 如果存在泄漏，会看到 1 个实例（带 ⚠️ 警告）
3. 点击该实例，查看 References 标签页

### 预期引用链

```
HandlerLeakActivity@xxx
  └─ this$0 in LeakyHandler        ← 非静态内部类持有 Activity 引用
       └─ target in Message         ← Message 指向 Handler
            └─ mLast, mMessages in MessageQueue  ← 消息在队列中未处理
                 └─ mQueue in Looper → ActivityThread$H  ← GC Root
```

### 关键点

- `this$0` 是编译器为非静态内部类自动生成的字段，指向外部类实例
- 消息在 MessageQueue 中排队 30 秒，期间 Activity 无法被回收
- Profiler 显示的是 GC Root → 泄漏对象的方向，所以看到的是 `this$0` 而不是 `mBadHandler`

## 泄漏原因

```java
// 非静态内部类 → 隐式持有外部 Activity 引用
private class LeakyHandler extends Handler { ... }

// postDelayed 的 Runnable 也是匿名内部类 → 同样持有 Activity 引用
mBadHandler.postDelayed(new Runnable() {
    public void run() {
        tvResult.setText("...");  // 引用了 Activity 的字段
    }
}, 30000);

// onDestroy 没有清理消息 → 消息留在队列中阻止 GC
@Override
protected void onDestroy() {
    super.onDestroy();
    // 缺少: mBadHandler.removeCallbacksAndMessages(null)
}
```

## 修复方法

```java
// 1. 使用静态内部类 + WeakReference
private static class SafeHandler extends Handler {
    private final WeakReference<Activity> mRef;

    SafeHandler(Activity activity) {
        super(Looper.getMainLooper());
        mRef = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        Activity a = mRef.get();
        if (a == null) return;
        // 安全使用 activity
    }
}

// 2. onDestroy 中清理消息
@Override
protected void onDestroy() {
    super.onDestroy();
    if (mHandler != null) {
        mHandler.removeCallbacksAndMessages(null);
    }
}
```

## 对比说明

| | 问题代码 | 修复代码 |
|--|---------|---------|
| Handler 类型 | 非静态内部类 | 静态内部类 |
| Activity 引用 | 隐式持有 (this$0) | WeakReference |
| onDestroy 清理 | 无 | removeCallbacksAndMessages |
| 泄漏结果 | Activity 无法回收 | Activity 可正常回收 |
