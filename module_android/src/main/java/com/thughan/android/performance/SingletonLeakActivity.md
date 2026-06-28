# 单例持有 Context 内存泄漏演示

## 演示内容

单例持有 Activity Context，单例生命周期 = 应用生命周期，Activity 销毁后单例仍持有它，导致无法被 GC 回收。

## 执行步骤

1. 进入「性能优化」→「单例持有 Context 内存泄漏」界面
2. 点击「运行问题代码」按钮
3. 按返回键退出界面
4. 在 Android Studio 中打开 Profiler → Analyze Memory Usage (Heap Dump)

## 如何验证泄漏

1. 在 Heap Dump 搜索框输入 `SingletonLeakActivity`
2. 点击该实例，查看 References 标签页
3. 引用链中出现 `mContext in SingletonLeakActivity$ProblematicSingleton` → 泄漏

### 预期引用链

```
SingletonLeakActivity@xxx
  └─ mContext in SingletonLeakActivity$ProblematicSingleton  ← 单例持有的 Context
       └─ instance in SingletonLeakActivity$ProblematicSingleton  ← 单例的静态实例
            └─ Index in Object[]           ← GC Root（类加载器）
```

### 关键点

- `ProblematicSingleton` 是静态单例，生命周期与应用相同
- `mContext` 字段存储了 Activity Context
- Activity 销毁后，单例仍然持有引用 → 整个 Activity 及 View 树无法回收
- Retained Size 可达 293KB+

## 泄漏原因

```java
// 静态单例 → 生命周期 = 应用生命周期
private static class ProblematicSingleton {
    private static ProblematicSingleton instance;
    private Context mContext;  // 持有 Activity Context

    void init(Context context) {
        this.mContext = context;  // 传入 Activity Context
    }
}

// 调用：传入 Activity Context
ProblematicSingleton.getInstance().init(this);  // this = Activity
```

## 为什么优化代码不泄漏

```java
// 优化：使用 ApplicationContext
ProblematicSingleton.getInstance().init(getApplicationContext());

// getApplicationContext() 返回 Application 级别的 Context
// Activity 销毁后，Application 仍然存活，不会造成 Activity 泄漏
```

### 验证优化代码

点击「运行优化代码」后：
1. 搜索 `SingletonLeakActivity`
2. 查看 `mContext` 字段
3. 指向 `Application` 而非 Activity → 无泄漏

## 修复方法

```java
// 方法1：init() 内部转换为 Application Context
void init(Context context) {
    this.mContext = context.getApplicationContext();
}

// 方法2：提供 clear() 方法手动释放
void clear() {
    this.mContext = null;
}

// 方法3：使用时传入 Application Context
ProblematicSingleton.getInstance().init(getApplicationContext());  // 而非 this
```

## 对比说明

| | 问题代码 | 优化代码 |
|--|---------|---------|
| 存储的 Context | Activity Context | Application Context |
| Activity 销毁后 | 单例持有 Activity → 泄漏 | 单例持有 Application → 正常 |
| mContext 指向 | SingletonLeakActivity | Application |
| Retained Size | 293KB+ | 无额外持有 |
