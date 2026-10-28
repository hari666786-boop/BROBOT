# BROBOT 2035 — Android prototype

> **YOUR DIGITAL LIFE. UNDER YOUR CONTROL.**
> A native Kotlin / Jetpack Compose concept prototype of a 2035 personal privacy guardian.
> Offline, no permissions, no login, **synthetic data only**. Not a security product.

## ⚠️ Build status (read this first)

| Item | Status |
|---|---|
| Complete source project | ✅ in this folder |
| Simulation engine, data catalogs, ViewModel/navigation logic | ✅ **compiled with Kotlin 2.0.21 and executed** (see "Verification") |
| Compose UI screens (`ui/`), `MainActivity` | ⚠️ **written but NOT compiled** — the authoring sandbox could not download the Android SDK, Android Gradle Plugin or Compose libraries |
| Debug APK | ❌ **not built, no APK exists yet** |
| Run on device / emulator | ❌ not done |

Expect the first real build to possibly surface a few small Compose compile errors to fix.

## Get the APK

**Option A — Android Studio (easiest)**
1. Install Android Studio (Koala or newer) and open this `BROBOT` folder.
2. Let Gradle sync (needs internet once).
3. *Build → Build Bundle(s) / APK(s) → Build APK(s)*.
4. APK: `app/build/outputs/apk/debug/app-debug.apk`.

**Option B — command line** (JDK 17 + Android SDK, `ANDROID_HOME` set)
