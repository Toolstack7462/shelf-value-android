# Shelf Value Android beta

Shelf Value compares two grocery offers at the same unit price, in English or Urdu. Enter the total package price, quantity per pack, unit and pack count. It shows the cheaper offer, both normalized prices, the absolute difference and the percentage saved. The comparison uses exact decimal inputs before rounding for display. The editable currency label defaults to `Rs`.

## Install the debug APK

1. Open [Android beta checks](https://github.com/Toolstack7462/shelf-value-android/actions/workflows/android-ci.yml) and choose the run for the desired commit.
2. Download `shelf-value-debug-<commit>`, extract `app-debug.apk` and use `adb install -r app-debug.apk` on an Android 7.0 (API 24) or later test device.
3. The `android-reports` artifact contains the APK checksum, signing certificate, package information, permissions, lint and unit-test results. The `emulator-reports` artifact from a successful emulator job contains installation checks, test output and screenshots. Match the commit and SHA-256 before distributing a beta.

## Build and test

The project uses Kotlin and Jetpack Compose for its interface and a Java decimal comparison engine. It targets API 36 and uses JDK 17, AGP 8.10.1, Gradle 8.11.1, Android SDK Platform 36 and Build Tools 35.0.0. Open the repository root in Android Studio or run:

```sh
./tests/run-jvm.sh
./gradlew :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

On Windows, use `gradlew.bat` for the Gradle commands. The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`. CI installs the same debug APK on an API 35 emulator and runs instrumented tests with English at the default text size and Urdu at twice the default size.

App ID: `com.shelfvalue.beta`. Any APK in the debug workflow uses a debug key. Separate builds may use different debug certificates. The app reads prices supplied by the shopper. Urdu copy is still pending native-speaker review. Real-device testing and shopper research are tracked separately from CI.

## Study and provenance

Use [the native checklist](docs/study/NATIVE_CHECKLIST.md) on a test device before recruiting shoppers. [Third-party notices](THIRD_PARTY_NOTICES.md) describe the wrapper and reference material. [The initial source handoff](docs/STATUS.md) records the checks that were possible before the first GitHub Actions run.
