#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
if [ ! -d out ]; then ./scripts/compilar.sh; fi
java -cp out mx.edu.tecmilenio.sicoro.Main
