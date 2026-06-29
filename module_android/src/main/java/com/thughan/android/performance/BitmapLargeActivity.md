# Bitmap 内存占用过大演示

## 演示内容

直接加载大尺寸 Bitmap 会导致内存占用过高，可能引发 OOM。演示问题代码与优化代码的内存对比。

## 执行步骤

1. 进入「性能优化」→「Bitmap 内存过大」界面
2. 点击「运行问题代码」按钮
3. 在 Android Studio 中打开 Profiler → Analyze Memory Usage (Heap Dump)
4. 点击「重置」按钮，再点击「运行优化代码」
5. 再次抓取 Heap Dump 对比

## 如何验证内存占用

1. 在 Heap Dump 搜索框输入 `Bitmap`
2. 查看 Bitmap 实例的 Native Size 列
3. 点击实例，查看 References 标签页确认引用链

### 预期引用链（问题代码）

```
Bitmap@xxx (Depth 5, Native Size ~16MB)
  └─ mLargeBitmap in BitmapLargeActivity@xxx   ← 成员变量持有
       └─ mBitmap in BitmapDrawable$BitmapState  ← ImageView 内部持有
            └─ mDrawable in ImageView → GC Root
```

### 预期引用链（优化代码）

```
Bitmap@xxx (Depth 5, Native Size ~1MB)
  └─ mOptimizedBitmap in BitmapLargeActivity@xxx
       └─ mBitmap in BitmapDrawable$BitmapState
            └─ mDrawable in ImageView → GC Root
```

## 内存计算

| 配置 | 尺寸 | 计算公式 | 内存占用 |
|------|------|---------|---------|
| ARGB_8888（问题代码） | 2000×2000 | 2000 × 2000 × 4 bytes | ~16MB |
| ARGB_8888（优化代码） | 500×500 | 500 × 500 × 4 bytes | ~1MB |

- ARGB_8888：每像素 4 字节（R/G/B/A 各 8 位）
- 降采样 4 倍：宽高各缩小 4 倍，面积缩小 16 倍

## 问题代码

```java
// 直接创建大尺寸 Bitmap → 内存占用 16MB
mLargeBitmap = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888);
mIvDemo.setImageBitmap(mLargeBitmap);
```

## 优化代码

```java
// 降采样 4 倍 → 内存占用 1MB，减少 16 倍
mOptimizedBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
mIvDemoOptimized.setImageBitmap(mOptimizedBitmap);
```

## 其他优化手段

| 方法 | 内存减少 | 说明 |
|------|---------|------|
| inSampleSize 降采样 | 可配置倍数 | BitmapFactory.Options 设置采样率 |
| RGB_565 替代 ARGB_8888 | 50% | 无透明通道，内存减半 |
| 使用 Glide/Fresco | 自动管理 | 图片库自动回收与缓存 |

## 对比说明

| | 问题代码 | 优化代码 |
|--|---------|---------|
| Bitmap 尺寸 | 2000×2000 | 500×500 |
| Native Size | ~16MB | ~1MB |
| 内存倍数 | 16x | 1x |
| OOM 风险 | 高 | 低 |

## 退出后释放

`onDestroy()` → `resetState()` 会执行：
1. `mLargeBitmap.recycle()` + 置 null
2. `mOptimizedBitmap.recycle()` + 置 null
3. `ImageView.setImageBitmap(null)` 清除引用

两个引用都被清除后，Bitmap 可被 GC 回收。

## Profiler 知识点

### Bitmap 在 Heap Dump 中的显示

- Native Size 列显示的是 Bitmap 像素数据实际占用的 native 内存（不在 Java heap 中）
- Shallow Size 是 Java 对象本身的大小（很小，仅包含对象头和字段引用）
- Retained Size 是对象被 GC 回收后能释放的总内存

### Depth 的含义与计算

Depth 表示从 GC Root 到该对象的最短引用链长度。

GC Root 包括：
- 线程栈帧中的局部变量
- 静态变量
- JNI 全局引用
- 活跃线程

计算规则：
- GC Root = Depth 0
- 每经过一个引用（字段、数组元素）+1
- 取所有路径中最短的那条

示例（Depth = 5）：
```
Depth 0: GC Root（线程栈）
Depth 1: WeakHashMap$Entry
Depth 2: WeakHashMap 内部结构
Depth 3: BitmapLargeActivity
Depth 4: mOptimizedBitmap 字段
Depth 5: Bitmap 对象
```

Depth 越小，说明对象离 GC Root 越近，越难被回收。

### Depth 显示 "-" 的含义

当引用类型是数组元素（如 `Index 1 in Object[]`）时，Depth 显示 `-`。

原因：Depth 是从 GC Root 到对象的最短路径，数组元素是一种特殊引用，Profiler 认为它的深度信息对分析内存泄漏意义不大，所以用 `-` 表示"不适用"。

### 引用链中各字段的含义

| 字段 | 含义 |
|------|------|
| `mLargeBitmap` / `mOptimizedBitmap` | Activity 的成员变量，直接持有 Bitmap |
| `mBitmap in BitmapDrawable$BitmapState` | ImageView 内部通过 Drawable 间接持有 Bitmap |
| `referent in WeakHashMap$Entry` | WeakHashMap 的弱引用，不阻止 GC |
| `referent in Cleaner` | Cleaner 机制的引用，用于 native 内存回收 |
