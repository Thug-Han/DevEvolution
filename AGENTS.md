# AGENTS.md

## Project Overview

Android multi-module learning project (Java + Kotlin). Uses ARouter for inter-module navigation.

## Critical Setup

**JDK 17 required.** Kotlin 1.7.20 kapt is incompatible with JDK 21. Set `JAVA_HOME` to JDK 17 or configure `org.gradle.java.home` in `gradle.properties`. If kapt fails on JDK 21, stop the daemon first (`.\gradlew.bat --stop`).

## Build Commands

Use `./gradlew` (Unix) or `.\gradlew.bat` (Windows). Only JDK 17 works — see Critical Setup above.

```bash
# Full build
./gradlew assembleDebug

# Single module build
./gradlew :app:assembleDebug
./gradlew :module_kotlin:assembleDebug

# Tests (unit only — no instrumented test runners configured beyond defaults)
./gradlew testDebugUnitTest

# Clean
./gradlew clean

# Fix kapt JDK compatibility (if using JDK 21)
.\gradlew.bat --stop
.\gradlew.bat assembleDebug
```

## Module Structure

| Module | Purpose | Language |
|--------|---------|----------|
| `app` | Main application shell | Java (Kotlin plugin applied) |
| `module_kotlin` | Kotlin learning + Compose | Kotlin |
| `module_designmode` | Design patterns | Java |
| `module_ipc` | IPC demonstrations | Java |
| `module_android` | Android utilities | Java |

## Key Quirks

- **Debug flags** in `versions.gradle`: Set `ModuleKotlinDebug = true` (etc.) to run a module standalone as app instead of library. When debug flag is true, the module applies `com.android.application` plugin; when false, `com.android.library`. The launcher intent-filter lives in `src/standalone/AndroidManifest.xml`, dynamically loaded via `sourceSets { debug.manifest.srcFile }` only when the flag is true. This avoids manifest merger leaking launcher activities into the main app.
- **ARouter**: All inter-module navigation uses ARouter paths (e.g., `/design/activity`, `/module/activity`). App module uses `annotationProcessor`; Kotlin module uses `kapt` for ARouter annotation processing.
- **Conditional module dependencies** in `app/build.gradle`: Each submodule is only included as a dependency when its debug flag is false
- **Kotlin module**: Uses Compose BOM 2024.09.00, Java 11 target
- **Other modules**: Java 1.8 target
- **Versions**: Gradle 8.1, AGP 8.1.0, Kotlin 1.7.20
- **Modules**: `module_kotlin`, `module_designmode`, `module_ipc`, `module_android` are regular directories in the repo, not Git submodules

## Entry Points

- `app/src/main/java/com/thughan/evolution/MainActivity.java` — launcher, routes to other modules via ARouter
- `module_kotlin/src/main/java/com/thughan/kotlin/jetpack/compose/ComposeActivity.kt` — Compose examples
- `module_kotlin/src/main/java/com/thughan/kotlin/firstline/` — Kotlin language learning (chapters 2.2–2.8)

## ARouter Route Constants

Routes are defined in `*Constants.java` files in each module:
- `module_kotlin/src/main/java/com/thughan/kotlin/KotlinConstants.java`: `/kotlin/compose`, `/kotlin/firstline`
- `module_designmode/src/main/java/com/thughan/designmode/DesignConstants.java`: `/design/activity`
- `module_ipc/src/main/java/com/thughan/ipc/IpcConstants.java`: `/ipc/activity`
- `module_android/src/main/java/com/thughan/android/ModuleConstants.java`: `/module/activity`
