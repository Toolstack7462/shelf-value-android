#!/usr/bin/env bash
set -euo pipefail

package=com.shelfvalue.beta
reports=emulator-reports
mkdir -p "$reports"
trap 'adb logcat -d -b crash > "$reports/crash.log" || true' EXIT

adb install -t packages/app-debug.apk
adb install -t packages/app-debug-androidTest.apk
installed=$(adb shell pm path "$package" | tr -d '\r' | sed -n 's/^package://p' | head -1)
test -n "$installed"
adb pull "$installed" "$RUNNER_TEMP/shelf-installed.apk"
cmp packages/app-debug.apk "$RUNNER_TEMP/shelf-installed.apk"
sha256sum packages/app-debug.apk "$RUNNER_TEMP/shelf-installed.apk" | tee "$reports/checksums.txt"
adb shell getprop ro.build.version.sdk | tee "$reports/emulator-api.txt"
adb shell dumpsys package "$package" > "$reports/installed-package.txt"

run_ui() {
  local locale="$1" scale="$2" label="$3"
  adb shell am force-stop "$package"
  adb shell settings put system font_scale "$scale"
  adb shell cmd locale set-app-locales "$package" --user 0 --locales "$locale"
  adb shell cmd locale get-app-locales "$package" | tee "$reports/$label-locale.txt"
  adb shell am start -W -n "$package/.MainActivity" | tee "$reports/$label-launch.txt"
  grep -q 'Status: ok' "$reports/$label-launch.txt"
  adb shell am instrument -w -e expected_language "${locale%%-*}" -e expected_font "$scale" \
    "$package.test/androidx.test.runner.AndroidJUnitRunner" | tr -d '\r' | tee "$reports/$label-ui.txt"
  grep -Eq '^OK \([1-9][0-9]* tests?\)' "$reports/$label-ui.txt"
  adb pull "/sdcard/Android/data/$package/files/ui-checks/." "$reports/"
}

run_ui en-US 1.0 english
run_ui ur-PK 2.0 urdu-large
