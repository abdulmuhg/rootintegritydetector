# Root Integrity Detector (Android – Kotlin + C++/NDK – Jetpack Compose)

A tiny Android app demonstrating **systems-level security checks in native C++** with a **JNI** bridge to Kotlin and a **Jetpack Compose** UI.
It inspects common root/tamper indicators (su paths, system properties, SELinux enforcing) and computes an **Integrity Score**.

**What it shows**
- NDK/C++17 + CMake + JNI boundary design
- Low-level checks: `/system/xbin/su`, `ro.debuggable`, `ro.secure`, `ro.build.tags`, `/sys/fs/selinux/enforce`
- Compose UI for visualization

**Build**
- Android Studio Ladybug+; install NDK + CMake
- Run app → **Run Integrity Check**
