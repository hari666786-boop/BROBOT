# BROBOT 2035 — every file with its GitHub path

Create each file at the path shown (GitHub: *Add file → Create new file*, type the path in the name box, paste the code).

**Not included as text:** `gradlew` and `gradle/wrapper/gradle-wrapper.jar` (binary) — take them from BROBOT.zip, or run `gradle wrapper --gradle-version 8.9` locally.

## File tree

```
.gitignore
README.md
build.gradle.kts
gradle.properties
gradlew.bat
settings.gradle.kts
.github/workflows/build-apk.yml
app/build.gradle.kts
app/src/main/AndroidManifest.xml
app/src/main/java/com/brobot/future/MainActivity.kt
app/src/main/java/com/brobot/future/data/AutopilotCatalog.kt
app/src/main/java/com/brobot/future/data/FeatureCatalog.kt
app/src/main/java/com/brobot/future/data/ShadowCatalog.kt
app/src/main/java/com/brobot/future/data/UniverseCatalog.kt
app/src/main/java/com/brobot/future/domain/BrobotViewModel.kt
app/src/main/java/com/brobot/future/domain/JudgeDemo.kt
app/src/main/java/com/brobot/future/model/Models.kt
app/src/main/java/com/brobot/future/simulation/ExplainerEngine.kt
app/src/main/java/com/brobot/future/simulation/ScenarioCatalog.kt
app/src/main/java/com/brobot/future/simulation/SimulationEngine.kt
app/src/main/java/com/brobot/future/simulation/TwinGraph.kt
app/src/main/java/com/brobot/future/ui/BrobotApp.kt
app/src/main/java/com/brobot/future/ui/components/Components.kt
app/src/main/java/com/brobot/future/ui/components/ConstellationGraph.kt
app/src/main/java/com/brobot/future/ui/components/Shared.kt
app/src/main/java/com/brobot/future/ui/screens/AutopilotScreen.kt
app/src/main/java/com/brobot/future/ui/screens/ButterflyScreen.kt
app/src/main/java/com/brobot/future/ui/screens/ExplainerScreen.kt
app/src/main/java/com/brobot/future/ui/screens/HomeScreen.kt
app/src/main/java/com/brobot/future/ui/screens/InterventionScreen.kt
app/src/main/java/com/brobot/future/ui/screens/IntroScreen.kt
app/src/main/java/com/brobot/future/ui/screens/SettingsScreen.kt
app/src/main/java/com/brobot/future/ui/screens/ShadowScreen.kt
app/src/main/java/com/brobot/future/ui/screens/SimulateScreen.kt
app/src/main/java/com/brobot/future/ui/screens/TimelineScreen.kt
app/src/main/java/com/brobot/future/ui/screens/TwinScreen.kt
app/src/main/java/com/brobot/future/ui/screens/UniverseScreen.kt
app/src/main/java/com/brobot/future/ui/theme/Theme.kt
app/src/main/res/drawable/ic_launcher_foreground.xml
app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml
app/src/main/res/values/colors.xml
app/src/main/res/values/themes.xml
app/src/test/java/com/brobot/future/simulation/SimulationEngineTest.kt
gradle/wrapper/gradle-wrapper.properties
```


---

## 📁 `.gitignore`

```
.gradle/
build/
local.properties
.idea/
*.iml
```


---

## 📁 `README.md`

```markdown
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
```
./gradlew testDebugUnitTest assembleDebug      # Windows: gradlew.bat ...
```

**Option C — free cloud build:** push this folder to a GitHub repo → *Actions → Build BROBOT debug APK → Run workflow* → download the `brobot-debug-apk` artifact. (Workflow file included; not yet run.)

## Install on a phone
1. Copy `app-debug.apk` to the phone.
2. Open it and allow "Install unknown apps" for your file manager/browser when asked.
3. Launch **BROBOT 2035**. (Or with USB debugging: `adb install app-debug.apk`.)

## Tech
Kotlin 2.0.21 · Jetpack Compose (BOM 2024.09.03) · Material 3 · AGP 8.5.2 · Gradle 8.9 wrapper · minSdk 26 / targetSdk 34 · application id `com.brobot.future`. Only dependencies: AndroidX core, activity-compose, lifecycle-viewmodel-compose, Compose UI/Material3/icons-core. **No `<uses-permission>` at all, no internet.**

## Structure
```
app/src/main/java/com/brobot/future/
  model/        data classes & enums (profile, scenarios, pathways, results)
  simulation/   ScenarioCatalog (documented rules) · SimulationEngine · TwinGraph · ExplainerEngine   ← pure Kotlin
  data/         feature, universe, autopilot, shadow catalogs                                           ← pure Kotlin
  domain/       BrobotViewModel (state + navigation back stack) · JudgeDemo steps
  ui/theme      colours, theme, motion settings
  ui/components FuturisticCard, FeatureChip, FeatureInfoDialog, DemoStatusBadge, PrimaryActionButton, ConstellationGraph …
  ui/screens    Intro, Home, Twin, Simulate, Butterfly, Intervention, Explainer, Universe, Autopilot, Shadow, Timeline, Settings
app/src/test/   SimulationEngineTest (JUnit)
```

## Screens (all 12 requested)
Intro · Home · Privacy Twin · Butterfly Effect · What-If Simulator · Risk Intervention · AI Risk Explainer · Privacy Universe · Privacy Autopilot · Digital Shadow · Privacy Timeline · Settings & About. Bottom nav: Home · Twin · Simulate · Universe · Settings.

## How the simulation works
`SimulationEngine.simulate(scenario, DemoProfile)` filters each scenario's documented `RuleStep`s by a predicate on the demo profile. No randomness, no clock, no I/O. Interventions flip one condition and the UI shows `SimulationEngine.compare(...)`, computed from two real engine runs. No probabilities are produced; results are labelled *Pathway present / reduced / not present in this simulation* and always say other risks may remain.

## Honest limitations
- Synthetic data only; nothing is scanned, located, detected or prevented.
- "AI" explanations are template-based local rules (labelled as such) — no model is connected.
- History, checklist and notes are session-only and reset on app restart.
- Dialogs use Material 3 default enter/exit animation; no custom dialog animation.
- Not yet tested on a device: touch-target, contrast, small-screen and text-scaling checks are by design (48dp+ targets, ≥12sp text, "Larger text" and "Reduce motion" toggles) but unverified visually.
```


---

## 📁 `build.gradle.kts`

```kotlin
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}
```


---

## 📁 `gradle.properties`

```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true
```


---

## 📁 `gradlew.bat`

```bat
@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem
@rem SPDX-License-Identifier: Apache-2.0
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH. 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME% 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:execute
@rem Setup the command line

set CLASSPATH=


@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" -jar "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%GRADLE_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
```


---

## 📁 `settings.gradle.kts`

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "BROBOT"
include(":app")
```


---

## 📁 `.github/workflows/build-apk.yml`

```yaml
name: Build BROBOT debug APK
on:
  workflow_dispatch:
  push:
    branches: [ main, master ]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'
      - name: Unit tests (simulation rules)
        run: ./gradlew testDebugUnitTest
      - name: Build debug APK
        run: ./gradlew assembleDebug
      - uses: actions/upload-artifact@v4
        with:
          name: brobot-debug-apk
          path: app/build/outputs/apk/debug/app-debug.apk
```


---

## 📁 `app/build.gradle.kts`

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.brobot.future"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.brobot.future"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0-prototype"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.09.03")
    implementation(composeBom)
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-core")

    testImplementation("junit:junit:4.13.2")
}
```


---

## 📁 `app/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- Deliberately NO permissions: BROBOT is an offline, synthetic-data prototype. -->
    <application
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="BROBOT 2035"
        android:supportsRtl="true"
        android:theme="@style/Theme.Brobot">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```


---

## 📁 `app/src/main/java/com/brobot/future/MainActivity.kt`

```kotlin
package com.brobot.future

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.brobot.future.domain.BrobotViewModel
import com.brobot.future.ui.BrobotApp
import com.brobot.future.ui.theme.BrobotColors
import com.brobot.future.ui.theme.BrobotTheme

class MainActivity : ComponentActivity() {
    private val viewModel: BrobotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrobotTheme(largeText = viewModel.largeText, reduceMotion = viewModel.reduceMotion) {
                Surface(modifier = Modifier.fillMaxSize(), color = BrobotColors.Bg) {
                    BrobotApp(viewModel)
                }
            }
        }
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/data/AutopilotCatalog.kt`

```kotlin
package com.brobot.future.data

import com.brobot.future.model.ChecklistTask

object AutopilotCatalog {
    val tasks: List<ChecklistTask> = listOf(
        ChecklistTask("recovery", "Review important account-recovery options", "Open each important account's settings yourself and check recovery email, number and questions."),
        ChecklistTask("mfa", "Learn how multifactor authentication works", "Read how a second step protects a sign-in and where it can be enabled."),
        ChecklistTask("permissions", "Review app permissions manually", "In your phone's settings, look for apps with access they do not need."),
        ChecklistTask("links", "Learn to recognise suspicious links", "Practise checking sender, spelling and destination before tapping."),
        ChecklistTask("unique", "Check that important accounts use unique passwords", "Make sure email and financial accounts do not share a password."),
        ChecklistTask("public", "Review publicly visible personal information", "Look at what your public profiles show to strangers.")
    )
}
```


---

## 📁 `app/src/main/java/com/brobot/future/data/FeatureCatalog.kt`

```kotlin
package com.brobot.future.data

import com.brobot.future.model.*

object FeatureCatalog {

    private const val NOT_REAL = "All data is synthetic and every result comes from a local rules engine. It does not scan your real device or accounts."

    val features: List<FeatureInfo> = listOf(
        FeatureInfo("twin", "Privacy Twin", "Privacy Twin — Your Digital Risk Simulator",
            "A digital model of your privacy situation.",
            "Select a hypothetical event, such as losing your phone, and explore possible consequences through an interactive risk map.",
            "Understand how one privacy weakness could lead to other problems.",
            "Personalised, interactive digital-risk modelling.",
            "Tap nodes, toggle demo conditions and watch the map update.",
            NOT_REAL, Destination.TWIN, "Try Simulation"),
        FeatureInfo("butterfly", "Butterfly Effect", "Privacy Butterfly Effect — Follow the Chain",
            "A visual chain showing how a small weakness might connect to larger ones.",
            "Reveals a hypothetical, conditional pathway step by step; each step can be inspected.",
            "Invisible cause-and-effect links between everyday choices.",
            "Real-time pathway views built from your own consented data.",
            "Replay a pathway and inspect each step's assumptions and mitigation.",
            "Pathways are conditional possibilities from fixed rules; no real compromise is detected. $NOT_REAL",
            Destination.BUTTERFLY, "Try Simulation"),
        FeatureInfo("whatif", "What-If Simulator", "What-If Simulator — Ask “What If?”",
            "A set of hypothetical privacy situations.",
            "Pick a scenario, run it and see the possible pathway, assumptions and protective actions.",
            "Risks are easier to grasp when you can try them safely.",
            "Personalised scenario libraries that adapt as technology and habits change.",
            "Six built-in scenarios whose outcome depends on your demo conditions.",
            "No phones are located, no Wi-Fi is tested, no apps are inspected. $NOT_REAL",
            Destination.SIMULATE, "Try Simulation"),
        FeatureInfo("universe", "Privacy Universe", "Privacy Universe — Your Constellation",
            "A constellation of the areas that make up a digital life.",
            "Shows identity, accounts, devices, data, permissions, recovery and behaviour and how they connect.",
            "Privacy is spread across many places and is hard to picture.",
            "A living map that updates as your consented digital footprint changes.",
            "Tap a star to highlight its connections and read about it.",
            "The constellation is a fixed illustration, not a map of your real data.",
            Destination.UNIVERSE, "Open Universe"),
        FeatureInfo("explainer", "AI Risk Explainer", "BROBOT Intelligence — Reasons, Not Guesses",
            "An assistant-style screen that explains simulated results.",
            "Answers fixed questions using the simulation's own rules and tags each statement by source.",
            "Risk scores without reasons are hard to trust.",
            "Conversational explanations grounded in verifiable data, with clear uncertainty.",
            "Five questions answered from local logic, separating inputs, rules and unknowns.",
            "No live AI model is connected; text is template-based, not generated by an AI model. $NOT_REAL",
            Destination.EXPLAINER, "Ask BROBOT"),
        FeatureInfo("autopilot", "Privacy Autopilot", "Privacy Autopilot — Guided, Not Automatic",
            "A guided checklist of protective habits.",
            "Walks you through tasks you do yourself, tracking completion.",
            "Knowing what to do next is often the hard part.",
            "Personalised reminders and safe guided flows, with you approving every change.",
            "Check tasks off and watch the completion count.",
            "GUIDED DEMO — NO AUTOMATIC DEVICE CHANGES. BROBOT never alters real settings.",
            Destination.AUTOPILOT, "Open Checklist"),
        FeatureInfo("timeline", "Privacy Timeline", "Privacy Timeline — Your Demo Story",
            "A log of what you explored in this demo.",
            "Records demonstration events and lets you inspect each one.",
            "Seeing what changed and why builds understanding.",
            "A private history of consented privacy changes and their effects.",
            "Open entries and clear history with confirmation.",
            "Entries are synthetic demo events kept for this session only; they may reset when the app restarts.",
            Destination.TIMELINE, "Open Timeline"),
        FeatureInfo("shadow", "Digital Shadow", "Digital Shadow — The Trail You Leave",
            "The information and patterns a person may leave across digital services.",
            "Illustrates categories of footprint with fictional examples and local notes.",
            "People rarely see the combined picture of what they share.",
            "Consent-based footprint discovery with clear controls.",
            "Browse fictional examples and add or remove local notes.",
            "Real footprint discovery is NOT active: nothing is scraped and nobody is searched.",
            Destination.SHADOW, "Explore Shadow")
    )

    fun byTag(tag: String): FeatureInfo = features.first { it.tag == tag }
    fun byId(id: String): FeatureInfo = features.first { it.id == id }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/data/ShadowCatalog.kt`

```kotlin
package com.brobot.future.data

data class ShadowCategory(val id: String, val title: String, val syntheticExample: String, val whyItMatters: String)

object ShadowCatalog {
    val categories: List<ShadowCategory> = listOf(
        ShadowCategory("profile", "Public Profile Information", "Fictional: “Avery K., Lisbon, loves cycling”.", "Small details can combine into a recognisable picture."),
        ShadowCategory("posts", "Public Posts", "Fictional: a holiday photo with a tagged place.", "Posts can reveal habits and locations."),
        ShadowCategory("permissions", "App Permissions", "Fictional: a flashlight app that can read contacts.", "Broad access can widen what is collected."),
        ShadowCategory("connections", "Account Connections", "Fictional: “Sign in with…” links between services.", "Connected accounts can share more than expected."),
        ShadowCategory("sharing", "Data-Sharing Choices", "Fictional: personalised-ads toggle left on.", "Defaults often decide what is shared.")
    )
}
```


---

## 📁 `app/src/main/java/com/brobot/future/data/UniverseCatalog.kt`

```kotlin
package com.brobot.future.data

data class UniverseNode(
    val id: String,
    val label: String,
    val represents: String,
    val whyItMatters: String,
    val productionNeeds: String,
    val prototypeStatus: String,
    /** Normalised position in a 0..1 square. */
    val x: Float,
    val y: Float,
    val connectsTo: List<String>
)

object UniverseCatalog {
    val nodes: List<UniverseNode> = listOf(
        UniverseNode("identity", "Identity", "Who you are online: names, handles and profiles.",
            "Identity ties the other areas together.", "Consent-based identity inventory and clear controls.",
            "Illustration only; no identity is read or searched.", 0.50f, 0.46f,
            listOf("accounts", "personal", "behaviour", "recovery")),
        UniverseNode("accounts", "Accounts", "Services you sign in to.",
            "Accounts are the doors to your data.", "A user-approved account inventory and sign-in hygiene guidance.",
            "No accounts are connected; the demo uses a synthetic profile.", 0.18f, 0.20f,
            listOf("identity", "recovery", "permissions")),
        UniverseNode("devices", "Devices", "Phones, laptops and other hardware.",
            "Devices hold data and keep you signed in.", "On-device, user-approved posture checks.",
            "No device is inspected.", 0.82f, 0.20f,
            listOf("personal", "permissions", "recovery")),
        UniverseNode("personal", "Personal Data", "Photos, messages, files and records.",
            "This is what privacy protects.", "Local classification with explicit consent.",
            "No data is read or stored.", 0.84f, 0.62f,
            listOf("identity", "devices", "permissions")),
        UniverseNode("permissions", "App Permissions", "What apps are allowed to access.",
            "Broad permissions widen what could be seen.", "Guided permission reviews that never change settings silently.",
            "Permissions are not read; one scenario illustrates the idea.", 0.50f, 0.84f,
            listOf("accounts", "devices", "personal")),
        UniverseNode("recovery", "Recovery", "Ways to regain access to accounts.",
            "Recovery routes are also routes in.", "Recovery-method review with user confirmation.",
            "Represented by a demo condition only.", 0.16f, 0.64f,
            listOf("identity", "accounts", "devices")),
        UniverseNode("behaviour", "Online Behaviour", "Habits such as link handling and sharing.",
            "Behaviour shapes exposure as much as settings do.", "Private, on-device habit insights.",
            "Represented by a demo condition only.", 0.50f, 0.12f,
            listOf("identity", "accounts"))
    )
    fun node(id: String) = nodes.first { it.id == id }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/domain/BrobotViewModel.kt`

```kotlin
package com.brobot.future.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.brobot.future.data.FeatureCatalog
import com.brobot.future.model.*
import com.brobot.future.simulation.SimulationEngine

data class UiMessage(val id: Long, val text: String)

/** Single source of UI state. All state is in memory (session only) and all data is synthetic. */
class BrobotViewModel : ViewModel() {

