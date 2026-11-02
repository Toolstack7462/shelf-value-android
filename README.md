# Shelf Value — independent Android beta source

**Status: native source and CI workflow prepared; no Android build or APK has succeeded.** This archive is not an installable beta. Name and package `com.shelfvalue.beta` are provisional and have not been cleared for trademark or Play availability. Do not publish the app without further validation and owner authorisation.

The app uses Kotlin/Jetpack Compose for UI and an original Java exact-arithmetic engine. It compares two products using price, quantity, units and pack count; supports g/kg, ml/litre, item, tie, difference, percentage and reset. Currency is a user-editable label, initially `Rs`; it does not convert exchange rates or retrieve prices. English and draft Urdu resources are included, selected by Android device language. No native-speaker translation review, RTL or TalkBack test was performed. Light/dark colour schemes, original vector icon and splash are implemented in source only; UI/device rendering has not been verified. No Internet permission or third-party analytics is declared in the source manifest; merged APK permissions remain unverified.

## Tested calculation core

From this directory run `./tests/run-jvm.sh` using a JDK 17 installation. In this environment `java -m jdk.compiler/com.sun.tools.javac.Main` compiled the source and the standalone test completed **25 assertion checks**. These are Java core checks, not Kotlin JUnit, Android, UI, emulator, device or build tests. CI also contains Kotlin JUnit tests that have not run.

## Build locally with Android Studio

1. Install Android Studio and a JDK 17, Android SDK Platform 36 and Build Tools 35.0.0, with internet access to Google Maven, Maven Central and services.gradle.org.
2. Open this folder as a Gradle project and allow Gradle sync. Do not modify the original PriceGrab repo. The configured combination is AGP 8.10.1, Gradle 8.11.1 and JDK 17; these versions have not been built here.
3. Run `./gradlew :app:lintDebug :app:testDebugUnitTest :app:assembleDebug` on macOS/Linux, or `gradlew.bat :app:lintDebug :app:testDebugUnitTest :app:assembleDebug` in Windows PowerShell.
4. Only if it succeeds, the debug APK should appear at `app/build/outputs/apk/debug/app-debug.apk`. Verify package ID, target and permissions with Android SDK build tools, install only on a test device and run the three shopper-study scenarios. See docs/STATUS.md.
5. A release bundle requires separate signing and Play setup. No release signing key, keystore, Play account, or production deployment is included.

## GitHub Actions — separate private repository

Create an **empty private repository** in the intended GitHub account and grant the connected GitHub app access to that repository. Add the extracted project files to its root, including the normally hidden `.github/workflows/android-ci.yml` and Gradle wrapper. A push or manual workflow dispatch is intended to run the JVM harness, Android lint, Kotlin/JUnit tests, assemble a debug APK, inspect its identity/permissions, upload it as an artifact, and run an emulator job. This workflow has not run and may require fixes. It never signs a release or publishes to Google Play. Do not copy release credentials into the repository. Third-party actions are version-tagged; review and pin to verified immutable SHAs before relying on CI for sensitive release work.

**Git example**, replacing the URL and `main` with your own repository/default branch:
```
git init
git add .
git commit -m "Initial Android beta source"
git branch -M main
git remote add origin https://github.com/YOUR_ACCOUNT/YOUR_PRIVATE_REPO.git
git push -u origin main
```
Use this only from the extracted **ShelfValue** project folder, not the upstream PriceGrab checkout or an existing repository. Then open GitHub Actions and inspect the run logs; a successful APK is downloadable from that run's Artifacts section. A private account's Actions quota and policies may affect availability/cost.

## Study

`docs/study/Previous_Browser_Study_Kit.md` and the blank workbook are included for continuity; replace the kit's browser-specific setup with the native checklist in `docs/study/NATIVE_CHECKLIST.md` only after an APK is built and smoke-tested. No users have been recruited or studied.
