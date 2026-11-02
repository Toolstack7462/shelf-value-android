# Implementation plan

Independent beta project; no PriceGrab application code or branding copied. The standard Gradle wrapper files originate from the PriceGrab checkout and retain Gradle's Apache notices. Package and name are provisional and must be checked before release.

1. Write executable JVM tests for exact rational comparison; confirm a deliberately empty implementation fails, then implement and rerun all tests with the available JDK compiler module.
2. Integrate the tested engine into a Kotlin/Compose screen with two offers, pack counts, compatible units, clear/reset, saveable state, light/dark theme and localized resources. Draft Urdu is subject to native review and RTL/device testing.
3. Configure AGP 8.10.1, Gradle 8.11.1, JDK 17, compile/target 36 and least-privilege GitHub Actions jobs for lint, unit tests, debug APK and emulator tests. Build and validate if Android SDK/dependencies become available.
4. Deliver honest results, licence notices, CI instructions, Phase 3 shopper study, and unpassed device/release gates. Never publish or use an upstream signing identity.