    private val tabs = setOf(Destination.HOME, Destination.TWIN, Destination.SIMULATE, Destination.UNIVERSE, Destination.SETTINGS)

    // ---- navigation (state-based, no extra dependency) ----
    val backStack = mutableStateListOf(Destination.INTRO)
    val current: Destination get() = backStack.last()
    val selectedTab: Destination get() = backStack.lastOrNull { it in tabs } ?: Destination.HOME

    // ---- demo state ----
    var profile by mutableStateOf(DemoProfile.DEFAULT); private set
    var selectedScenario by mutableStateOf<ScenarioId?>(null); private set
    var result by mutableStateOf<SimulationResult?>(null); private set
    var comparison by mutableStateOf<Comparison?>(null); private set
    var selectedInterventionId by mutableStateOf<String?>(null)
    var replayKey by mutableIntStateOf(0); private set
    var inspectStepId by mutableStateOf<String?>(null)
    var explainerQuestion by mutableStateOf<String?>(null)

    val timeline = mutableStateListOf<TimelineEvent>()
    var completedTasks by mutableStateOf<Set<String>>(emptySet()); private set
    val notes = mutableStateListOf<String>()

    // ---- overlays / settings ----
    var featureDialog by mutableStateOf<FeatureInfo?>(null)
    var demoStep by mutableStateOf<Int?>(null); private set
    var message by mutableStateOf<UiMessage?>(null); private set
    var largeText by mutableStateOf(false)
    var reduceMotion by mutableStateOf(false)

    private var eventId = 0
    private var msgId = 0L
    private val demoLogged = mutableSetOf<Int>()

    init { logProfileCreated() }

    // ---------------- navigation ----------------
    fun enterApp() { setStack(Destination.HOME) }

    fun navigate(d: Destination) {
        when (d) {
            Destination.HOME, Destination.INTRO -> setStack(Destination.HOME)
            Destination.TWIN, Destination.SIMULATE, Destination.UNIVERSE, Destination.SETTINGS -> setStack(d)
            Destination.JUDGE_DEMO -> startJudgeDemo()
            else -> if (current != d) backStack.add(d)
        }
    }

    private fun setStack(d: Destination) {
        backStack.clear()
        backStack.add(Destination.HOME)
        if (d != Destination.HOME) backStack.add(d)
    }

    /** @return true if a screen was popped. */
    fun back(): Boolean {
        if (backStack.size > 1) { backStack.removeAt(backStack.lastIndex); return true }
        return false
    }

    fun openFeature(tag: String) { featureDialog = FeatureCatalog.byTag(tag) }
    fun openFeatureById(id: String) { featureDialog = FeatureCatalog.byId(id) }
    fun dismissFeature() { featureDialog = null }
    fun followFeature(info: FeatureInfo) { featureDialog = null; navigate(info.destination) }

    // ---------------- simulation ----------------
    fun toggleCondition(id: ConditionId, value: Boolean) {
        profile = profile.with(id, value)
        comparison = null
        selectedInterventionId = null
        selectedScenario?.let { if (result != null) result = SimulationEngine.simulate(it, profile) }
        replayKey++
    }

    fun selectScenario(id: ScenarioId) {
        if (selectedScenario != id) {
            selectedScenario = id
            result = null
            comparison = null
            selectedInterventionId = null
        }
    }

    fun runScenario(id: ScenarioId) = runScenarioInternal(id, log = true)

    private fun runScenarioInternal(id: ScenarioId, log: Boolean) {
        selectedScenario = id
        comparison = null
        selectedInterventionId = null
        val r = SimulationEngine.simulate(id, profile)
        result = r
        replayKey++
        if (log) log("Scenario explored: ${r.scenario.title}",
            "Simulated with: ${profileSummary(profile)}. Result: ${r.level.label} (${r.riskStepCount} possible step(s)). Synthetic data only.")
    }

    fun replay() { replayKey++ }

    fun applyIntervention() = applyInterventionInternal(log = true)

    private fun applyInterventionInternal(log: Boolean) {
        val r = result ?: return
        val ivId = selectedInterventionId ?: return
        val iv = SimulationEngine.intervention(ivId)
        val cmp = SimulationEngine.compare(r.scenario.id, profile, iv)
        profile = SimulationEngine.apply(iv, profile)
        comparison = cmp
        result = cmp.after
        selectedInterventionId = null
        replayKey++
        if (log) {
            log("Protective intervention selected", "Demo intervention: ${iv.title}. ${cmp.changeDescription}")
            log("Scenario simulated again", "${r.scenario.title} re-run with: ${profileSummary(profile)}.")
            log("Comparison reviewed", "${cmp.summary} Steps: ${cmp.before.riskStepCount} → ${cmp.after.riskStepCount}.")
        }
        toast(cmp.changeDescription)
    }

    fun resetDemoProfile() {
        profile = DemoProfile.DEFAULT
        comparison = null
        selectedInterventionId = null
        val s = selectedScenario
        result = if (s != null && result != null) SimulationEngine.simulate(s, profile) else null
        replayKey++
        log("Demo profile reset", "Demo conditions restored to the default synthetic profile.")
        toast("Demo profile reset")
    }

    fun resetAll() {
        profile = DemoProfile.DEFAULT
        selectedScenario = null
        result = null
        comparison = null
        selectedInterventionId = null
        inspectStepId = null
        explainerQuestion = null
        completedTasks = emptySet()
        notes.clear()
        timeline.clear()
        demoStep = null
        featureDialog = null
        demoLogged.clear()
        eventId = 0
        logProfileCreated()
        toast("All demo data reset")
    }

    // ---------------- checklist, notes, history ----------------
    fun toggleTask(id: String, checked: Boolean) {
        completedTasks = if (checked) completedTasks + id else completedTasks - id
        toast(if (checked) "Marked complete — demo state only, no device settings changed" else "Marked incomplete")
    }

    fun addNote(text: String) {
        val t = text.trim().take(80)
        if (t.isNotEmpty()) { notes.add(t); toast("Local demo note added") }
    }

    fun removeNote(index: Int) { if (index in notes.indices) notes.removeAt(index) }

    fun clearHistory() { timeline.clear(); toast("Demo history cleared") }

    // ---------------- judge demo ----------------
    fun startJudgeDemo() {
        demoLogged.clear()
        goToDemoStep(0)
    }

    fun exitJudgeDemo() { demoStep = null; inspectStepId = null }

    fun nextDemoStep() { val s = demoStep ?: return; if (s < JudgeDemo.steps.lastIndex) goToDemoStep(s + 1) else exitJudgeDemo() }
    fun previousDemoStep() { val s = demoStep ?: return; if (s > 0) goToDemoStep(s - 1) }

    private fun prepareReusedPassword(log: Boolean) {
        profile = DemoProfile.DEFAULT
        runScenarioInternal(ScenarioId.REUSED_PASSWORD, log)
    }

    private fun goToDemoStep(i: Int) {
        demoStep = i
        featureDialog = null
        inspectStepId = null
        val first = demoLogged.add(if (i == 5) 4 else i)
        when (i) {
            0 -> { resetAllQuiet(); setStack(Destination.HOME) }
            1 -> { profile = DemoProfile.DEFAULT; selectedScenario = null; result = null; comparison = null; setStack(Destination.TWIN) }
            2 -> { prepareReusedPassword(first); setStack(Destination.BUTTERFLY) }
            3 -> {
                prepareReusedPassword(false)
                setStack(Destination.BUTTERFLY)
                inspectStepId = result?.steps?.getOrNull(1)?.id
            }
            4, 5 -> {
                prepareReusedPassword(false)
                selectedInterventionId = "unique_passwords"
                applyInterventionInternal(first)
                setStack(Destination.INTERVENTION)
            }
            else -> setStack(Destination.HOME)
        }
    }

    // ---------------- helpers ----------------
    private fun resetAllQuiet() {
        profile = DemoProfile.DEFAULT; selectedScenario = null; result = null; comparison = null
        selectedInterventionId = null; timeline.clear(); eventId = 0; logProfileCreated()
    }

    private fun logProfileCreated() =
        log("Demo profile created", "A synthetic demo profile was created: ${profileSummary(DemoProfile.DEFAULT)}. Nothing was read from your device.")

    private fun log(title: String, details: String) {
        eventId++
        timeline.add(TimelineEvent(eventId, title, details, eventId))
    }

    fun toast(text: String) { message = UiMessage(++msgId, text) }

    fun profileSummary(p: DemoProfile): String =
        ConditionId.values().joinToString("; ") { conditionStateLabel(it, p) }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/domain/JudgeDemo.kt`

```kotlin
package com.brobot.future.domain

import com.brobot.future.model.Destination

data class JudgeDemoStep(val timing: String, val title: String, val body: String, val destination: Destination)

object JudgeDemo {
    val steps: List<JudgeDemoStep> = listOf(
        JudgeDemoStep("0–10 s", "Meet BROBOT",
            "BROBOT makes invisible privacy risks visible and interactive. Everything runs offline on synthetic data.", Destination.HOME),
        JudgeDemoStep("10–25 s", "Your Privacy Twin",
            "A simulated digital identity. Tap a node or flip a demo condition — only the local demo profile changes.", Destination.TWIN),
        JudgeDemoStep("25–40 s", "The Butterfly Effect",
            "We picked “Reused Password”. Watch a hypothetical, conditional chain appear — nothing real is detected.", Destination.BUTTERFLY),
        JudgeDemoStep("40–55 s", "Inspect a step",
            "Every step explains what it represents, why it connects, the assumptions used and what could reduce it.", Destination.BUTTERFLY),
        JudgeDemoStep("55–70 s", "Apply a protective action",
            "We applied “Use unique passwords” to the demo profile and re-ran the same deterministic rules.", Destination.INTERVENTION),
        JudgeDemoStep("70–82 s", "Compare before and after",
            "The comparison comes straight from the rules engine. It names the changed condition and says other risks may remain.", Destination.INTERVENTION),
        JudgeDemoStep("82–90 s", "More from 2035",
            "Tap any tag on Home to explore Autopilot, Digital Shadow, the Explainer, Timeline and more.", Destination.HOME)
    )
}
```


---

## 📁 `app/src/main/java/com/brobot/future/model/Models.kt`

```kotlin
package com.brobot.future.model

/** Every place a feature tag can send the judge. */
enum class Destination(val label: String) {
    INTRO("Intro"),
    HOME("Home"),
    TWIN("Twin"),
    SIMULATE("Simulate"),
    UNIVERSE("Universe"),
    SETTINGS("Settings"),
    BUTTERFLY("Butterfly Effect"),
    INTERVENTION("Risk Intervention"),
    EXPLAINER("AI Risk Explainer"),
    AUTOPILOT("Privacy Autopilot"),
    SHADOW("Digital Shadow"),
    TIMELINE("Privacy Timeline"),
    JUDGE_DEMO("Judge Demo")
}

/** Selectable demo conditions. These only ever change the LOCAL simulated profile. */
enum class ConditionId(val label: String) {
    PASSWORD_REUSE("Password reuse"),
    TWO_FACTOR("Two-factor authentication"),
    PHONE_LOCK("Phone lock"),
    RECOVERY_METHOD("Recovery method"),
    LINK_AWARENESS("Suspicious-link awareness"),
    PERMISSION_REVIEW("App-permission review")
}

/**
 * The simulated profile. Pure synthetic data: never read from the real device.
 * Default = a deliberately "weak" demo persona so there is something to simulate.
 */
data class DemoProfile(
    val passwordReuse: Boolean = true,
    val twoFactor: Boolean = false,
    val phoneLock: Boolean = false,
    val strongRecovery: Boolean = false,
    val linkAware: Boolean = false,
    val permissionsReviewed: Boolean = false
) {
    fun isSet(id: ConditionId): Boolean = when (id) {
        ConditionId.PASSWORD_REUSE -> passwordReuse
        ConditionId.TWO_FACTOR -> twoFactor
        ConditionId.PHONE_LOCK -> phoneLock
        ConditionId.RECOVERY_METHOD -> strongRecovery
        ConditionId.LINK_AWARENESS -> linkAware
        ConditionId.PERMISSION_REVIEW -> permissionsReviewed
    }

    fun with(id: ConditionId, value: Boolean): DemoProfile = when (id) {
        ConditionId.PASSWORD_REUSE -> copy(passwordReuse = value)
        ConditionId.TWO_FACTOR -> copy(twoFactor = value)
        ConditionId.PHONE_LOCK -> copy(phoneLock = value)
        ConditionId.RECOVERY_METHOD -> copy(strongRecovery = value)
        ConditionId.LINK_AWARENESS -> copy(linkAware = value)
        ConditionId.PERMISSION_REVIEW -> copy(permissionsReviewed = value)
    }

    companion object {
        val DEFAULT = DemoProfile()
    }
}

/** Human-readable state label for a condition, e.g. "Unique passwords" / "Passwords reused". */
fun conditionStateLabel(id: ConditionId, profile: DemoProfile): String = when (id) {
    ConditionId.PASSWORD_REUSE -> if (profile.passwordReuse) "Passwords reused (demo)" else "Unique passwords (demo)"
    ConditionId.TWO_FACTOR -> if (profile.twoFactor) "2FA on (demo)" else "2FA off (demo)"
    ConditionId.PHONE_LOCK -> if (profile.phoneLock) "Phone locked (demo)" else "No phone lock (demo)"
    ConditionId.RECOVERY_METHOD -> if (profile.strongRecovery) "Reviewed recovery (demo)" else "Weak recovery (demo)"
    ConditionId.LINK_AWARENESS -> if (profile.linkAware) "Link-aware (demo)" else "Not link-aware (demo)"
    ConditionId.PERMISSION_REVIEW -> if (profile.permissionsReviewed) "Permissions reviewed (demo)" else "Permissions not reviewed (demo)"
}

enum class ScenarioId { REUSED_PASSWORD, SUSPICIOUS_LINK, LOST_PHONE, WEAK_RECOVERY, PUBLIC_WIFI, EXCESSIVE_PERMISSIONS }

data class Scenario(
    val id: ScenarioId,
    val title: String,
    val summary: String,
    /** Conditions the rules for this scenario read. Documented for the Explainer. */
    val readsConditions: List<ConditionId>
)

/** Where a statement in an explanation comes from. Required by the "no fake AI" rule. */
enum class EvidenceKind(val label: String) {
    USER_INPUT("Your demo input"),
    SYNTHETIC("Synthetic demo data"),
    RULE("Documented rule"),
    UNKNOWN("Unknown to this simulation"),
    SUGGESTION("Suggested protective action")
}

data class Assumption(val kind: EvidenceKind, val text: String)

enum class TwinNodeId(val label: String) {
    ACCOUNT_ACCESS("Account Access"),
    PERSONAL_DATA("Personal Data"),
    DEVICE_SECURITY("Device Security"),
    SOCIAL_ENGINEERING("Social Engineering"),
    ACCOUNT_RECOVERY("Account Recovery"),
    FINANCIAL_EXPOSURE("Financial Exposure")
}

enum class NodeStatus(val label: String) {
    PROTECTED("Protective conditions set (demo)"),
    MIXED("Partly protected (demo)"),
    ATTENTION("Needs attention (demo)")
}

/** One step of a hypothetical, conditional pathway. */
data class PathwayStep(
    val id: String,
    val title: String,
    val represents: String,
    val whyConnected: String,
    val assumptions: List<Assumption>,
    val reduceAction: String,
    val nodes: Set<TwinNodeId>,
    val isTrigger: Boolean
)

enum class PathwayLevel(val label: String) {
    PRESENT("Pathway present in this simulation"),
    REDUCED("Pathway reduced in this simulation"),
    ABSENT("Pathway not present in this simulation")
}

data class Intervention(
    val id: String,
    val title: String,
    val description: String,
    val condition: ConditionId,
    /** Value the condition is set to when applied. */
    val setsTo: Boolean,
    val relevantScenarios: Set<ScenarioId>
)

data class SimulationResult(
    val scenario: Scenario,
    val profile: DemoProfile,
    val steps: List<PathwayStep>,
    val assumptions: List<Assumption>,
    val suggestedInterventions: List<Intervention>,
    val noPathwayNote: String?,
    val label: String = "SIMULATED RESULT · SYNTHETIC DATA · NOT A REAL SCAN"
) {
    /** Risk steps = everything after the trigger. */
    val riskStepCount: Int get() = steps.count { !it.isTrigger }
    val level: PathwayLevel get() = if (riskStepCount > 0) PathwayLevel.PRESENT else PathwayLevel.ABSENT
    val affectedNodes: Set<TwinNodeId> get() = steps.flatMap { it.nodes }.toSet()
}

data class Comparison(
    val before: SimulationResult,
    val after: SimulationResult,
    val intervention: Intervention,
    val changeDescription: String,
    val removedSteps: List<PathwayStep>,
    val remainingSteps: List<PathwayStep>,
    val level: PathwayLevel,
    val summary: String
)

data class TimelineEvent(
    val id: Int,
    val title: String,
    val details: String,
    /** Simple monotonically increasing sequence label, e.g. "Step 3" (no real clock needed). */
    val sequence: Int
)

data class ChecklistTask(val id: String, val title: String, val detail: String)

data class FeatureInfo(
    val id: String,
    val tag: String,
    val dialogTitle: String,
    val whatItIs: String,
    val whatItDoes: String,
    val problemSolved: String,
    val vision2035: String,
    val prototypeDemonstrates: String,
    val simulatedOrMissing: String,
    val destination: Destination,
    val actionLabel: String
)

data class ExplainerAnswer(val question: String, val parts: List<Assumption>)
```


---

## 📁 `app/src/main/java/com/brobot/future/simulation/ExplainerEngine.kt`

```kotlin
package com.brobot.future.simulation

import com.brobot.future.model.*

/**
 * "BROBOT INTELLIGENCE": template-based explanations generated by local rules.
 * This is NOT a language model and the UI says so. Every part is tagged with its [EvidenceKind].
 */
object ExplainerEngine {

