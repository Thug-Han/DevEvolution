# ANR (Application Not Responding) 示例

## 概述

ANR 是 Android 系统检测到应用无响应时触发的机制。本示例演示三种 ANR 类型及其行为差异。

## ANR 类型对比

| 类型 | 超时时间 | Android 14+ 行为 | 进程结果 |
|------|----------|------------------|----------|
| Activity ANR | 5秒 | 不弹对话框，记录日志 | 存活（前台）/ 被杀（后台） |
| 前台 Service ANR | 20秒 | 弹 ANR 对话框 | 存活（有通知保护） |
| 后台 Service ANR | 200秒 | 不弹对话框 | **直接被杀** |

## 测试方法

### 1. Activity ANR

**触发方式：** 点击"触发Activity ANR"按钮

**原理：** 主线程调用 `SystemClock.sleep(15000)` 阻塞 15 秒

**验证命令：**
```bash
# 查看 ANR 日志
adb logcat -d | grep -E "ANR|Davey|Skipped frames"

# 查看 ANR trace（需要 root）
adb shell cat /data/anr/anr_*.txt
```

**预期结果：**
- 日志显示 `Davey! duration=15000ms`
- 日志显示 `Skipped 900 frames!`
- Android 14+ 不弹 ANR 对话框

### 2. 前台 Service ANR

**触发方式：** 点击"触发前台Service ANR"按钮

**原理：** 启动前台 Service，在 Service 中阻塞主线程 25 秒

**验证命令：**
```bash
# 查看 Service 状态
adb shell dumpsys activity services com.thughan.evolution

# 查看 ANR 日志
adb logcat -d | grep -E "ForegroundAnrService|ANR|not responding"
```

**预期结果：**
- Service 启动并显示通知
- 20 秒后触发 ANR，弹出对话框
- 进程存活（前台通知保护）

### 3. 后台 Service ANR

**触发方式：** 点击"触发后台Service ANR"按钮，然后按 Home 键

**原理：** 启动后台 Service，在 Service 中阻塞主线程 250 秒

**验证命令：**
```bash
# 启动后台 Service
adb shell am start -n com.thughan.evolution/com.thughan.android.anr.BackgroundAnrService

# 按 Home 键让应用进入后台
adb shell input keyevent KEYCODE_HOME

# 等待 200+ 秒后检查进程
adb shell ps | grep thughan.evolution

# 查看 ANR 日志
adb logcat -d | grep -E "BackgroundAnrService|ANR|killing|bg anr"
```

**预期结果：**
- 200 秒后进程被系统杀死
- 日志显示 `Killing ... (adj 200): bg anr`
- 进程从 `ps` 列表消失

## ANR Trace 分析

### 获取 Trace 文件

```bash
# 需要 root 权限
adb root
adb shell cat /data/anr/anr_*.txt
```

### Trace 文件结构

```
Subject: Input dispatching timed out (...)

----- pid 5043 at 2026-06-20 12:53:29 -----
Cmd line: com.thughan.evolution

"main" prio=5 tid=1 Sleeping          <- 主线程状态
  at java.lang.Thread.sleep(Native method)
  at android.os.SystemClock.sleep(SystemClock.java:146)
  at com.thughan.android.anr.BackgroundAnrService$1$1.run(BackgroundAnrService.java:35)  <- 问题代码
  at android.os.Handler.handleCallback(Handler.java:995)
  at android.os.Looper.loopOnce(Looper.java:248)
  at android.app.ActivityThread.main(ActivityThread.java:9067)
```

### 关键字段说明

| 字段 | 含义 | 分析要点 |
|------|------|----------|
| "main" tid=1 | 主线程 | ANR 一定发生在主线程 |
| Sleeping | 线程状态 | 调用了 sleep() |
| Waiting | 线程状态 | 等待锁或条件变量 |
| Blocked | 线程状态 | 被其他线程阻塞 |
| at com.xxx.xxx | 调用栈 | **问题代码位置** |
| locked <0x...> | 持有的锁 | 锁的内存地址 |

### 常见 ANR 原因

| 线程状态 | 原因 | 解决方案 |
|----------|------|----------|
| Sleeping | 主线程调用 sleep() | 移到子线程 |
| Waiting | 等待锁/条件变量 | 检查锁竞争 |
| Blocked | 被 synchronized 阻塞 | 减少锁粒度 |
| Native | 执行 native 代码 | 检查 native 耗时操作 |
| Runnable | CPU 密集计算 | 移到子线程 |

## Android 版本差异

| Android 版本 | Activity ANR | 前台 Service ANR | 后台 Service ANR |
|-------------|--------------|------------------|------------------|
| Android 5-13 | 弹对话框 - 用户可选 | 弹对话框 - 用户可选 | 直接杀死 |
| Android 14+ | 不弹对话框 | 弹对话框 | 直接杀死 |

## 厂商差异

| 厂商 | 特殊行为 |
|------|----------|
| 小米 MIUI | 可能延长 ANR 超时，自研应用保活机制 |
| 华为 EMUI | 类似小米，有自己的进程管理 |
| OPPO ColorOS | 可能静默杀死后台 ANR 进程 |
| 原生 Android | 严格遵循 AOSP 行为 |

## 代码说明

- `ANRActivity.java` - 主界面，三个按钮分别触发三种 ANR
- `ForegroundAnrService.java` - 前台 Service，显示通知后阻塞主线程
- `BackgroundAnrService.java` - 后台 Service，直接阻塞主线程
- `activity_anr.xml` - 布局文件

## 注意事项

1. 测试后台 Service ANR 需要等待 200+ 秒
2. Android 14+ 用户触发的 ANR 不再弹对话框
3. 需要 root 权限才能读取 ANR trace 文件
4. 不同厂商的 ANR 行为可能有差异
