# Archived verification status — 1 November 2026

This is the original pre-import snapshot. For a later source commit, consult its [Android beta checks run](https://github.com/Toolstack7462/shelf-value-android/actions/workflows/android-ci.yml) and artifacts.

Only the indicated check is covered by each status; all Android and CI claims require actual run evidence.

| Acceptance item | Status | Evidence / limitation |
|---|---|---|
| Android project compiles | VERIFIED FAIL | `./gradlew --no-daemon :app:lintDebug :app:testDebugUnitTest :app:assembleDebug` failed before compilation: `java.net.SocketException: Network is unreachable` downloading Gradle 8.11.1. |
| Debug APK exists | VERIFIED FAIL | No APK found after the failed build; no APK supplied. |
| APK identity verified | NOT TESTED | Application ID in source is provisional `com.shelfvalue.beta`; no APK exists to inspect. |
| Core comparison logic passes tests | VERIFIED PASS | 25 standalone JVM assertion checks passed on Java 17 via `./tests/run-jvm.sh`, covering core cases and near ties. Kotlin JUnit tests have not run. |
| API 36 configured | VERIFIED PASS | Source `app/build.gradle.kts` sets compile/target 36 with AGP 8.10.1 / Gradle 8.11.1; compiled manifest and APK not verified. |
| Correct licence attribution | VERIFIED PASS | Source contains retained Gradle wrapper licence and provenance notice, plus PriceGrab's MIT licence as reference. Full transitive dependency and asset rights audit is not tested. |
| Original branding | NOT TESTED | Original vector icon, splash and name exist in source; compiled output, visual quality and name/trademark rights are unverified. |
| English UI | NOT TESTED | English resources and Compose code written; build and device behaviour not verified. |
| Urdu/RTL | NOT TESTED | Draft Urdu resources and supportsRtl in source only; native-speaker review, numeral/IME and device layouts unverified. |
| Emulator testing | NOT TESTED | Workflow and one UI test written but no GitHub run or emulator available. |
| Real-device testing | NOT TESTED | No device connected. |
| Shopper study ready | NOT TESTED | Kit and workbook supplied; cannot deploy before a verified APK and facilitator smoke test. |
| Release AAB readiness | NOT TESTED | No release AAB, signing keys, upload or policy submission. |

## Build and CI blocker

GitHub connector exposes two linked accounts and access to existing unrelated repositories, but exposes no repository-creation action. No Desktop Commander device is connected. No new private repository was created and no existing repository modified; consequently there is no remote SHA, workflow URL or CI logs. Create an empty private repo in the preferred connected account and grant the GitHub app access, then add the source and run the supplied workflow. The workflow itself is not verified and may fail; inspect logs and adjust as needed. GitHub Actions availability and quota depend on account settings.

## Known risks before testing

- Source and Compose UI are uncompiled; fix any compilation, lint, dependency or emulator failures revealed by CI.
- Third-party GitHub Actions use version tags and need verified immutable SHA pins for stronger supply-chain controls. The Gradle wrapper JAR hash matches Gradle's published 8.11.1 checksum; the official distribution SHA-256 is configured, but the distribution could not be downloaded and checked here.
- Name, icon identity and package ID are provisional and not legally cleared. No server, login, ad or subscription implemented.
- Exact winner selection uses BigDecimal cross products, not rounded outputs. When values round to the same displayed price, UI includes a near-tie explanation. Users still need real-device testing to ensure this is clear.
- Urdu strings are draft translations, not independently reviewed. The UI is driven by Android device language rather than an in-app language toggle; RTL, digit shaping and keyboards remain untested.
- Android 16 edge-to-edge, splash, colour contrast, large fonts, TalkBack, reset, rotation, RTL and offline operation have not been verified.
- The study kit contains a previous browser-prototype instruction page; use the separate native checklist only after a valid APK exists.