    val questions: List<String> = listOf(
        "Why is this pathway shown?",
        "What assumption is being made?",
        "What should I do first?",
        "What changes if I enable protection?",
        "What does this simulation not know?"
    )

    fun answer(question: String, result: SimulationResult): ExplainerAnswer {
        val parts = mutableListOf<Assumption>()
        val scenario = result.scenario
        val rules = ScenarioCatalog.rulesFor(scenario.id)

        when (question) {
            questions[0] -> {
                if (result.riskStepCount == 0) {
                    parts += Assumption(EvidenceKind.RULE, "No risk steps are shown for “${scenario.title}” because none of its documented rules match the current demo profile.")
                } else {
                    parts += Assumption(EvidenceKind.RULE, "“${scenario.title}” shows ${result.riskStepCount} possible step(s) because these documented rules matched:")
                    rules.filter { it.requires(result.profile) && !it.step.isTrigger }.forEach {
                        parts += Assumption(EvidenceKind.RULE, "${it.step.title} — ${it.ruleText}")
                    }
                }
                result.profile.let { p ->
                    scenario.readsConditions.forEach { c ->
                        parts += Assumption(EvidenceKind.USER_INPUT, "${c.label}: ${conditionStateLabel(c, p)}.")
                    }
                }
                parts += Assumption(EvidenceKind.SYNTHETIC, "The profile is synthetic demo data, not read from your device.")
            }
            questions[1] -> {
                result.steps.flatMap { it.assumptions }.distinctBy { it.text }.forEach { parts += it }
                if (result.steps.isEmpty()) parts += Assumption(EvidenceKind.RULE, "No steps are active, so no step-level assumptions apply.")
                parts += Assumption(EvidenceKind.UNKNOWN, "Real-world likelihood and impact are not modelled.")
            }
            questions[2] -> {
                val first = result.suggestedInterventions.firstOrNull()
                if (first == null) {
                    parts += Assumption(EvidenceKind.SUGGESTION, "In this scenario the relevant demo protections are already set. Try another scenario or reset the demo profile.")
                } else {
                    parts += Assumption(EvidenceKind.SUGGESTION, "First: ${first.title}. ${first.description}")
                    parts += Assumption(EvidenceKind.RULE, "It is listed first because it matches a condition read by this scenario's rules and is not yet set in the demo profile.")
                    result.suggestedInterventions.drop(1).forEach { parts += Assumption(EvidenceKind.SUGGESTION, "Then: ${it.title}.") }
                }
                parts += Assumption(EvidenceKind.UNKNOWN, "Which action matters most for a real person depends on facts this simulation does not have.")
            }
            questions[3] -> {
                val first = result.suggestedInterventions.firstOrNull()
                if (first == null) {
                    parts += Assumption(EvidenceKind.RULE, "No further demo protection would change this scenario's pathway.")
                } else {
                    val cmp = SimulationEngine.compare(scenario.id, result.profile, first)
                    parts += Assumption(EvidenceKind.SUGGESTION, "If you applied “${first.title}”: ${cmp.changeDescription}")
                    parts += Assumption(EvidenceKind.RULE, cmp.summary)
                    parts += Assumption(EvidenceKind.RULE, "Possible steps: ${cmp.before.riskStepCount} → ${cmp.after.riskStepCount}.")
                }
                parts += Assumption(EvidenceKind.UNKNOWN, "Other risks may remain; this simulation does not estimate probabilities.")
            }
            else -> {
                parts += Assumption(EvidenceKind.UNKNOWN, "Your real devices, accounts, apps, networks and habits — nothing real is inspected.")
                parts += Assumption(EvidenceKind.UNKNOWN, "Whether any real weakness or breach exists. BROBOT does not detect breaches.")
                parts += Assumption(EvidenceKind.UNKNOWN, "How likely or how severe any pathway would be in reality. No probabilities are produced.")
                parts += Assumption(EvidenceKind.SYNTHETIC, "Everything shown comes from synthetic scenario data and a local rules engine — no live AI model generated this text.")
            }
        }
        return ExplainerAnswer(question, parts)
    }

