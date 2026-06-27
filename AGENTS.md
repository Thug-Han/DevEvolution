# AGENTS.md

## Project Overview

Android multi-module learning project (Java + Kotlin). Uses ARouter for inter-module navigation.

## Critical Setup

**JDK 17 required.** Kotlin 1.7.20 kapt is incompatible with JDK 21. If kapt fails, stop the daemon first (`.\gradlew.bat --stop`).

## Build Commands

```bash
./gradlew assembleDebug          # Full build
./gradlew :module_kotlin:assembleDebug  # Single module
./gradlew testDebugUnitTest      # Unit tests only
```

## Module Structure

| Module | Purpose | Language | Annotation Processor |
|--------|---------|----------|---------------------|
| `app` | Main shell | Java (Kotlin plugin) | `annotationProcessor` |
| `module_kotlin` | Kotlin + Compose | Kotlin | `kapt` |
| `module_designmode` | Design patterns | Java | `annotationProcessor` |
| `module_ipc` | IPC demos | Java | `annotationProcessor` |
| `module_android` | Android utils | Java | `annotationProcessor` |
| `module_jni` | JNI demos | Java + C++ | `annotationProcessor` |

## Key Quirks

- **Debug flags** in `versions.gradle`: Set e.g. `ModuleKotlinDebug = true` to build a module as standalone app. When true, the module applies `com.android.application`; when false, `com.android.library`. The launcher intent-filter lives in `src/standalone/AndroidManifest.xml`, dynamically loaded via `sourceSets { debug.manifest.srcFile }`. This avoids manifest merger leaking launcher activities into the main app.
- **Conditional module dependencies** in `app/build.gradle`: Each submodule is included only when its debug flag is false (`if (!flag) { implementation project(...) }`). Setting a flag to true automatically excludes it from `app/build.gradle`.
- **ARouter**: All inter-module navigation uses ARouter paths. Debug mode is enabled in `MainActivity` (`ARouter.openDebug()`). Route constants are in `*Constants.java` files per module.
- **JNI module** (`module_jni`): Requires Android NDK and CMake 3.22.1. Build fails with "CMake not found" if NDK is missing.
- **Kotlin module**: Uses Compose BOM 2024.09.00, Java 11 target. Compose compiler extension version is not explicitly pinned (commented out, defaults from BOM). Still uses deprecated `kotlin-android-extensions` — do not remove without migrating to ViewBinding.
- **Other Java modules** (`module_designmode`, `module_ipc`, `module_android`): Java 1.8 target. Depend on `utilcodex` (BlankJ utility library). `module_jni` does not depend on utilcodex.
- **Versions**: Gradle 8.1, AGP 8.1.0, Kotlin 1.7.20, compileSdk 34, minSdk 21, targetSdk 34.
- **`local.properties`**: Contains machine-specific `sdk.dir` — not committed. If missing, set `sdk.dir=C\:\\Users\\<you>\\AppData\\Local\\Android\\Sdk`.

## Entry Points

- `app/.../MainActivity.java:18-22` — launcher with ARouter hub paths; hides buttons for standalone-debugged modules via `BuildConfig.MODULE_*_DEBUG`.

## ARouter Routes

- `module_kotlin`: `/kotlin/activity`, `/kotlin/compose`, `/kotlin/firstline`
- `module_designmode`: `/design/activity`
- `module_ipc`: `/ipc/activity`
- `module_android`: `/module/activity`, `/module/leak_canary`, `/module/handler_thread`, `/module/crash`, `/module/main`, `/module/performance`
- `module_jni`: `/jni/activity`
