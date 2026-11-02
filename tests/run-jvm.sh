#!/usr/bin/env bash
set -euo pipefail
root_dir="$(cd "$(dirname "$0")/.." && pwd)"
test_dir="$root_dir/build/standalone-jvm-tests"
mkdir -p "$test_dir"
if command -v javac >/dev/null 2>&1; then
  javac -d "$test_dir" "$root_dir/app/src/main/java/com/shelfvalue/beta/ComparisonEngine.java" "$root_dir/tests/EngineJvmTest.java"
else
  java -m jdk.compiler/com.sun.tools.javac.Main -d "$test_dir" "$root_dir/app/src/main/java/com/shelfvalue/beta/ComparisonEngine.java" "$root_dir/tests/EngineJvmTest.java"
fi
java -cp "$test_dir" com.shelfvalue.beta.EngineJvmTest
