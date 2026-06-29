# Bitmap 未回收导致内存累积演示

## 演示内容

切换图片时未回收旧 Bitmap，导致内存中同时存在多张大图，内存持续增长。

## 执行步骤

1. 进入「性能优化」→「Bitmap 未回收」界面
2. 点击「运行问题代码」按钮 5 次
3. 在 Android Studio 中打开 Profiler → Analyze Memory Usage (Heap Dump)
4. 观察 Bitmap 数量和 Native Size
5. 点击「重置」按钮
6. 点击 Force GC（垃圾桶图标 🗑️）
7. 再次抓取 Heap Dump 对比

## 如何验证内存累积

1. 在 Heap Dump 搜索框输入 `Bitmap`
2. 查看 Bitmap 实例数量和 Native Size 列
3. 点击实例，查看 References 标签页确认引用链

### 预期引用链（问题代码，点击 5 次后）

```
Bitmap@xxx (Native Size ~4MB)
  └─ Index N in Object[]@xxx   ← mBitmapList 持有引用
       └─ BitmapRecycleActivity → GC Root

（共 5 个这样的 Bitmap，总计 ~20MB）
```

### 重置 + Force GC 后

```
5 个大 Bitmap 消失，只剩系统 UI 的小 Bitmap（~367KB）
```

## 问题代码

```java
// 不回收旧的，直接创建新的
private void runBadCode() {
    mCurrentBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
    mBitmapList.add(mCurrentBitmap);  // 用 List 保存所有引用，阻止 GC 回收
    mImageView.setImageBitmap(mCurrentBitmap);
}
```

**原始代码缺陷：** 只用单变量 `mCurrentBitmap` 保存引用，每次覆盖后旧 Bitmap 失去引用，GC 可回收，无法真正演示"多张大图共存"。

**修复方案：** 新增 `List<Bitmap> mBitmapList` 保存所有创建的 Bitmap，阻止 GC 回收。

## 优化代码

```java
// 先回收旧的，再创建新的
private void runGoodCode() {
    if (mCurrentBitmap != null && !mCurrentBitmap.isRecycled()) {
        mCurrentBitmap.recycle();
    }
    mBitmapList.clear();
    mCurrentBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
    mBitmapList.add(mCurrentBitmap);
    mImageView.setImageBitmap(mCurrentBitmap);
}
```

## 对比说明

| | 问题代码（点击 5 次） | 优化代码（点击 5 次） |
|--|---------|---------|
| Bitmap 数量 | 5 个 | 1 个 |
| 单个大小 | 4MB | 4MB |
| 总内存 | ~20MB | ~4MB |
| 内存趋势 | 持续增长 | 稳定 |

## 退出后释放

`onDestroy()` → `resetState()` 会执行：
1. 遍历 `mBitmapList` 逐个 `recycle()`
2. `mBitmapList.clear()` 清空列表
3. `mCurrentBitmap = null`
4. `ImageView.setImageBitmap(null)` 清除引用

Activity 销毁后引用链断开，GC 可回收所有对象。

## Profiler 知识点

### recycle() 的作用

调用 `bitmap.recycle()` 后：
- `mRecycled = true` — 标记为已回收
- Native 内存被释放
- Java 对象仍在，等待 GC 回收

### 为什么重置后 Profiler 仍显示 Bitmap

`recycle()` 只是标记和释放 native 内存，Java 对象需要 GC 才能彻底清除。验证步骤：
1. 点击「重置」— recycle 标记
2. 点击 Force GC（垃圾桶图标）— 触发垃圾回收
3. 再抓 Heap Dump — Java 对象已被回收

### Force GC 按钮位置

Memory Profiler 右上角工具栏，垃圾桶图标 🗑️，点击后立即触发一次垃圾回收。

### Native Size 变化验证

| 阶段 | Native Size | 说明 |
|------|-------------|------|
| 点击 5 次问题代码 | ~20MB | 5 × 4MB |
| 重置后（未 GC） | ~20MB | recycle 已标记，但对象未回收 |
| Force GC 后 | ~367KB | 5 个大 Bitmap 已被回收 |

### 为什么 Activity 退出后没有泄漏

`onDestroy()` 调用 `resetState()` 清理了所有引用：
- `mBitmapList.clear()` — 列表不再持有 Bitmap
- `mCurrentBitmap = null` — 字段不再持有 Bitmap

Activity 销毁后，这些字段随 Activity 一起被 GC 回收，不存在泄漏。

### 原始代码缺陷分析

原始 `runBadCode()` 只用单变量保存引用：
```java
mCurrentBitmap = Bitmap.createBitmap(...); // 旧引用被覆盖
```

旧 Bitmap 失去引用后，GC 可以随时回收它，无法累积。修复方案是用 `List<Bitmap>` 保存所有引用，模拟真实场景中多张图片共存的情况。
