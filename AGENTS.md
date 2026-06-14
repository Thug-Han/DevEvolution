# AGENTS.md

## Project Overview

Android multi-module learning project (Java + Kotlin). Uses ARouter for inter-module navigation.

## Build Commands

```bash
# Full build
./gradlew assembleDebug

# Single module build
./gradlew :app:assembleDebug
./gradlew :kotlin:assembleDebug

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
| `kotlin` | Kotlin learning + Compose | Kotlin |
| `designmode` | Design patterns | Java |
| `ipc` | IPC demonstrations | Java |
| `module_android` | Android utilities | Java |

## Key Quirks

- **Debug flags** in `versions.gradle`: Set `KotlinDebug = true` (etc.) to run a module standalone as app instead of library. When debug flag is true, the module applies `com.android.application` plugin; when false, `com.android.library`.
- **Submodules**: `kotlin`, `designmode`, `ipc`, `module_android` are Git submodules — clone with `--recursive`
- **ARouter**: All inter-module navigation uses ARouter paths (e.g., `/design/activity`, `/module/activity`). App module uses `annotationProcessor`; Kotlin module uses `kapt` for ARouter annotation processing.
- **JVM**: Requires JDK 17. Kotlin 1.7.20 kapt is incompatible with JDK 21. `gradle.properties` does NOT set `org.gradle.java.home` — set it yourself or use `JAVA_HOME` pointing to JDK 17.
- **Kotlin module**: Uses Compose BOM 2024.09.00, Java 11 target
- **Other modules**: Java 1.8 target
- **Versions**: Gradle 8.1, AGP 8.1.0, Kotlin 1.7.20
- **Conditional module dependencies** in `app/build.gradle`: Each submodule is only included as a dependency when its debug flag is false

## Entry Points

- `app/src/main/java/com/thughan/evolution/MainActivity.java` — launcher, routes to other modules via ARouter
- `kotlin/src/main/java/com/thughan/kotlin/jetpack/compose/ComposeActivity.kt` — Compose examples
- `kotlin/src/main/java/com/thughan/kotlin/firstline/` — Kotlin language learning (chapters 2.2–2.8)

## ARouter Route Constants

Routes are defined in `*Constants.java` files in each module:
- `kotlin/src/main/java/com/thughan/kotlin/KotlinConstants.java`: `/kotlin/compose`, `/kotlin/firstline`
- `designmode/src/main/java/com/thughan/designmode/DesignConstants.java`: `/design/activity`
- `module_android/src/main/java/com/thughan/android/ModuleConstants.java`: `/module/activity`