    /** Explains exactly which documented rules produced (or hid) each step of the current chain. */
    fun explainChain(result: SimulationResult): List<Pair<String, String>> {
        val rules = ScenarioCatalog.rulesFor(result.scenario.id)
        val shown = rules.filter { it.requires(result.profile) }
        val hidden = rules.filter { !it.requires(result.profile) }
        return listOf(
            "Hypothetical and conditional" to "This chain is a what-if. It does not mean any of these steps has happened or will happen, and no real compromise was detected.",
            "Steps shown (rule matched)" to (if (shown.isEmpty()) "None." else shown.joinToString("\n") { "• ${it.step.title} — ${it.ruleText}" }),
            "Steps not shown (rule not matched)" to (if (hidden.isEmpty()) "None — every rule matched." else hidden.joinToString("\n") { "• ${it.step.title} — ${it.ruleText}" }),
            "What is not known" to "Real-world likelihood, timing and impact. Everything uses synthetic data and a local rules engine, not a live AI model."
        )
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/simulation/ScenarioCatalog.kt`

```kotlin
package com.brobot.future.simulation

import com.brobot.future.model.*

/**
 * A documented rule: a pathway step is included in the simulation if and only if
 * [requires] is true for the demo profile. [ruleText] is shown to the judge verbatim
 * (Explainer / "What assumption is being made?"), so docs and behaviour cannot diverge.
 */
class RuleStep(
    val step: PathwayStep,
    val ruleText: String,
    val requires: (DemoProfile) -> Boolean
)

object ScenarioCatalog {

    val scenarios: List<Scenario> = listOf(
        Scenario(
            ScenarioId.REUSED_PASSWORD, "Reused Password",
            "Explore a hypothetical situation in which one password is used on several accounts.",
            listOf(ConditionId.PASSWORD_REUSE, ConditionId.TWO_FACTOR, ConditionId.RECOVERY_METHOD)
        ),
        Scenario(
            ScenarioId.SUSPICIOUS_LINK, "Suspicious Link",
            "Explore a hypothetical situation in which a convincing but untrustworthy link arrives.",
            listOf(ConditionId.LINK_AWARENESS, ConditionId.TWO_FACTOR, ConditionId.PASSWORD_REUSE)
        ),
        Scenario(
            ScenarioId.LOST_PHONE, "Lost Phone",
            "Explore a hypothetical situation in which your phone is lost.",
            listOf(ConditionId.PHONE_LOCK, ConditionId.RECOVERY_METHOD)
        ),
        Scenario(
            ScenarioId.WEAK_RECOVERY, "Weak Account Recovery",
            "Explore a hypothetical situation in which an account-recovery option is easy to guess.",
            listOf(ConditionId.RECOVERY_METHOD, ConditionId.TWO_FACTOR, ConditionId.PASSWORD_REUSE)
        ),
        Scenario(
            ScenarioId.PUBLIC_WIFI, "Public Wi-Fi Privacy",
            "Explore a hypothetical situation in which you use an unfamiliar public network.",
            listOf(ConditionId.LINK_AWARENESS, ConditionId.PASSWORD_REUSE, ConditionId.TWO_FACTOR)
        ),
        Scenario(
            ScenarioId.EXCESSIVE_PERMISSIONS, "Excessive App Permissions",
            "Explore a hypothetical situation in which an app holds broader permissions than it needs.",
            listOf(ConditionId.PERMISSION_REVIEW, ConditionId.TWO_FACTOR)
        )
    )

    fun scenario(id: ScenarioId): Scenario = scenarios.first { it.id == id }

    // ---- helpers to keep rule definitions compact and consistent ----
    private fun a(kind: EvidenceKind, text: String) = Assumption(kind, text)
    private val SYN_NO_REAL = a(EvidenceKind.SYNTHETIC, "No real device, account, network or app was inspected; all data is synthetic.")
    private val UNK_REAL_WORLD = a(EvidenceKind.UNKNOWN, "Real-world likelihood is unknown. Pathways are conditional possibilities, not predictions.")

    private fun step(
        id: String, title: String, represents: String, why: String,
        assumptions: List<Assumption>, reduce: String, nodes: Set<TwinNodeId>, trigger: Boolean = false
    ) = PathwayStep(id, title, represents, why, assumptions, reduce, nodes, trigger)

    private val rules: Map<ScenarioId, List<RuleStep>> = mapOf(

        ScenarioId.REUSED_PASSWORD to listOf(
            RuleStep(
                step("rp1", "Password Reuse", "One password is shared by several accounts in this demo profile.",
                    "This is the starting condition you selected.",
                    listOf(a(EvidenceKind.USER_INPUT, "Password reuse is ON in the demo profile."), SYN_NO_REAL),
                    "Use unique passwords for important accounts.", setOf(TwinNodeId.ACCOUNT_ACCESS), trigger = true),
                "Included when: password reuse = ON.", { it.passwordReuse }),
            RuleStep(
                step("rp2", "One Account Could Be Compromised", "A hypothetical exposure of a single account's password.",
                    "If a password is exposed somewhere, the same password is a candidate elsewhere.",
                    listOf(a(EvidenceKind.RULE, "Assumes one account's password is hypothetically exposed."), UNK_REAL_WORLD),
                    "Consider a reputable password manager to generate unique passwords.", setOf(TwinNodeId.ACCOUNT_ACCESS)),
                "Included when: password reuse = ON.", { it.passwordReuse }),
            RuleStep(
                step("rp3", "Other Accounts Might Become Exposed", "Accounts sharing the same password become reachable in this model.",
                    "Reuse is what links the first account to the others.",
                    listOf(a(EvidenceKind.RULE, "Rule: reused password links accounts together."), UNK_REAL_WORLD),
                    "Change reused passwords, starting with email and financial accounts.", setOf(TwinNodeId.ACCOUNT_ACCESS, TwinNodeId.ACCOUNT_RECOVERY)),
                "Included when: password reuse = ON.", { it.passwordReuse }),
            RuleStep(
                step("rp4", "Personal Information Could Be Misused", "Data stored in the reachable accounts could be viewed.",
                    "Without a second sign-in step, a password alone could be enough in this model.",
                    listOf(a(EvidenceKind.USER_INPUT, "Two-factor authentication is OFF in the demo profile."), a(EvidenceKind.RULE, "Rule: 2FA ON breaks this link in the simulation.")),
                    "Enable multifactor authentication where available.", setOf(TwinNodeId.PERSONAL_DATA)),
                "Included when: password reuse = ON and 2FA = OFF.", { it.passwordReuse && !it.twoFactor }),
            RuleStep(
                step("rp5", "Further Privacy or Financial Consequences", "Downstream effects if recovery routes are also weak.",
                    "Weak recovery options could widen the effect in this model.",
                    listOf(a(EvidenceKind.USER_INPUT, "Recovery method is not reviewed in the demo profile."), UNK_REAL_WORLD),
                    "Review account-recovery options.", setOf(TwinNodeId.FINANCIAL_EXPOSURE, TwinNodeId.ACCOUNT_RECOVERY)),
                "Included when: password reuse = ON, 2FA = OFF and recovery = weak.",
                { it.passwordReuse && !it.twoFactor && !it.strongRecovery })
        ),

        ScenarioId.SUSPICIOUS_LINK to listOf(
            RuleStep(
                step("sl1", "A Suspicious Link Arrives", "A hypothetical message contains an untrustworthy link.",
                    "This is the scenario's starting event; it is always assumed.",
                    listOf(SYN_NO_REAL), "Learn to recognise suspicious links before opening them.", setOf(TwinNodeId.SOCIAL_ENGINEERING), trigger = true),
                "Always included (scenario trigger).", { true }),
            RuleStep(
                step("sl2", "The Link Might Be Opened", "Without link awareness the link may look trustworthy.",
                    "Social-engineering messages rely on appearing legitimate.",
                    listOf(a(EvidenceKind.USER_INPUT, "Suspicious-link awareness is OFF in the demo profile."), UNK_REAL_WORLD),
                    "Pause and check sender and link address before tapping.", setOf(TwinNodeId.SOCIAL_ENGINEERING)),
                "Included when: link awareness = OFF.", { !it.linkAware }),
            RuleStep(
                step("sl3", "Details Might Be Entered on a Look-alike Page", "A copied sign-in page could ask for credentials.",
                    "An opened link can lead to a page imitating a real service.",
                    listOf(a(EvidenceKind.RULE, "Rule: opened link may lead to a look-alike page."), UNK_REAL_WORLD),
                    "Open services by typing the address or using the official app.", setOf(TwinNodeId.SOCIAL_ENGINEERING, TwinNodeId.PERSONAL_DATA)),
                "Included when: link awareness = OFF.", { !it.linkAware }),
            RuleStep(
                step("sl4", "Account Access Might Be Attempted", "Captured details could be tried on the real service.",
                    "Without 2FA, a password alone might be accepted in this model.",
                    listOf(a(EvidenceKind.USER_INPUT, "Two-factor authentication is OFF in the demo profile.")),
                    "Enable multifactor authentication where available.", setOf(TwinNodeId.ACCOUNT_ACCESS)),
                "Included when: link awareness = OFF and 2FA = OFF.", { !it.linkAware && !it.twoFactor }),
            RuleStep(
                step("sl5", "Reuse Could Widen the Effect", "The same password may exist on other accounts.",
                    "Reused passwords connect one account to others.",
                    listOf(a(EvidenceKind.USER_INPUT, "Password reuse is ON in the demo profile.")),
                    "Use unique passwords for important accounts.", setOf(TwinNodeId.ACCOUNT_ACCESS, TwinNodeId.FINANCIAL_EXPOSURE)),
                "Included when: link awareness = OFF, 2FA = OFF and password reuse = ON.",
                { !it.linkAware && !it.twoFactor && it.passwordReuse })
        ),

        ScenarioId.LOST_PHONE to listOf(
            RuleStep(
                step("lp1", "The Phone Is Lost", "A hypothetical loss of the device.",
                    "This is the scenario's starting event; it is always assumed.",
                    listOf(SYN_NO_REAL, a(EvidenceKind.UNKNOWN, "BROBOT does not locate or track any real phone.")),
                    "Set a screen lock before anything goes missing.", setOf(TwinNodeId.DEVICE_SECURITY), trigger = true),
                "Always included (scenario trigger).", { true }),
            RuleStep(
                step("lp2", "Unauthorised Access Might Be Attempted", "Someone could try to use the unlocked device.",
                    "With no lock, the device itself is the only barrier.",
                    listOf(a(EvidenceKind.USER_INPUT, "Phone lock is OFF in the demo profile.")),
                    "Enable a PIN, pattern or biometric lock.", setOf(TwinNodeId.DEVICE_SECURITY)),
                "Included when: phone lock = OFF.", { !it.phoneLock }),
            RuleStep(
                step("lp3", "Locally Stored Information Might Be Exposed", "Photos, messages or files on the device.",
                    "Access to an unlocked device can include its stored data.",
                    listOf(a(EvidenceKind.RULE, "Rule: no lock → local data considered reachable."), UNK_REAL_WORLD),
                    "Enable a lock and consider encrypted storage options.", setOf(TwinNodeId.PERSONAL_DATA, TwinNodeId.DEVICE_SECURITY)),
                "Included when: phone lock = OFF.", { !it.phoneLock }),
            RuleStep(
                step("lp4", "Signed-in Accounts Might Be Reached", "Apps that stay signed in could be opened.",
                    "Signed-in apps depend on the device lock in this model.",
                    listOf(a(EvidenceKind.USER_INPUT, "Phone lock is OFF in the demo profile.")),
                    "Enable a lock and review which apps stay signed in.", setOf(TwinNodeId.ACCOUNT_ACCESS, TwinNodeId.FINANCIAL_EXPOSURE)),
                "Included when: phone lock = OFF.", { !it.phoneLock }),
            RuleStep(
                step("lp5", "Account-Recovery Complications", "Recovery codes or numbers tied to the lost device.",
                    "Recovery methods that depend on the phone may be harder to use.",
                    listOf(a(EvidenceKind.USER_INPUT, "Recovery method is not reviewed in the demo profile."), UNK_REAL_WORLD),
                    "Review account-recovery options and keep a backup method.", setOf(TwinNodeId.ACCOUNT_RECOVERY)),
                "Included when: recovery = weak (regardless of phone lock).", { !it.strongRecovery })
        ),

        ScenarioId.WEAK_RECOVERY to listOf(
            RuleStep(
                step("wr1", "A Weak Recovery Option", "A recovery question or address that is easy to guess or find.",
                    "This is the starting condition you selected.",
                    listOf(a(EvidenceKind.USER_INPUT, "Recovery method is not reviewed in the demo profile."), SYN_NO_REAL),
                    "Review account-recovery options.", setOf(TwinNodeId.ACCOUNT_RECOVERY), trigger = true),
                "Included when: recovery = weak.", { !it.strongRecovery }),
            RuleStep(
                step("wr2", "A Reset Might Be Attempted", "Someone could try the recovery route instead of the password.",
                    "Recovery is an alternative way into an account.",
                    listOf(a(EvidenceKind.RULE, "Rule: weak recovery is treated as an alternative entry route."), UNK_REAL_WORLD),
                    "Use recovery options that are not publicly guessable.", setOf(TwinNodeId.ACCOUNT_RECOVERY, TwinNodeId.ACCOUNT_ACCESS)),
                "Included when: recovery = weak.", { !it.strongRecovery }),
            RuleStep(
                step("wr3", "The Reset Might Succeed", "Guessable answers could satisfy the check.",
                    "Without a second factor nothing else may stop the reset in this model.",
                    listOf(a(EvidenceKind.USER_INPUT, "Two-factor authentication is OFF in the demo profile.")),
                    "Enable multifactor authentication where available.", setOf(TwinNodeId.ACCOUNT_ACCESS, TwinNodeId.PERSONAL_DATA)),
                "Included when: recovery = weak and 2FA = OFF.", { !it.strongRecovery && !it.twoFactor }),
            RuleStep(
                step("wr4", "Connected Accounts Might Follow", "An email account often unlocks other resets.",
                    "Reuse could let one recovered account open others.",
                    listOf(a(EvidenceKind.USER_INPUT, "Password reuse is ON in the demo profile.")),
                    "Use unique passwords for important accounts.", setOf(TwinNodeId.FINANCIAL_EXPOSURE, TwinNodeId.ACCOUNT_ACCESS)),
                "Included when: recovery = weak, 2FA = OFF and password reuse = ON.",
                { !it.strongRecovery && !it.twoFactor && it.passwordReuse })
        ),

        ScenarioId.PUBLIC_WIFI to listOf(
            RuleStep(
                step("pw1", "An Unfamiliar Public Network", "A hypothetical open network in a public place.",
                    "This is the scenario's starting event; it is always assumed.",
                    listOf(SYN_NO_REAL, a(EvidenceKind.UNKNOWN, "BROBOT does not test or connect to any real Wi-Fi network.")),
                    "Prefer trusted networks for sensitive tasks.", setOf(TwinNodeId.DEVICE_SECURITY), trigger = true),
                "Always included (scenario trigger).", { true }),
            RuleStep(
                step("pw2", "A Fake Sign-in Page Might Appear", "Some networks show a login page that could imitate a real one.",
                    "People who are not link-aware may trust such pages.",
                    listOf(a(EvidenceKind.USER_INPUT, "Suspicious-link awareness is OFF in the demo profile.")),
                    "Learn to recognise suspicious pages before entering details.", setOf(TwinNodeId.SOCIAL_ENGINEERING, TwinNodeId.PERSONAL_DATA)),
                "Included when: link awareness = OFF.", { !it.linkAware }),
            RuleStep(
                step("pw3", "Captured Details Might Be Reused", "A captured password could be tried elsewhere.",
                    "Reuse allows one captured password to matter on other services.",
                    listOf(a(EvidenceKind.USER_INPUT, "Password reuse is ON in the demo profile.")),
                    "Use unique passwords for important accounts.", setOf(TwinNodeId.ACCOUNT_ACCESS)),
                "Included when: link awareness = OFF and password reuse = ON.", { !it.linkAware && it.passwordReuse }),
            RuleStep(
                step("pw4", "Account Access Might Be Attempted", "Captured details might be tried on real services.",
                    "Without 2FA a password alone might be accepted in this model.",
                    listOf(a(EvidenceKind.USER_INPUT, "Two-factor authentication is OFF in the demo profile.")),
                    "Enable multifactor authentication where available.", setOf(TwinNodeId.ACCOUNT_ACCESS, TwinNodeId.FINANCIAL_EXPOSURE)),
                "Included when: link awareness = OFF, password reuse = ON and 2FA = OFF.",
                { !it.linkAware && it.passwordReuse && !it.twoFactor })
        ),

        ScenarioId.EXCESSIVE_PERMISSIONS to listOf(
            RuleStep(
                step("ep1", "An App Holds Broad Permissions", "An app can access more than it needs in this demo.",
                    "This is the starting condition you selected.",
                    listOf(a(EvidenceKind.USER_INPUT, "App permissions are NOT reviewed in the demo profile."), SYN_NO_REAL),
                    "Review app permissions manually.", setOf(TwinNodeId.PERSONAL_DATA), trigger = true),
                "Included when: permissions reviewed = OFF.", { !it.permissionsReviewed }),
            RuleStep(
                step("ep2", "More Data Might Be Collected Than Needed", "Extra access can mean extra data.",
                    "Broader permissions widen what an app can read.",
                    listOf(a(EvidenceKind.RULE, "Rule: broader permission → more data in reach."), UNK_REAL_WORLD),
                    "Remove permissions an app does not need.", setOf(TwinNodeId.PERSONAL_DATA)),
                "Included when: permissions reviewed = OFF.", { !it.permissionsReviewed }),
            RuleStep(
                step("ep3", "Data Might Be Shared or Combined", "Collected data could feed wider profiles.",
                    "Sharing choices are often unclear to users.",
                    listOf(a(EvidenceKind.UNKNOWN, "What any real app shares is unknown to this simulation.")),
                    "Review data-sharing choices in each app.", setOf(TwinNodeId.PERSONAL_DATA, TwinNodeId.SOCIAL_ENGINEERING)),
                "Included when: permissions reviewed = OFF.", { !it.permissionsReviewed }),
            RuleStep(
                step("ep4", "An Exposed Account Could Reveal More", "If that app's account were reached, more would be visible.",
                    "Without 2FA, a stolen password alone might suffice in this model.",
                    listOf(a(EvidenceKind.USER_INPUT, "Two-factor authentication is OFF in the demo profile.")),
                    "Enable multifactor authentication where available.", setOf(TwinNodeId.ACCOUNT_ACCESS, TwinNodeId.PERSONAL_DATA)),
                "Included when: permissions reviewed = OFF and 2FA = OFF.", { !it.permissionsReviewed && !it.twoFactor })
        )
    )

    fun rulesFor(id: ScenarioId): List<RuleStep> = rules.getValue(id)

    /** Absent-pathway wording per scenario. Never claims all risk is gone. */
    fun noPathwayNote(id: ScenarioId): String = when (id) {
        ScenarioId.REUSED_PASSWORD -> "The demo now assumes unique passwords. The password-reuse pathway has been removed from this scenario. Other risks may remain."
        ScenarioId.EXCESSIVE_PERMISSIONS -> "The demo now assumes app permissions were reviewed. The excessive-permission pathway is not present in this scenario. Other risks may remain."
        ScenarioId.WEAK_RECOVERY -> "The demo now assumes a reviewed recovery method. The weak-recovery pathway is not present in this scenario. Other risks may remain."
        else -> "Under the current demo conditions this scenario shows no further steps. Other risks may remain."
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/simulation/SimulationEngine.kt`

```kotlin
package com.brobot.future.simulation

import com.brobot.future.model.*

/**
 * Deterministic local rules engine. No randomness, no clock, no network, no device access.
 * Same (scenario, profile) in → identical result out.
 */
object SimulationEngine {

    val interventions: List<Intervention> = listOf(
        Intervention("unique_passwords", "Use unique passwords",
            "Assume each important account now has its own password.",
            ConditionId.PASSWORD_REUSE, false,
            setOf(ScenarioId.REUSED_PASSWORD, ScenarioId.SUSPICIOUS_LINK, ScenarioId.WEAK_RECOVERY, ScenarioId.PUBLIC_WIFI)),
        Intervention("enable_2fa", "Enable multifactor authentication",
            "Assume a second sign-in step is enabled where available.",
            ConditionId.TWO_FACTOR, true,
            setOf(ScenarioId.REUSED_PASSWORD, ScenarioId.SUSPICIOUS_LINK, ScenarioId.WEAK_RECOVERY, ScenarioId.PUBLIC_WIFI, ScenarioId.EXCESSIVE_PERMISSIONS)),
        Intervention("enable_lock", "Turn on a phone lock",
            "Assume the phone is protected by a PIN, pattern or biometric lock.",
            ConditionId.PHONE_LOCK, true, setOf(ScenarioId.LOST_PHONE)),
        Intervention("review_recovery", "Review account-recovery options",
            "Assume recovery options were reviewed and are no longer easy to guess.",
            ConditionId.RECOVERY_METHOD, true,
            setOf(ScenarioId.REUSED_PASSWORD, ScenarioId.LOST_PHONE, ScenarioId.WEAK_RECOVERY)),
        Intervention("learn_links", "Learn to recognise suspicious links",
            "Assume you now check senders and link addresses before acting.",
            ConditionId.LINK_AWARENESS, true, setOf(ScenarioId.SUSPICIOUS_LINK, ScenarioId.PUBLIC_WIFI)),
        Intervention("review_permissions", "Review app permissions",
            "Assume unneeded app permissions were removed manually.",
            ConditionId.PERMISSION_REVIEW, true, setOf(ScenarioId.EXCESSIVE_PERMISSIONS))
    )

    fun intervention(id: String): Intervention = interventions.first { it.id == id }

    fun simulate(scenarioId: ScenarioId, profile: DemoProfile): SimulationResult {
        val scenario = ScenarioCatalog.scenario(scenarioId)
        val steps = ScenarioCatalog.rulesFor(scenarioId).filter { it.requires(profile) }.map { it.step }

        // Suggest interventions only if they would change something: the condition is not already in the target state.
        val suggestions = interventions.filter {
            scenarioId in it.relevantScenarios && profile.isSet(it.condition) != it.setsTo
        }

        val assumptions = buildList {
            scenario.readsConditions.forEach { c ->
                add(Assumption(EvidenceKind.USER_INPUT, "${c.label}: ${conditionStateLabel(c, profile)}."))
            }
            add(Assumption(EvidenceKind.SYNTHETIC, "All inputs are a synthetic demo profile; nothing was read from the real device."))
            add(Assumption(EvidenceKind.RULE, "Steps appear only when their documented rule matches the demo profile."))
            add(Assumption(EvidenceKind.UNKNOWN, "Real-world likelihood, timing and impact are unknown to this simulation."))
        }

        val result = SimulationResult(
            scenario = scenario, profile = profile, steps = steps, assumptions = assumptions,
            suggestedInterventions = suggestions, noPathwayNote = null
        )
        return if (result.riskStepCount == 0) {
            result.copy(noPathwayNote = ScenarioCatalog.noPathwayNote(scenarioId))
        } else result
    }

    /** Applies the intervention to the profile and returns the new profile. */
    fun apply(intervention: Intervention, profile: DemoProfile): DemoProfile =
        profile.with(intervention.condition, intervention.setsTo)

    /**
     * Runs the scenario before and after applying [intervention]. The comparison is computed from the two
     * actual engine results — nothing is hard-coded.
     */
    fun compare(scenarioId: ScenarioId, profile: DemoProfile, intervention: Intervention): Comparison {
        val before = simulate(scenarioId, profile)
        val newProfile = apply(intervention, profile)
        val after = simulate(scenarioId, newProfile)

        val changed = profile.isSet(intervention.condition) != intervention.setsTo
        val changeText = if (changed)
            "Demo condition changed: ${intervention.condition.label} — ${conditionStateLabel(intervention.condition, profile)} → ${conditionStateLabel(intervention.condition, newProfile)}."
        else
            "No demo condition changed: ${intervention.condition.label} was already ${conditionStateLabel(intervention.condition, profile)}."

        val afterIds = after.steps.map { it.id }.toSet()
        val removed = before.steps.filter { it.id !in afterIds }
        val remaining = after.steps

        val level = when {
            after.riskStepCount == 0 -> PathwayLevel.ABSENT
            after.riskStepCount < before.riskStepCount -> PathwayLevel.REDUCED
            else -> PathwayLevel.PRESENT
        }

        val summary = when {
            !changed -> "Nothing changed because the demo already assumed this protection. Other risks may remain."
            level == PathwayLevel.ABSENT -> "The pathway is not present in this simulation after the change. Other risks may remain."
            level == PathwayLevel.REDUCED -> "The pathway is reduced in this simulation (${before.riskStepCount} → ${after.riskStepCount} possible steps). Other risks may remain."
            else -> "This change did not alter the pathway for this scenario in the simulation. A different protective action may be relevant. Other risks may remain."
        }
        return Comparison(before, after, intervention, changeText, removed, remaining, level, summary)
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/simulation/TwinGraph.kt`

```kotlin
package com.brobot.future.simulation

import com.brobot.future.model.*

/** Privacy Twin node logic: which demo conditions protect which node, and the explanation text. */
object TwinGraph {

    /** For each node: protective checks (label → is protective in this profile). */
    private fun checks(node: TwinNodeId, p: DemoProfile): List<Pair<String, Boolean>> = when (node) {
        TwinNodeId.ACCOUNT_ACCESS -> listOf("Unique passwords" to !p.passwordReuse, "Two-factor authentication" to p.twoFactor)
        TwinNodeId.PERSONAL_DATA -> listOf("App permissions reviewed" to p.permissionsReviewed, "Phone lock" to p.phoneLock)
        TwinNodeId.DEVICE_SECURITY -> listOf("Phone lock" to p.phoneLock)
        TwinNodeId.SOCIAL_ENGINEERING -> listOf("Suspicious-link awareness" to p.linkAware)
        TwinNodeId.ACCOUNT_RECOVERY -> listOf("Reviewed recovery method" to p.strongRecovery, "Two-factor authentication" to p.twoFactor)
        TwinNodeId.FINANCIAL_EXPOSURE -> listOf("Unique passwords" to !p.passwordReuse, "Two-factor authentication" to p.twoFactor, "Suspicious-link awareness" to p.linkAware)
    }

    fun status(node: TwinNodeId, p: DemoProfile): NodeStatus {
        val c = checks(node, p)
        val good = c.count { it.second }
        return when (good) {
            c.size -> NodeStatus.PROTECTED
            0 -> NodeStatus.ATTENTION
            else -> NodeStatus.MIXED
        }
    }

    private val meaning = mapOf(
        TwinNodeId.ACCOUNT_ACCESS to "How sign-in to your accounts is protected: passwords and second factors.",
        TwinNodeId.PERSONAL_DATA to "Information stored on the device and in apps, and how widely apps can reach it.",
        TwinNodeId.DEVICE_SECURITY to "Whether the device itself is protected if someone else holds it.",
        TwinNodeId.SOCIAL_ENGINEERING to "How easily convincing messages or pages could lead to a mistake.",
        TwinNodeId.ACCOUNT_RECOVERY to "The routes used to regain an account — which are also routes into it.",
        TwinNodeId.FINANCIAL_EXPOSURE to "Where weaknesses elsewhere could reach money-related accounts. Always conditional."
    )

    private val links = mapOf(
        TwinNodeId.ACCOUNT_ACCESS to "Connects to Account Recovery and Financial Exposure.",
        TwinNodeId.PERSONAL_DATA to "Connects to Device Security and Account Access.",
        TwinNodeId.DEVICE_SECURITY to "Connects to Personal Data and Account Access.",
        TwinNodeId.SOCIAL_ENGINEERING to "Connects to Account Access and Financial Exposure.",
        TwinNodeId.ACCOUNT_RECOVERY to "Connects to Account Access and Device Security.",
        TwinNodeId.FINANCIAL_EXPOSURE to "Connects to Account Access and Social Engineering."
    )

    fun explanation(node: TwinNodeId, p: DemoProfile): String {
        val checkText = checks(node, p).joinToString("; ") { (n, ok) -> "$n: ${if (ok) "set" else "not set"}" }
        return "${meaning.getValue(node)}\n\n${links.getValue(node)}\n\nCurrent demo state — ${status(node, p).label}. $checkText.\n\nSynthetic demo data only: BROBOT has not inspected your real device or accounts."
    }

    val nodeOrder: List<TwinNodeId> = TwinNodeId.values().toList()
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/BrobotApp.kt`

```kotlin
package com.brobot.future.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brobot.future.domain.BrobotViewModel
import com.brobot.future.domain.JudgeDemo
import com.brobot.future.model.Destination
import com.brobot.future.ui.components.FeatureInfoDialog
import com.brobot.future.ui.components.PrimaryActionButton
import com.brobot.future.ui.screens.*
import com.brobot.future.ui.theme.BrobotColors
import com.brobot.future.ui.theme.LocalMotionEnabled

private data class Tab(val dest: Destination, val label: String, val icon: ImageVector)

private val tabs = listOf(
    Tab(Destination.HOME, "Home", Icons.Default.Home),
    Tab(Destination.TWIN, "Twin", Icons.Default.Person),
    Tab(Destination.SIMULATE, "Simulate", Icons.Default.PlayArrow),
    Tab(Destination.UNIVERSE, "Universe", Icons.Default.Star),
    Tab(Destination.SETTINGS, "Settings", Icons.Default.Settings)
)

@Composable
fun BrobotApp(vm: BrobotViewModel) {
    val snackbar = remember { SnackbarHostState() }
    val motion = LocalMotionEnabled.current

    LaunchedEffect(vm.message) { vm.message?.let { snackbar.showSnackbar(it.text) } }
    // Android Back: pops screens (open dialogs consume Back first, then the stack pops).
    BackHandler(enabled = vm.backStack.size > 1) { vm.back() }

    Scaffold(
        containerColor = BrobotColors.Bg,
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = { if (vm.current != Destination.INTRO) BrobotBottomBar(vm) }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            Box(Modifier.weight(1f)) {
                Crossfade(
                    targetState = vm.current,
                    animationSpec = tween(if (motion) 250 else 0),
                    label = "screens"
                ) { dest -> ScreenFor(dest, vm) }
            }
            vm.demoStep?.let { step ->
                JudgeDemoBar(step, onBack = vm::previousDemoStep, onNext = vm::nextDemoStep, onExit = vm::exitJudgeDemo)
            }
        }
    }

    vm.featureDialog?.let { info ->
        FeatureInfoDialog(info, onAction = { vm.followFeature(info) }, onDismiss = vm::dismissFeature)
    }
}

@Composable
private fun ScreenFor(dest: Destination, vm: BrobotViewModel) {
    when (dest) {
        Destination.INTRO -> IntroScreen(onEnter = vm::enterApp, onExploreDemo = vm::startJudgeDemo)
        Destination.HOME -> HomeScreen(
            profile = vm.profile,
            onTag = vm::openFeature,
            onNavigate = vm::navigate,
            onHowItWorks = { vm.openFeature("Privacy Twin") },
            onStartJudgeDemo = vm::startJudgeDemo
        )
        Destination.TWIN -> TwinScreen(
            profile = vm.profile, onToggle = vm::toggleCondition, onReset = vm::resetDemoProfile,
            onInfo = { vm.openFeatureById("twin") }, onTag = vm::openFeature, onNavigate = vm::navigate
        )
        Destination.SIMULATE -> SimulateScreen(
            profile = vm.profile, selected = vm.selectedScenario, result = vm.result,
            onSelect = vm::selectScenario, onSimulate = vm::runScenario, onToggle = vm::toggleCondition,
            onInfo = { vm.openFeatureById("whatif") }, onNavigate = vm::navigate
        )
        Destination.UNIVERSE -> UniverseScreen(onInfo = { vm.openFeatureById("universe") }, onTag = vm::openFeature)
        Destination.SETTINGS -> SettingsScreen(
            largeText = vm.largeText, onLargeText = { vm.largeText = it },
            reduceMotion = vm.reduceMotion, onReduceMotion = { vm.reduceMotion = it },
            onResetAll = vm::resetAll, onClearHistory = vm::clearHistory
        )
        Destination.BUTTERFLY -> ButterflyScreen(
            result = vm.result, selected = vm.selectedScenario, replayKey = vm.replayKey,
            inspectStepId = vm.inspectStepId, onInspect = { vm.inspectStepId = it },
            onReplay = vm::replay, onRun = vm::runScenario,
            onInfo = { vm.openFeatureById("butterfly") }, onNavigate = vm::navigate
        )
        Destination.INTERVENTION -> InterventionScreen(
            result = vm.result, comparison = vm.comparison, profile = vm.profile,
            selectedId = vm.selectedInterventionId, onSelect = { vm.selectedInterventionId = it },
            onApply = vm::applyIntervention, onReset = vm::resetDemoProfile,
            onInfo = { vm.openFeatureById("whatif") }, onNavigate = vm::navigate
        )
        Destination.EXPLAINER -> ExplainerScreen(
            result = vm.result, question = vm.explainerQuestion,
            onQuestion = { vm.explainerQuestion = it }, onRun = vm::runScenario,
            onInfo = { vm.openFeatureById("explainer") }
        )
        Destination.AUTOPILOT -> AutopilotScreen(vm.completedTasks, vm::toggleTask, onInfo = { vm.openFeatureById("autopilot") })
        Destination.SHADOW -> ShadowScreen(vm.notes, vm::addNote, vm::removeNote, onInfo = { vm.openFeatureById("shadow") })
        Destination.TIMELINE -> TimelineScreen(vm.timeline, vm::clearHistory, onInfo = { vm.openFeatureById("timeline") })
        Destination.JUDGE_DEMO -> Unit
    }
}

@Composable
private fun BrobotBottomBar(vm: BrobotViewModel) {
    NavigationBar(containerColor = BrobotColors.Surface) {
        tabs.forEach { t ->
            NavigationBarItem(
                selected = vm.selectedTab == t.dest,
                onClick = { vm.navigate(t.dest) },
                icon = { Icon(t.icon, contentDescription = t.label) },
                label = { Text(t.label, fontSize = 12.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BrobotColors.Lavender,
                    selectedTextColor = BrobotColors.Lavender,
                    indicatorColor = BrobotColors.VioletDeep,
                    unselectedIconColor = BrobotColors.TextSecondary,
                    unselectedTextColor = BrobotColors.TextSecondary
                )
            )
        }
    }
}

@Composable
private fun JudgeDemoBar(step: Int, onBack: () -> Unit, onNext: () -> Unit, onExit: () -> Unit) {
    val s = JudgeDemo.steps[step]
    val last = step == JudgeDemo.steps.lastIndex
    Column(
        Modifier
            .fillMaxWidth()
            .background(BrobotColors.SurfaceHigh)
            .border(1.dp, BrobotColors.Violet.copy(alpha = 0.6f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "JUDGE DEMO · ${step + 1}/${JudgeDemo.steps.size} · ${s.timing}",
                Modifier.weight(1f), color = BrobotColors.Cyan, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp
            )
            TextButton(onClick = onExit, modifier = Modifier.padding(0.dp)) { Text("Exit", color = BrobotColors.Lavender) }
        }
        Text(s.title, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
        Text(s.body, style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryActionButton("Back", onBack, Modifier.weight(1f), secondary = true, enabled = step > 0)
            PrimaryActionButton(if (last) "Finish" else "Next", onNext, Modifier.weight(1f))
        }
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/components/Components.kt`

```kotlin
package com.brobot.future.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brobot.future.model.Assumption
import com.brobot.future.model.EvidenceKind
import com.brobot.future.model.FeatureInfo
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun FuturisticCard(
    modifier: Modifier = Modifier,
    accent: Color = BrobotColors.Violet,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(BrobotColors.Surface, shape)
            .border(1.dp, accent.copy(alpha = 0.35f), shape)
            .then(if (onClick != null) Modifier.clickable(role = Role.Button, onClick = onClick) else Modifier)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content
    )
}

@Composable
fun FeatureChip(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, selected: Boolean = false) {
    val shape = RoundedCornerShape(50)
    Row(
        modifier = modifier
            .heightIn(min = 48.dp)
            .clip(shape)
            .background(if (selected) BrobotColors.VioletDeep else BrobotColors.SurfaceHigh, shape)
            .border(1.dp, if (selected) BrobotColors.Lavender else BrobotColors.Violet.copy(alpha = 0.5f), shape)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Info, contentDescription = null, tint = BrobotColors.Cyan, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.labelLarge, color = BrobotColors.TextPrimary)
    }
}

@Composable
fun DemoStatusBadge(text: String = "DEMO MODE · SYNTHETIC DATA", color: Color = BrobotColors.Cyan) {
    val shape = RoundedCornerShape(50)
    Row(
        modifier = Modifier
            .clip(shape)
            .background(color.copy(alpha = 0.12f), shape)
            .border(1.dp, color.copy(alpha = 0.5f), shape)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(8.dp).background(color, CircleShape))
        Spacer(Modifier.width(8.dp))
        Text(text, color = color, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.8.sp)
    }
}

@Composable
fun PrimaryActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    secondary: Boolean = false,
    enabled: Boolean = true
) {
    if (secondary) {
        OutlinedButton(
            onClick = onClick, enabled = enabled,
            modifier = modifier.heightIn(min = 48.dp),
            border = BorderStroke(1.dp, BrobotColors.Lavender.copy(alpha = if (enabled) 0.8f else 0.3f))
        ) { Text(text, color = if (enabled) BrobotColors.Lavender else BrobotColors.TextSecondary) }
    } else {
        Button(
            onClick = onClick, enabled = enabled,
            modifier = modifier.heightIn(min = 48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrobotColors.Violet, contentColor = Color.White,
                disabledContainerColor = BrobotColors.VioletDeep, disabledContentColor = BrobotColors.TextSecondary
            )
        ) { Text(text, fontWeight = FontWeight.SemiBold) }
    }
}

@Composable
fun ScreenColumn(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = content
    )
}

@Composable
fun ScreenHeader(title: String, subtitle: String, onInfo: (() -> Unit)? = null) {
    Row(verticalAlignment = Alignment.Top) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = BrobotColors.TextSecondary)
        }
        if (onInfo != null) {
            IconButton(onClick = onInfo) {
                Icon(Icons.Default.Info, contentDescription = "About this feature", tint = BrobotColors.Cyan)
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(text.uppercase(), style = MaterialTheme.typography.labelMedium, color = BrobotColors.Lavender, letterSpacing = 1.2.sp, fontWeight = FontWeight.SemiBold)
}

fun evidenceColor(kind: EvidenceKind): Color = when (kind) {
    EvidenceKind.USER_INPUT -> BrobotColors.Cyan
    EvidenceKind.SYNTHETIC -> BrobotColors.Lavender
    EvidenceKind.RULE -> BrobotColors.Violet
    EvidenceKind.UNKNOWN -> BrobotColors.Amber
    EvidenceKind.SUGGESTION -> BrobotColors.Teal
}

@Composable
fun EvidenceBadge(kind: EvidenceKind) {
    val c = evidenceColor(kind)
    Text(
        kind.label.uppercase(), color = c, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.6.sp,
        modifier = Modifier
            .background(c.copy(alpha = 0.14f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

@Composable
fun AssumptionList(items: List<Assumption>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items.forEach {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                EvidenceBadge(it.kind)
                Text(it.text, style = MaterialTheme.typography.bodyMedium, color = BrobotColors.TextPrimary)
            }
        }
    }
}

@Composable
private fun DialogSections(sections: List<Pair<String, String>>) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        sections.forEach { (label, body) ->
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(label.uppercase(), color = BrobotColors.Lavender, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
                Text(body, color = BrobotColors.TextPrimary, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

/** Reusable pop-up for every feature tag. "Got It" closes; the action button navigates. */
@Composable
fun FeatureInfoDialog(info: FeatureInfo, onAction: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = BrobotColors.SurfaceHigh,
        titleContentColor = BrobotColors.TextPrimary,
        textContentColor = BrobotColors.TextPrimary,
        title = { Text(info.dialogTitle, fontWeight = FontWeight.Bold) },
        text = {
            DialogSections(
                listOf(
                    "What it is" to info.whatItIs,
                    "What it does" to info.whatItDoes,
                    "Problem it solves" to info.problemSolved,
                    "How it could work in 2035" to info.vision2035,
                    "What this prototype shows" to info.prototypeDemonstrates,
                    "Prototype status — simulated / not implemented" to info.simulatedOrMissing
                )
            )
        },
        confirmButton = { PrimaryActionButton(info.actionLabel, onAction) },
        dismissButton = { TextButton(onClick = onDismiss, modifier = Modifier.heightIn(min = 48.dp)) { Text("Got It", color = BrobotColors.Lavender) } }
    )
}

@Composable
fun InfoDialog(
    title: String,
    sections: List<Pair<String, String>>,
    onDismiss: () -> Unit,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = BrobotColors.SurfaceHigh,
        titleContentColor = BrobotColors.TextPrimary,
        textContentColor = BrobotColors.TextPrimary,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = { DialogSections(sections) },
        confirmButton = {
            if (actionLabel != null && onAction != null) PrimaryActionButton(actionLabel, onAction)
            else PrimaryActionButton("Got It", onDismiss)
        },
        dismissButton = {
            if (actionLabel != null && onAction != null)
                TextButton(onClick = onDismiss, modifier = Modifier.heightIn(min = 48.dp)) { Text("Got It", color = BrobotColors.Lavender) }
        }
    )
}

@Composable
fun ConfirmDialog(title: String, text: String, confirmLabel: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = BrobotColors.SurfaceHigh,
        titleContentColor = BrobotColors.TextPrimary,
        textContentColor = BrobotColors.TextSecondary,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = { Text(text) },
        confirmButton = { PrimaryActionButton(confirmLabel, onConfirm) },
        dismissButton = { TextButton(onClick = onDismiss, modifier = Modifier.heightIn(min = 48.dp)) { Text("Cancel", color = BrobotColors.Lavender) } }
    )
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/components/ConstellationGraph.kt`

```kotlin
package com.brobot.future.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brobot.future.ui.theme.BrobotColors
import com.brobot.future.ui.theme.LocalMotionEnabled

data class GraphNode(
    val id: String,
    val label: String,
    /** Normalised 0..1 position. */
    val x: Float,
    val y: Float,
    val color: Color = BrobotColors.Violet,
    val emphasized: Boolean = false,
    val dimmed: Boolean = false,
    val big: Boolean = false,
    val description: String = label
)

data class GraphEdge(val a: String, val b: String, val highlighted: Boolean = false)

/** Tappable node graph drawn with Canvas; edges flow slowly unless motion is reduced. */
@Composable
fun ConstellationGraph(
    nodes: List<GraphNode>,
    edges: List<GraphEdge>,
    onNodeClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 380.dp
) {
    val motion = LocalMotionEnabled.current
    val phase: Float = if (motion) {
        rememberInfiniteTransition(label = "edgeFlow").animateFloat(
            initialValue = 0f, targetValue = 48f,
            animationSpec = infiniteRepeatable(tween(2600, easing = LinearEasing), RepeatMode.Restart),
            label = "phase"
        ).value
    } else 0f

    BoxWithConstraints(modifier.fillMaxWidth().height(height)) {
        val w = maxWidth
        val h = maxHeight
        Canvas(Modifier.fillMaxSize()) {
            edges.forEach { e ->
                val a = nodes.firstOrNull { it.id == e.a }
                val b = nodes.firstOrNull { it.id == e.b }
                if (a != null && b != null) {
                    drawLine(
                        color = if (e.highlighted) BrobotColors.Lavender.copy(alpha = 0.95f) else BrobotColors.Violet.copy(alpha = 0.35f),
                        start = Offset(a.x * size.width, a.y * size.height),
                        end = Offset(b.x * size.width, b.y * size.height),
                        strokeWidth = if (e.highlighted) 3.dp.toPx() else 1.5.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), phase)
                    )
                }
            }
        }
        nodes.forEach { n ->
            val circle = if (n.big) 64.dp else 52.dp
            NodeBubble(
                node = n,
                circle = circle,
                onClick = { onNodeClick(n.id) },
                modifier = Modifier.offset(x = w * n.x - 44.dp, y = h * n.y - circle / 2)
            )
        }
    }
}

@Composable
private fun NodeBubble(node: GraphNode, circle: Dp, onClick: () -> Unit, modifier: Modifier) {
    val border by animateColorAsState(
        targetValue = if (node.emphasized) BrobotColors.Lavender else node.color.copy(alpha = 0.8f),
        label = "nodeBorder"
    )
    val fill by animateColorAsState(
        targetValue = node.color.copy(alpha = if (node.emphasized) 0.55f else 0.28f),
        label = "nodeFill"
    )
    Column(
        modifier = modifier
            .width(88.dp)
            .alpha(if (node.dimmed) 0.45f else 1f)
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { contentDescription = node.description },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(circle)
                .background(fill, CircleShape)
                .border(if (node.emphasized) 3.dp else 2.dp, border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.size(if (node.big) 18.dp else 14.dp).background(border, CircleShape))
        }
        Text(
            node.label,
            color = BrobotColors.TextPrimary,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            fontWeight = if (node.emphasized) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 3
        )
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/components/Shared.kt`

```kotlin
@file:OptIn(ExperimentalLayoutApi::class)

package com.brobot.future.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.model.ConditionId
import com.brobot.future.model.DemoProfile
import com.brobot.future.model.PathwayStep
import com.brobot.future.model.Scenario
import com.brobot.future.model.conditionStateLabel
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun FeatureTagRow(tags: List<String>, onTag: (String) -> Unit) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { FeatureChip(it, onClick = { onTag(it) }) }
    }
}

/** Switches that edit ONLY the local simulated profile. */
@Composable
fun ConditionsPanel(profile: DemoProfile, onToggle: (ConditionId, Boolean) -> Unit) {
    FuturisticCard {
        SectionTitle("Demo conditions")
        Text("These switches change only the simulated profile. Nothing real is read or changed.", style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
        ConditionId.values().forEach { id ->
            Row(
                Modifier.fillMaxWidth().heightIn(min = 56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f).padding(end = 12.dp)) {
                    Text(id.label, fontWeight = FontWeight.SemiBold, color = BrobotColors.TextPrimary)
                    Text(conditionStateLabel(id, profile), style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
                }
                Switch(checked = profile.isSet(id), onCheckedChange = { onToggle(id, it) })
            }
        }
    }
}

@Composable
fun ScenarioCard(scenario: Scenario, selected: Boolean, onSelect: () -> Unit, onSimulate: () -> Unit) {
    FuturisticCard(
        accent = if (selected) BrobotColors.Lavender else BrobotColors.Violet,
        onClick = onSelect
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(scenario.title.uppercase(), Modifier.weight(1f), fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
            if (selected) Text("SELECTED", color = BrobotColors.Teal, style = MaterialTheme.typography.labelMedium)
        }
        Text(scenario.summary, style = MaterialTheme.typography.bodyMedium, color = BrobotColors.TextSecondary)
        PrimaryActionButton("Simulate", onSimulate, Modifier.fillMaxWidth())
    }
}

@Composable
fun StepSummaryList(steps: List<PathwayStep>, struck: Boolean = false) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        steps.forEachIndexed { i, s ->
            Text(
                "${i + 1}. ${s.title}",
                style = MaterialTheme.typography.bodyMedium,
                color = if (struck) BrobotColors.TextSecondary else BrobotColors.TextPrimary
            )
        }
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/AutopilotScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.data.AutopilotCatalog
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun AutopilotScreen(completed: Set<String>, onToggle: (String, Boolean) -> Unit, onInfo: () -> Unit) {
    val total = AutopilotCatalog.tasks.size
    val done = AutopilotCatalog.tasks.count { it.id in completed }
    ScreenColumn {
        ScreenHeader("Privacy Autopilot", "A guided checklist you complete yourself.", onInfo)
        DemoStatusBadge("GUIDED DEMO — NO AUTOMATIC DEVICE CHANGES", BrobotColors.Amber)

        FuturisticCard(accent = BrobotColors.Teal) {
            Text("$done of $total complete", fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
            LinearProgressIndicator(
                progress = { done / total.toFloat() },
                modifier = Modifier.fillMaxWidth(), color = BrobotColors.Teal, trackColor = BrobotColors.VioletDeep
            )
            Text("Checking a box only records it in this demo session. BROBOT never changes your real security settings.", style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
        }

        AutopilotCatalog.tasks.forEach { t ->
            val checked = t.id in completed
            FuturisticCard(accent = if (checked) BrobotColors.Teal else BrobotColors.Violet, onClick = { onToggle(t.id, !checked) }) {
                Row(Modifier.fillMaxWidth().heightIn(min = 48.dp), verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checked, onCheckedChange = { onToggle(t.id, it) })
                    Column(Modifier.padding(start = 8.dp)) {
                        Text(t.title, fontWeight = FontWeight.SemiBold, color = BrobotColors.TextPrimary)
                        Text(t.detail, style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
                    }
                }
            }
        }
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/ButterflyScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.model.Destination
import com.brobot.future.model.PathwayStep
import com.brobot.future.model.ScenarioId
import com.brobot.future.model.SimulationResult
import com.brobot.future.simulation.ExplainerEngine
import com.brobot.future.simulation.ScenarioCatalog
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors
import com.brobot.future.ui.theme.LocalMotionEnabled
import kotlinx.coroutines.delay

@Composable
fun ButterflyScreen(
    result: SimulationResult?,
    selected: ScenarioId?,
    replayKey: Int,
    inspectStepId: String?,
    onInspect: (String?) -> Unit,
    onReplay: () -> Unit,
    onRun: (ScenarioId) -> Unit,
    onInfo: () -> Unit,
    onNavigate: (Destination) -> Unit
) {
    var showChainExplain by rememberSaveable { mutableStateOf(false) }

    ScreenColumn {
        ScreenHeader(
            "Privacy Butterfly Effect",
            "One small digital weakness can create several possible downstream consequences. Explore the connections before they happen.",
            onInfo
        )
        DemoStatusBadge("HYPOTHETICAL · CONDITIONAL · NOT A DETECTED COMPROMISE", BrobotColors.Amber)

        // Scenario switcher — every chip re-runs the local rules.
        SectionTitle("Scenario")
        FeatureTagRow(ScenarioCatalog.scenarios.map { it.title }) { title ->
            ScenarioCatalog.scenarios.firstOrNull { it.title == title }?.let { onRun(it.id) }
        }

        if (result == null || result.scenario.id != selected) {
            FuturisticCard {
                Text("No pathway yet", fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                Text("Choose a scenario above to run the local rules and reveal its pathway.", color = BrobotColors.TextSecondary)
                PrimaryActionButton("Run “Reused Password”", { onRun(ScenarioId.REUSED_PASSWORD) }, Modifier.fillMaxWidth())
            }
        } else {
            Text(result.scenario.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
            Text(result.level.label, color = if (result.riskStepCount > 0) BrobotColors.Amber else BrobotColors.Teal, fontWeight = FontWeight.SemiBold)

            if (result.noPathwayNote != null) {
                FuturisticCard(accent = BrobotColors.Teal) {
                    Text(result.noPathwayNote!!, color = BrobotColors.TextPrimary)
                }
            }
            Column {
                result.steps.forEachIndexed { i, step ->
                    PathwayNode(i, step, replayKey, isLast = i == result.steps.lastIndex, onClick = { onInspect(step.id) })
                }
            }
            Text("Tap any step to inspect what it represents, why it connects, its assumptions and what could reduce it.", style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PrimaryActionButton("Replay Pathway", onReplay, Modifier.weight(1f))
                PrimaryActionButton("Explain This Chain", { showChainExplain = true }, Modifier.weight(1f), secondary = true)
            }
            PrimaryActionButton("Choose Protective Action", { onNavigate(Destination.INTERVENTION) }, Modifier.fillMaxWidth(), secondary = true)
        }
    }

    val step = result?.steps?.firstOrNull { it.id == inspectStepId }
    val idx = result?.steps?.indexOfFirst { it.id == inspectStepId } ?: -1
    if (step != null && result != null) {
        InfoDialog(
            title = step.title,
            sections = listOf(
                "What this node represents" to step.represents,
                "Why it may connect to the previous node" to
                    if (idx == 0) "It is the starting point of this hypothetical pathway. ${step.whyConnected}" else step.whyConnected,
                "Assumptions used" to step.assumptions.joinToString("\n") { "• [${it.kind.label}] ${it.text}" },
                "Action that might reduce the risk" to step.reduceAction,
                "Reminder" to "Hypothetical and conditional. This does not mean the step has happened or will happen."
            ),
            onDismiss = { onInspect(null) }
        )
    }
    if (showChainExplain && result != null) {
        InfoDialog(
            title = "Explain This Chain",
            sections = ExplainerEngine.explainChain(result),
            onDismiss = { showChainExplain = false },
            actionLabel = "Open Intelligence",
            onAction = { showChainExplain = false; onNavigate(Destination.EXPLAINER) }
        )
    }
}

@Composable
private fun PathwayNode(index: Int, step: PathwayStep, replayKey: Int, isLast: Boolean, onClick: () -> Unit) {
    val motion = LocalMotionEnabled.current
    var visible by remember(replayKey, step.id) { mutableStateOf(!motion) }
    LaunchedEffect(replayKey, step.id, motion) {
        if (motion) {
            visible = false
            delay(index * 400L)
            visible = true
        } else visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(350)) + expandVertically(tween(350)),
        exit = fadeOut(tween(150))
    ) {
        Column {
            FuturisticCard(accent = if (step.isTrigger) BrobotColors.Lavender else BrobotColors.Amber, onClick = onClick) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(32.dp).background(if (step.isTrigger) BrobotColors.Violet else BrobotColors.Amber.copy(alpha = 0.85f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) { Text("${index + 1}", color = BrobotColors.Bg, fontWeight = FontWeight.Bold) }
                    Column(Modifier.padding(start = 12.dp)) {
                        Text(step.title, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                        Text(if (step.isTrigger) "Starting point · tap to inspect" else "Possible · tap to inspect", style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
                    }
                }
            }
            if (!isLast) {
                Box(Modifier.padding(start = 31.dp).width(2.dp).height(18.dp).background(BrobotColors.Violet.copy(alpha = 0.6f)))
            }
        }
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/ExplainerScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.brobot.future.model.ScenarioId
import com.brobot.future.model.SimulationResult
import com.brobot.future.simulation.ExplainerEngine
import com.brobot.future.simulation.ScenarioCatalog
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun ExplainerScreen(
    result: SimulationResult?,
    question: String?,
    onQuestion: (String) -> Unit,
    onRun: (ScenarioId) -> Unit,
    onInfo: () -> Unit
) {
    ScreenColumn {
        ScreenHeader("BROBOT INTELLIGENCE", "Understand the reason behind every simulated risk.", onInfo)
        DemoStatusBadge("LOCAL RULES · NO LIVE AI MODEL CONNECTED", BrobotColors.Amber)
        Text(
            "These answers are assembled from this simulation's own rules and scenario data. They are not written by an AI model, and every statement is labelled by where it comes from.",
            style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary
        )

        SectionTitle("Scenario")
        FeatureTagRow(ScenarioCatalog.scenarios.map { it.title }) { title ->
            ScenarioCatalog.scenarios.firstOrNull { it.title == title }?.let { onRun(it.id) }
        }

        if (result == null) {
            FuturisticCard {
                Text("No scenario selected", fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                Text("Pick a scenario above, then ask a question.", color = BrobotColors.TextSecondary)
                PrimaryActionButton("Use “Reused Password”", { onRun(ScenarioId.REUSED_PASSWORD) }, Modifier.fillMaxWidth())
            }
            return@ScreenColumn
        }

        Text("Scenario: ${result.scenario.title} · ${result.level.label}", fontWeight = FontWeight.SemiBold, color = BrobotColors.TextPrimary)

        SectionTitle("Ask a question")
        ExplainerEngine.questions.forEach { q ->
            PrimaryActionButton(q, { onQuestion(q) }, Modifier.fillMaxWidth(), secondary = q != question)
        }

        if (question != null) {
            val answer = ExplainerEngine.answer(question, result)
            FuturisticCard(accent = BrobotColors.Cyan) {
                Text(answer.question, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                AssumptionList(answer.parts)
            }
        } else {
            Text("Choose one of the questions above.", color = BrobotColors.TextSecondary)
        }
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/HomeScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.data.FeatureCatalog
import com.brobot.future.model.ConditionId
import com.brobot.future.model.DemoProfile
import com.brobot.future.model.Destination
import com.brobot.future.ui.components.DemoStatusBadge
import com.brobot.future.ui.components.FeatureTagRow
import com.brobot.future.ui.components.FuturisticCard
import com.brobot.future.ui.components.PrimaryActionButton
import com.brobot.future.ui.components.ScreenColumn
import com.brobot.future.ui.components.SectionTitle
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun HomeScreen(
    profile: DemoProfile,
    onTag: (String) -> Unit,
    onNavigate: (Destination) -> Unit,
    onHowItWorks: () -> Unit,
    onStartJudgeDemo: () -> Unit
) {
    ScreenColumn {
        DemoStatusBadge()
        Text("Good to see you.", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
        Text("Your privacy universe, reimagined.", style = MaterialTheme.typography.bodyLarge, color = BrobotColors.TextSecondary)

        FuturisticCard(accent = BrobotColors.Lavender) {
            SectionTitle("Signature feature")
            Text("BROBOT PRIVACY TWIN", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = BrobotColors.TextPrimary)
            Text("Explore what could happen. Discover what you can change.", style = MaterialTheme.typography.bodyMedium, color = BrobotColors.TextSecondary)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PrimaryActionButton("Launch Simulation", { onNavigate(Destination.TWIN) }, Modifier.weight(1f))
                PrimaryActionButton("How It Works", onHowItWorks, Modifier.weight(1f), secondary = true)
            }
        }

        PrimaryActionButton("Start Judge Demo (≈90 s)", onStartJudgeDemo, Modifier.fillMaxWidth(), secondary = true)

        SectionTitle("Futuristic features — tap a tag")
        FeatureTagRow(FeatureCatalog.features.map { it.tag }, onTag)

        val protective = ConditionId.values().count {
            // "Protective" = password reuse OFF, all others ON
            if (it == ConditionId.PASSWORD_REUSE) !profile.isSet(it) else profile.isSet(it)
        }
        FuturisticCard {
            SectionTitle("Demo overview")
            Text("Protective demo conditions set: $protective of ${ConditionId.values().size}", color = BrobotColors.TextPrimary, fontWeight = FontWeight.SemiBold)
            LinearProgressIndicator(
                progress = { protective / ConditionId.values().size.toFloat() },
                modifier = Modifier.fillMaxWidth(),
                color = BrobotColors.Teal,
                trackColor = BrobotColors.VioletDeep
            )
            Text(
                "DEMO SCORE — a simple count of switches in the synthetic profile. It is not a security assessment, not a probability, and nothing was scanned.",
                style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary
            )
        }
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/InterventionScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.model.Comparison
import com.brobot.future.model.DemoProfile
import com.brobot.future.model.Destination
import com.brobot.future.model.PathwayLevel
import com.brobot.future.model.SimulationResult
import com.brobot.future.model.TwinNodeId
import com.brobot.future.simulation.TwinGraph
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun InterventionScreen(
    result: SimulationResult?,
    comparison: Comparison?,
    profile: DemoProfile,
    selectedId: String?,
    onSelect: (String) -> Unit,
    onApply: () -> Unit,
    onReset: () -> Unit,
    onInfo: () -> Unit,
    onNavigate: (Destination) -> Unit
) {
    ScreenColumn {
        ScreenHeader("Risk Intervention", "Apply a protective action to the demo profile, re-run the rules and compare.", onInfo)
        DemoStatusBadge("DEMO INTERVENTION · NO REAL SETTINGS CHANGE")

        if (result == null) {
            FuturisticCard {
                Text("No scenario simulated yet", fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                Text("Run a scenario first, then come back to choose a protective action.", color = BrobotColors.TextSecondary)
                PrimaryActionButton("Choose a Scenario", { onNavigate(Destination.SIMULATE) }, Modifier.fillMaxWidth())
            }
            return@ScreenColumn
        }

        Text("Scenario: ${result.scenario.title}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
        Text(result.level.label, color = if (result.riskStepCount > 0) BrobotColors.Amber else BrobotColors.Teal, fontWeight = FontWeight.SemiBold)

        SectionTitle("Suggested protective actions")
        if (result.suggestedInterventions.isEmpty()) {
            FuturisticCard(accent = BrobotColors.Teal) {
                Text("The demo protections relevant to this scenario are already set. Other risks may remain.", color = BrobotColors.TextPrimary)
            }
        } else {
            result.suggestedInterventions.forEach { iv ->
                val sel = iv.id == selectedId
                FuturisticCard(accent = if (sel) BrobotColors.Teal else BrobotColors.Violet, onClick = { onSelect(iv.id) }) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(iv.title, Modifier.weight(1f), fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                        if (sel) Text("SELECTED", color = BrobotColors.Teal, style = MaterialTheme.typography.labelMedium)
                    }
                    Text(iv.description, color = BrobotColors.TextSecondary, style = MaterialTheme.typography.bodyMedium)
                    Text("Changes demo condition: ${iv.condition.label}", color = BrobotColors.Lavender, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        PrimaryActionButton("Apply Demo Intervention", onApply, Modifier.fillMaxWidth(), enabled = selectedId != null)

        if (comparison != null) {
            ComparisonSection(comparison)
        }

        PrimaryActionButton("Reset Demo Profile", onReset, Modifier.fillMaxWidth(), secondary = true)
        PrimaryActionButton("Ask BROBOT Intelligence", { onNavigate(Destination.EXPLAINER) }, Modifier.fillMaxWidth(), secondary = true)
    }
}

@Composable
private fun ComparisonSection(c: Comparison) {
    var nodeName by rememberSaveable { mutableStateOf<String?>(null) }

    SectionTitle("Before vs after")
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FuturisticCard(Modifier.weight(1f), accent = BrobotColors.Amber) {
            Text("BEFORE", color = BrobotColors.Amber, fontWeight = FontWeight.Bold)
            Text("${c.before.riskStepCount} possible step(s)", color = BrobotColors.TextPrimary, fontWeight = FontWeight.SemiBold)
            StepSummaryList(c.before.steps)
        }
        FuturisticCard(Modifier.weight(1f), accent = BrobotColors.Teal) {
            Text("AFTER", color = BrobotColors.Teal, fontWeight = FontWeight.Bold)
            Text("${c.after.riskStepCount} possible step(s)", color = BrobotColors.TextPrimary, fontWeight = FontWeight.SemiBold)
            if (c.after.steps.isEmpty()) Text("No steps in this simulation.", color = BrobotColors.TextSecondary)
            else StepSummaryList(c.after.steps)
        }
    }

    FuturisticCard(accent = BrobotColors.Teal) {
        DemoStatusBadge(c.after.label, BrobotColors.Teal)
        Text(c.level.label, color = if (c.level == PathwayLevel.PRESENT) BrobotColors.Amber else BrobotColors.Teal, fontWeight = FontWeight.Bold)
        Text(c.changeDescription, color = BrobotColors.TextPrimary)
        Text(c.summary, color = BrobotColors.TextSecondary)
        if (c.removedSteps.isNotEmpty()) {
            SectionTitle("No longer present in this simulation")
            StepSummaryList(c.removedSteps, struck = true)
        }
        c.after.noPathwayNote?.let { Text(it, color = BrobotColors.TextPrimary) }
    }

    SectionTitle("Affected nodes (tap one)")
    val afterNodes = c.after.affectedNodes
    val beforeNodes = c.before.affectedNodes
    val nodes = TwinNodeId.values().map { n ->
        val (x, y) = twinPositions.getValue(n)
        val color = when {
            n in afterNodes -> BrobotColors.Amber
            n in beforeNodes -> BrobotColors.Teal
            else -> BrobotColors.Violet
        }
        GraphNode(n.name, n.label, x, y, color, emphasized = n in afterNodes || n in beforeNodes,
            description = "${n.label}: " + when {
                n in afterNodes -> "still involved in this simulation"
                n in beforeNodes -> "no longer involved in this simulation"
                else -> "not involved in this scenario"
            })
    } + GraphNode("center", "YOUR DIGITAL IDENTITY", 0.5f, 0.48f, BrobotColors.Lavender, big = true)
    val edges = TwinNodeId.values().map { GraphEdge("center", it.name, highlighted = it in afterNodes) }
    FuturisticCard {
        ConstellationGraph(nodes, edges, onNodeClick = { if (it != "center") nodeName = it })
        Text("Amber = still involved · Teal = no longer involved in this simulation · Violet = not involved.", style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
    }

    val tapped = nodeName?.let { TwinNodeId.valueOf(it) }
    if (tapped != null) {
        InfoDialog(
            title = tapped.label,
            sections = listOf(
                "In this comparison" to when {
                    tapped in afterNodes -> "Still involved after the change (other risks may remain)."
                    tapped in beforeNodes -> "Involved before the change, no longer involved in this simulation."
                    else -> "Not involved in this scenario."
                },
                "Current demo state" to TwinGraph.explanation(tapped, c.after.profile)
            ),
            onDismiss = { nodeName = null }
        )
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/IntroScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brobot.future.ui.components.DemoStatusBadge
import com.brobot.future.ui.components.PrimaryActionButton
import com.brobot.future.ui.theme.BrobotColors
import com.brobot.future.ui.theme.LocalMotionEnabled
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun IntroScreen(onEnter: () -> Unit, onExploreDemo: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(16.dp))
        ShieldConstellation(Modifier.size(220.dp))
        Spacer(Modifier.height(24.dp))
        Text("BROBOT", fontSize = 44.sp, fontWeight = FontWeight.Black, letterSpacing = 8.sp, color = BrobotColors.TextPrimary)
        Text("2035", fontSize = 18.sp, letterSpacing = 6.sp, color = BrobotColors.Lavender)
        Spacer(Modifier.height(12.dp))
        Text(
            "YOUR DIGITAL LIFE. UNDER YOUR CONTROL.",
            style = MaterialTheme.typography.labelLarge, letterSpacing = 1.5.sp,
            color = BrobotColors.Cyan, textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text("Explore the future of personal privacy.", style = MaterialTheme.typography.bodyLarge, color = BrobotColors.TextSecondary, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        DemoStatusBadge("CONCEPT PROTOTYPE · SYNTHETIC DATA")
        Spacer(Modifier.height(28.dp))
        PrimaryActionButton("Enter BROBOT", onEnter, Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        PrimaryActionButton("Explore Demo", onExploreDemo, Modifier.fillMaxWidth(), secondary = true)
    }
}

@Composable
private fun ShieldConstellation(modifier: Modifier) {
    val motion = LocalMotionEnabled.current
    val angle: Float = if (motion) {
        rememberInfiniteTransition(label = "intro").animateFloat(
            0f, 360f, infiniteRepeatable(tween(24000, easing = LinearEasing), RepeatMode.Restart), label = "angle"
        ).value
    } else 0f
    val pulse: Float = if (motion) {
        rememberInfiniteTransition(label = "pulse").animateFloat(
            0.5f, 1f, infiniteRepeatable(tween(2200), RepeatMode.Reverse), label = "pulseValue"
        ).value
    } else 0.8f

    Canvas(modifier) {
        val c = Offset(size.width / 2, size.height / 2)
        val r = size.minDimension / 2
        // orbit ring with constellation dots
        drawCircle(BrobotColors.VioletDeep, radius = r * 0.95f, center = c, style = Stroke(width = 2.dp.toPx()))
        for (i in 0 until 7) {
            val a = Math.toRadians((angle + i * 360.0 / 7))
            val p = Offset(c.x + (r * 0.95f * cos(a)).toFloat(), c.y + (r * 0.95f * sin(a)).toFloat())
            drawCircle(BrobotColors.Lavender.copy(alpha = pulse), radius = 4.dp.toPx(), center = p)
        }
        // shield
        val s = Path().apply {
            moveTo(c.x, c.y - r * 0.55f)
            lineTo(c.x + r * 0.42f, c.y - r * 0.38f)
            lineTo(c.x + r * 0.42f, c.y + r * 0.05f)
            cubicTo(c.x + r * 0.42f, c.y + r * 0.35f, c.x + r * 0.2f, c.y + r * 0.5f, c.x, c.y + r * 0.62f)
            cubicTo(c.x - r * 0.2f, c.y + r * 0.5f, c.x - r * 0.42f, c.y + r * 0.35f, c.x - r * 0.42f, c.y + r * 0.05f)
            lineTo(c.x - r * 0.42f, c.y - r * 0.38f)
            close()
        }
        drawPath(s, BrobotColors.Violet.copy(alpha = 0.18f))
        drawPath(s, BrobotColors.Violet, style = Stroke(width = 3.dp.toPx()))
        val tick = Path().apply {
            moveTo(c.x - r * 0.2f, c.y + r * 0.02f)
            lineTo(c.x - r * 0.04f, c.y + r * 0.18f)
            lineTo(c.x + r * 0.22f, c.y - r * 0.14f)
        }
        drawPath(tick, BrobotColors.Teal.copy(alpha = pulse), style = Stroke(width = 4.dp.toPx()))
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/SettingsScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

const val APP_VERSION = "1.0.0-prototype"

@Composable
fun SettingsScreen(
    largeText: Boolean, onLargeText: (Boolean) -> Unit,
    reduceMotion: Boolean, onReduceMotion: (Boolean) -> Unit,
    onResetAll: () -> Unit, onClearHistory: () -> Unit
) {
    var confirmReset by rememberSaveable { mutableStateOf(false) }
    var confirmClear by rememberSaveable { mutableStateOf(false) }
    var showLimits by rememberSaveable { mutableStateOf(false) }

    ScreenColumn {
        ScreenHeader("Settings & About", "BROBOT 2035 · concept prototype")
        DemoStatusBadge("DEMO MODE · ACTIVE")

        FuturisticCard {
            SectionTitle("About BROBOT 2035")
            Text("BROBOT is a concept prototype that imagines how personal privacy technology could work in 2035.", color = BrobotColors.TextPrimary)
            SectionTitle("Product vision")
            Text("Make invisible digital privacy risks understandable, visual and interactive.", color = BrobotColors.TextSecondary)
            SectionTitle("App version")
            Text(APP_VERSION, color = BrobotColors.TextPrimary)
        }

        FuturisticCard {
            SectionTitle("Accessibility")
            ToggleRow("Larger text", largeText, onLargeText)
            ToggleRow("Reduce motion", reduceMotion, onReduceMotion)
        }

        FuturisticCard {
            SectionTitle("Demo data")
            PrimaryActionButton("Clear Local Demo History", { confirmClear = true }, Modifier.fillMaxWidth(), secondary = true)
            PrimaryActionButton("Reset All Demo Data", { confirmReset = true }, Modifier.fillMaxWidth())
        }

        FuturisticCard(accent = BrobotColors.Amber, onClick = { showLimits = !showLimits }) {
            SectionTitle("Privacy and limitations (tap to ${if (showLimits) "hide" else "show"})")
            if (showLimits) {
                listOf(
                    "Concept prototype. Not a security product.",
                    "Uses synthetic data and a local, deterministic rules engine.",
                    "Does not scan your device, accounts, apps or networks.",
                    "Does not detect breaches or prevent attacks.",
                    "No live AI model is connected; explanations are template-based.",
                    "Requests no Android permissions and has no internet permission.",
                    "Collects no data, has no analytics and sends nothing to a server.",
                    "Demo history and settings may reset when the app restarts.",
                    "Scores shown are simple demo counts, not validated probabilities."
                ).forEach { Text("• $it", color = BrobotColors.TextPrimary, style = MaterialTheme.typography.bodyMedium) }
            }
        }
    }

    if (confirmReset) ConfirmDialog("Reset all demo data?", "This restores the default demo profile and clears scenarios, history, checklist and notes.", "Reset", { confirmReset = false; onResetAll() }, { confirmReset = false })
    if (confirmClear) ConfirmDialog("Clear demo history?", "This removes the Privacy Timeline entries only.", "Clear", { confirmClear = false; onClearHistory() }, { confirmClear = false })
}

@Composable
private fun ToggleRow(label: String, value: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().heightIn(min = 56.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, Modifier.weight(1f).padding(end = 12.dp), fontWeight = FontWeight.SemiBold, color = BrobotColors.TextPrimary)
        Switch(checked = value, onCheckedChange = onChange)
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/ShadowScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.data.ShadowCatalog
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun ShadowScreen(notes: List<String>, onAdd: (String) -> Unit, onRemove: (Int) -> Unit, onInfo: () -> Unit) {
    var draft by rememberSaveable { mutableStateOf("") }
    ScreenColumn {
        ScreenHeader("Digital Shadow", "The information and patterns a person may leave across digital services.", onInfo)
        DemoStatusBadge("SYNTHETIC EXAMPLE", BrobotColors.Lavender)

        FuturisticCard(accent = BrobotColors.Amber) {
            Text("Real footprint discovery is NOT active. BROBOT does not scrape the internet or search for anyone's identity. A future version could offer consent-based discovery with clear controls.", color = BrobotColors.TextPrimary, style = MaterialTheme.typography.bodyMedium)
        }

        ShadowCatalog.categories.forEach { c ->
            FuturisticCard {
                Text(c.title, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                DemoStatusBadge("SYNTHETIC EXAMPLE", BrobotColors.Lavender)
                Text(c.syntheticExample, color = BrobotColors.TextPrimary, style = MaterialTheme.typography.bodyMedium)
                Text(c.whyItMatters, color = BrobotColors.TextSecondary, style = MaterialTheme.typography.bodySmall)
            }
        }

        FuturisticCard(accent = BrobotColors.Cyan) {
            SectionTitle("Local demo notes")
            Text("Notes stay in this session only. Please do not type real personal details.", style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
            OutlinedTextField(
                value = draft, onValueChange = { draft = it.take(80) },
                modifier = Modifier.fillMaxWidth(), singleLine = true,
                label = { Text("Fictional note") }
            )
            PrimaryActionButton("Add Note", { onAdd(draft); draft = "" }, Modifier.fillMaxWidth(), enabled = draft.isNotBlank())
            if (notes.isEmpty()) Text("No notes yet.", color = BrobotColors.TextSecondary)
            notes.forEachIndexed { i, n ->
                Row(Modifier.fillMaxWidth().heightIn(min = 48.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(n, Modifier.weight(1f).padding(end = 8.dp), color = BrobotColors.TextPrimary)
                    TextButton(onClick = { onRemove(i) }, modifier = Modifier.heightIn(min = 48.dp)) { Text("Remove", color = BrobotColors.Amber) }
                }
            }
        }
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/SimulateScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.model.ConditionId
import com.brobot.future.model.DemoProfile
import com.brobot.future.model.Destination
import com.brobot.future.model.ScenarioId
import com.brobot.future.model.SimulationResult
import com.brobot.future.simulation.ScenarioCatalog
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun SimulateScreen(
    profile: DemoProfile,
    selected: ScenarioId?,
    result: SimulationResult?,
    onSelect: (ScenarioId) -> Unit,
    onSimulate: (ScenarioId) -> Unit,
    onToggle: (ConditionId, Boolean) -> Unit,
    onInfo: () -> Unit,
    onNavigate: (Destination) -> Unit
) {
    var showConditions by rememberSaveable { mutableStateOf(false) }

    ScreenColumn {
        ScreenHeader("What-If Simulator", "Pick a hypothetical scenario. The outcome depends on your demo conditions.", onInfo)
        DemoStatusBadge("SIMULATED · LOCAL RULES · OFFLINE")

        PrimaryActionButton(
            if (showConditions) "Hide demo conditions" else "Edit demo conditions",
            { showConditions = !showConditions }, Modifier.fillMaxWidth(), secondary = true
        )
        if (showConditions) ConditionsPanel(profile, onToggle)

        ScenarioCatalog.scenarios.forEach { sc ->
            ScenarioCard(sc, selected = sc.id == selected, onSelect = { onSelect(sc.id) }, onSimulate = { onSimulate(sc.id) })
        }

        if (result != null && result.scenario.id == selected) {
            FuturisticCard(accent = BrobotColors.Teal) {
                DemoStatusBadge(result.label, BrobotColors.Teal)
                Text("Scenario: ${result.scenario.title}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                Text(result.level.label, color = BrobotColors.Teal, fontWeight = FontWeight.SemiBold)

                SectionTitle("Possible risk pathway (hypothetical)")
                if (result.noPathwayNote != null) {
                    Text(result.noPathwayNote!!, color = BrobotColors.TextPrimary)
                } else {
                    StepSummaryList(result.steps)
                }

                SectionTitle("Assumptions used")
                AssumptionList(result.assumptions)

                SectionTitle("Suggested protective actions")
                if (result.suggestedInterventions.isEmpty()) {
                    Text("The relevant demo protections are already set. Other risks may remain.", color = BrobotColors.TextSecondary)
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        result.suggestedInterventions.forEach {
                            Text("• ${it.title} — ${it.description}", style = MaterialTheme.typography.bodyMedium, color = BrobotColors.TextPrimary)
                        }
                    }
                }

                PrimaryActionButton("View Butterfly Pathway", { onNavigate(Destination.BUTTERFLY) }, Modifier.fillMaxWidth())
                PrimaryActionButton("Choose Protective Action", { onNavigate(Destination.INTERVENTION) }, Modifier.fillMaxWidth(), secondary = true)
                PrimaryActionButton("Explain This Result", { onNavigate(Destination.EXPLAINER) }, Modifier.fillMaxWidth(), secondary = true)
            }
        } else if (selected != null) {
            Text("Press “Simulate” on the selected scenario to run the local rules.", style = MaterialTheme.typography.bodyMedium, color = BrobotColors.TextSecondary)
        } else {
            Text("Select a scenario above to begin.", style = MaterialTheme.typography.bodyMedium, color = BrobotColors.TextSecondary)
        }
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/TimelineScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.brobot.future.model.TimelineEvent
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun TimelineScreen(events: List<TimelineEvent>, onClear: () -> Unit, onInfo: () -> Unit) {
    var openId by rememberSaveable { mutableStateOf<Int?>(null) }
    var confirmClear by rememberSaveable { mutableStateOf(false) }

    ScreenColumn {
        ScreenHeader("Privacy Timeline", "What you explored in this demo session.", onInfo)
        DemoStatusBadge("DEMONSTRATION EVENTS · NOT REAL INCIDENTS")
        Text("History is kept for this session only and may reset when the app restarts.", style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)

        if (events.isEmpty()) {
            FuturisticCard {
                Text("No demo history", fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                Text("Run a scenario or apply an intervention and events will appear here.", color = BrobotColors.TextSecondary)
            }
        }
        events.forEach { e ->
            FuturisticCard(onClick = { openId = e.id }) {
                Text("STEP ${e.sequence}", style = MaterialTheme.typography.labelMedium, color = BrobotColors.Lavender)
                Text(e.title, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                Text("Tap to inspect", style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
            }
        }
        PrimaryActionButton("Clear Demo History", { confirmClear = true }, Modifier.fillMaxWidth(), secondary = true, enabled = events.isNotEmpty())
    }

    val open = events.firstOrNull { it.id == openId }
    if (open != null) {
        InfoDialog(
            title = open.title,
            sections = listOf("Saved demonstration details" to open.details, "Note" to "Synthetic demo event — not a real security incident."),
            onDismiss = { openId = null }
        )
    }
    if (confirmClear) {
        ConfirmDialog(
            title = "Clear demo history?",
            text = "This removes the demonstration events listed here. Your demo profile is not changed.",
            confirmLabel = "Clear History",
            onConfirm = { confirmClear = false; onClear() },
            onDismiss = { confirmClear = false }
        )
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/TwinScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.brobot.future.model.ConditionId
import com.brobot.future.model.DemoProfile
import com.brobot.future.model.Destination
import com.brobot.future.model.NodeStatus
import com.brobot.future.model.TwinNodeId
import com.brobot.future.simulation.TwinGraph
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

internal val twinPositions = mapOf(
    TwinNodeId.ACCOUNT_ACCESS to (0.20f to 0.12f),
    TwinNodeId.PERSONAL_DATA to (0.80f to 0.12f),
    TwinNodeId.DEVICE_SECURITY to (0.87f to 0.50f),
    TwinNodeId.SOCIAL_ENGINEERING to (0.13f to 0.50f),
    TwinNodeId.ACCOUNT_RECOVERY to (0.22f to 0.84f),
    TwinNodeId.FINANCIAL_EXPOSURE to (0.78f to 0.84f)
)

fun statusColor(s: NodeStatus): Color = when (s) {
    NodeStatus.PROTECTED -> BrobotColors.Teal
    NodeStatus.MIXED -> BrobotColors.Violet
    NodeStatus.ATTENTION -> BrobotColors.Amber
}

@Composable
fun TwinScreen(
    profile: DemoProfile,
    onToggle: (ConditionId, Boolean) -> Unit,
    onReset: () -> Unit,
    onInfo: () -> Unit,
    onTag: (String) -> Unit,
    onNavigate: (Destination) -> Unit
) {
    var selectedName by rememberSaveable { mutableStateOf<String?>(null) }
    val selected = selectedName?.let { TwinNodeId.valueOf(it) }

    ScreenColumn {
        ScreenHeader("Privacy Twin", "Tap a node, then change a demo condition and watch the map respond.", onInfo)
        DemoStatusBadge("SIMULATED PROFILE · NOT YOUR REAL PHONE")

        val nodes = listOf(
            GraphNode("center", "YOUR DIGITAL IDENTITY", 0.5f, 0.48f, BrobotColors.Lavender, big = true, description = "Your digital identity, the centre of the map")
        ) + TwinNodeId.values().map { n ->
            val (x, y) = twinPositions.getValue(n)
            val st = TwinGraph.status(n, profile)
            GraphNode(n.name, n.label, x, y, statusColor(st), emphasized = n == selected, dimmed = selected != null && n != selected,
                description = "${n.label}. ${st.label}. Double tap for explanation.")
        }
        val edges = TwinNodeId.values().map { GraphEdge("center", it.name, highlighted = it == selected) }

        FuturisticCard {
            ConstellationGraph(nodes, edges, onNodeClick = { id ->
                if (id != "center") selectedName = id
            })
            Text("Teal = protective conditions set · Violet = partly · Amber = needs attention (all demo states).", style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
        }

        ConditionsPanel(profile, onToggle)
        PrimaryActionButton("Reset Demo Profile", onReset, Modifier.fillMaxWidth(), secondary = true)

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryActionButton("Run a What-If", { onNavigate(Destination.SIMULATE) }, Modifier.weight(1f))
            PrimaryActionButton("Butterfly Effect", { onNavigate(Destination.BUTTERFLY) }, Modifier.weight(1f), secondary = true)
        }
        FeatureTagRow(listOf("What-If Simulator", "Butterfly Effect", "Privacy Universe"), onTag)
    }

    if (selected != null) {
        InfoDialog(
            title = selected.label,
            sections = listOf("What this node shows" to TwinGraph.explanation(selected, profile)),
            onDismiss = { selectedName = null }
        )
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/screens/UniverseScreen.kt`

```kotlin
package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.data.UniverseCatalog
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun UniverseScreen(onInfo: () -> Unit, onTag: (String) -> Unit) {
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    var dialogId by rememberSaveable { mutableStateOf<String?>(null) }
    val connected = selectedId?.let { UniverseCatalog.node(it).connectsTo.toSet() + it } ?: emptySet()

    ScreenColumn {
        ScreenHeader("Privacy Universe", "Tap a star to highlight what it connects to.", onInfo)
        DemoStatusBadge("ILLUSTRATION · NOT YOUR REAL DATA")

        val nodes = UniverseCatalog.nodes.map {
            GraphNode(
                it.id, it.label, it.x, it.y,
                color = if (it.id == "identity") BrobotColors.Lavender else BrobotColors.Violet,
                emphasized = it.id == selectedId,
                dimmed = selectedId != null && it.id !in connected,
                big = it.id == "identity",
                description = "${it.label}. ${it.represents}"
            )
        }
        val edges = UniverseCatalog.nodes.flatMap { n -> n.connectsTo.map { n.id to it } }
            .map { (a, b) -> if (a < b) a to b else b to a }.distinct()
            .map { (a, b) -> GraphEdge(a, b, highlighted = selectedId != null && (a == selectedId || b == selectedId)) }

        FuturisticCard {
            ConstellationGraph(nodes, edges, onNodeClick = { selectedId = it; dialogId = it }, height = 430.dp)
        }

        val sel = selectedId?.let { UniverseCatalog.node(it) }
        if (sel != null) {
            FuturisticCard(accent = BrobotColors.Lavender, onClick = { dialogId = sel.id }) {
                Text(sel.label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = BrobotColors.TextPrimary)
                Text(sel.represents, color = BrobotColors.TextSecondary)
                Text("Connected to: " + sel.connectsTo.joinToString { UniverseCatalog.node(it).label }, color = BrobotColors.Lavender, style = MaterialTheme.typography.bodySmall)
                Text("Tap for details", style = MaterialTheme.typography.bodySmall, color = BrobotColors.TextSecondary)
            }
        } else {
            Text("Select a star to see its connections.", color = BrobotColors.TextSecondary)
        }
        FeatureTagRow(listOf("Privacy Twin", "Digital Shadow"), onTag)
    }

    val d = dialogId?.let { UniverseCatalog.node(it) }
    if (d != null) {
        InfoDialog(
            title = d.label,
            sections = listOf(
                "What it represents" to d.represents,
                "Why it matters" to d.whyItMatters,
                "What a production version might need" to d.productionNeeds,
                "What this prototype does / does not do" to d.prototypeStatus
            ),
            onDismiss = { dialogId = null }
        )
    }
}
```


---

## 📁 `app/src/main/java/com/brobot/future/ui/theme/Theme.kt`

```kotlin
package com.brobot.future.ui.theme

import android.provider.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

object BrobotColors {
    val Bg = Color(0xFF07060D)
    val Surface = Color(0xFF120E24)
    val SurfaceHigh = Color(0xFF1B1535)
    val Violet = Color(0xFF8B5CF6)
    val VioletDeep = Color(0xFF3B2A78)
    val Lavender = Color(0xFFC4B5FD)
    val Cyan = Color(0xFF22D3EE)
    val Teal = Color(0xFF2DD4BF)
    val Amber = Color(0xFFFBBF24)
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFA8B0C0)
}

private val BrobotScheme = darkColorScheme(
    primary = BrobotColors.Violet,
    onPrimary = Color.White,
    secondary = BrobotColors.Cyan,
    tertiary = BrobotColors.Teal,
    background = BrobotColors.Bg,
    onBackground = BrobotColors.TextPrimary,
    surface = BrobotColors.Surface,
    onSurface = BrobotColors.TextPrimary,
    surfaceVariant = BrobotColors.SurfaceHigh,
    onSurfaceVariant = BrobotColors.TextSecondary,
    outline = BrobotColors.VioletDeep,
    error = BrobotColors.Amber
)

/** Whether decorative animation should run (system animator scale + in-app "Reduce motion"). */
val LocalMotionEnabled = staticCompositionLocalOf { true }

@Composable
fun rememberSystemAnimationsEnabled(): Boolean {
    val context = LocalContext.current
    return remember {
        try {
            Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) != 0f
        } catch (e: Exception) {
            true
        }
    }
}

@Composable
fun BrobotTheme(largeText: Boolean, reduceMotion: Boolean, content: @Composable () -> Unit) {
    val base = LocalDensity.current
    val density = if (largeText) Density(base.density, base.fontScale * 1.2f) else base
    val motion = rememberSystemAnimationsEnabled() && !reduceMotion
    CompositionLocalProvider(LocalDensity provides density, LocalMotionEnabled provides motion) {
        MaterialTheme(colorScheme = BrobotScheme, typography = Typography(), content = content)
    }
}
```


---

## 📁 `app/src/main/res/drawable/ic_launcher_foreground.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path
        android:fillColor="#00000000"
        android:strokeColor="#9B6BFF"
        android:strokeWidth="3.5"
        android:pathData="M54,26 L78,35 L78,55 C78,69 67,78 54,84 C41,78 30,69 30,55 L30,35 Z" />
    <path
        android:fillColor="#00000000"
        android:strokeColor="#3FE0D0"
        android:strokeWidth="3"
        android:pathData="M42,54 L51,63 L68,44" />
</vector>
```


---

## 📁 `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/brobot_icon_bg" />
    <foreground android:drawable="@drawable/ic_launcher_foreground" />
</adaptive-icon>
```


---

## 📁 `app/src/main/res/values/colors.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="brobot_bg">#07060D</color>
    <color name="brobot_icon_bg">#0B0916</color>
</resources>
```


---

## 📁 `app/src/main/res/values/themes.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.Brobot" parent="android:Theme.Material.NoActionBar">
        <item name="android:windowBackground">@color/brobot_bg</item>
        <item name="android:statusBarColor">@color/brobot_bg</item>
        <item name="android:navigationBarColor">@color/brobot_bg</item>
    </style>
</resources>
```


---

## 📁 `app/src/test/java/com/brobot/future/simulation/SimulationEngineTest.kt`

```kotlin
package com.brobot.future.simulation

import com.brobot.future.model.*
import org.junit.Assert.*
import org.junit.Test

class SimulationEngineTest {

    @Test fun determinism_sameInputsSameOutput() {
        val p = DemoProfile()
        ScenarioId.values().forEach { id ->
            assertEquals(SimulationEngine.simulate(id, p), SimulationEngine.simulate(id, p))
        }
    }

    @Test fun reusedPassword_defaultProfile_showsFullChain() {
        val r = SimulationEngine.simulate(ScenarioId.REUSED_PASSWORD, DemoProfile())
        assertEquals(5, r.steps.size)
        assertEquals(PathwayLevel.PRESENT, r.level)
    }

    @Test fun reusedPassword_uniquePasswords_removesPathway() {
        val r = SimulationEngine.simulate(ScenarioId.REUSED_PASSWORD, DemoProfile(passwordReuse = false))
        assertEquals(0, r.steps.size)
        assertEquals(PathwayLevel.ABSENT, r.level)
        assertNotNull(r.noPathwayNote)
    }

    @Test fun reusedPassword_2fa_trimsLaterSteps() {
        val r = SimulationEngine.simulate(ScenarioId.REUSED_PASSWORD, DemoProfile(twoFactor = true))
        assertEquals(3, r.steps.size)
    }

    @Test fun lostPhone_lockReducesPathway() {
        val unlocked = SimulationEngine.simulate(ScenarioId.LOST_PHONE, DemoProfile())
        val locked = SimulationEngine.simulate(ScenarioId.LOST_PHONE, DemoProfile(phoneLock = true))
        assertTrue(locked.riskStepCount < unlocked.riskStepCount)
        assertEquals(PathwayLevel.PRESENT, locked.level) // recovery still weak → other risks remain
    }

    @Test fun lostPhone_lockAndRecovery_absent() {
        val r = SimulationEngine.simulate(ScenarioId.LOST_PHONE, DemoProfile(phoneLock = true, strongRecovery = true))
        assertEquals(PathwayLevel.ABSENT, r.level)
    }

    @Test fun intervention_changesIntendedCondition_only() {
        val p = DemoProfile()
        val n = SimulationEngine.apply(SimulationEngine.intervention("enable_2fa"), p)
        assertTrue(n.twoFactor)
        assertEquals(p.copy(twoFactor = true), n)
    }

    @Test fun comparison_isConsistentWithEngine() {
        val p = DemoProfile()
        val iv = SimulationEngine.intervention("unique_passwords")
        val c = SimulationEngine.compare(ScenarioId.REUSED_PASSWORD, p, iv)
        assertEquals(SimulationEngine.simulate(ScenarioId.REUSED_PASSWORD, p), c.before)
        assertEquals(SimulationEngine.simulate(ScenarioId.REUSED_PASSWORD, p.copy(passwordReuse = false)), c.after)
        assertEquals(PathwayLevel.ABSENT, c.level)
        assertEquals(c.before.steps.size, c.removedSteps.size + c.remainingSteps.size)
    }

    @Test fun comparison_noChange_whenAlreadyProtected() {
        val p = DemoProfile(twoFactor = true)
        val c = SimulationEngine.compare(ScenarioId.REUSED_PASSWORD, p, SimulationEngine.intervention("enable_2fa"))
        assertEquals(c.before, c.after)
        assertTrue(c.changeDescription.startsWith("No demo condition changed"))
    }

    @Test fun suggestions_excludeAlreadySetConditions() {
        val r = SimulationEngine.simulate(ScenarioId.REUSED_PASSWORD, DemoProfile(passwordReuse = false, twoFactor = true, strongRecovery = true))
        assertTrue(r.suggestedInterventions.isEmpty())
    }

    @Test fun everyScenarioAndProfile_neverCrashes_andLevelsAreCoherent() {
        for (mask in 0 until 64) {
            val p = DemoProfile((mask and 1) != 0, (mask and 2) != 0, (mask and 4) != 0, (mask and 8) != 0, (mask and 16) != 0, (mask and 32) != 0)
            ScenarioId.values().forEach { id ->
                val r = SimulationEngine.simulate(id, p)
                assertEquals(r.riskStepCount == 0, r.level == PathwayLevel.ABSENT)
                assertEquals(r.riskStepCount == 0, r.noPathwayNote != null)
                SimulationEngine.interventions.forEach { iv ->
                    val c = SimulationEngine.compare(id, p, iv)
                    // an intervention must never ADD risk steps
                    assertTrue(c.after.riskStepCount <= c.before.riskStepCount)
                }
            }
        }
    }

    @Test fun twinNodeStatus_reactsToProfile() {
        assertEquals(NodeStatus.ATTENTION, TwinGraph.status(TwinNodeId.DEVICE_SECURITY, DemoProfile()))
        assertEquals(NodeStatus.PROTECTED, TwinGraph.status(TwinNodeId.DEVICE_SECURITY, DemoProfile(phoneLock = true)))
        assertEquals(NodeStatus.MIXED, TwinGraph.status(TwinNodeId.ACCOUNT_ACCESS, DemoProfile(twoFactor = true)))
    }

    @Test fun explainer_everyQuestionAnswered_andNeverEmpty() {
        ExplainerEngine.questions.forEach { q ->
            ScenarioId.values().forEach { id ->
                assertTrue(ExplainerEngine.answer(q, SimulationEngine.simulate(id, DemoProfile())).parts.isNotEmpty())
            }
        }
    }
}
```


---

## 📁 `gradle/wrapper/gradle-wrapper.properties`

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.9-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```
