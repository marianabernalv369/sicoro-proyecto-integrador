#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
rm -rf out
mkdir -p out
find src/main/java -name "*.java" -print0 | xargs -0 javac -encoding UTF-8 -source 11 -target 11 -d out
echo "Compilación terminada en la carpeta out/."
