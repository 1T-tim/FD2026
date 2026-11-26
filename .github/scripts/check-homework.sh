#!/usr/bin/env bash
set -euo pipefail

project="${1:?Нужна папка курса}/lesson4/homework"
submission="${2:?Нужна папка решения}"
src="$submission/lesson4/homework/src"

if [[ ! -f "$project/build.gradle.kts" || ! -s "$project/test/GardenTest.kt" ]]; then
  echo 'Не найдены сборка или открытые тесты домашки в ветке курса.' >&2
  exit 1
fi

for dir in "$submission" "$submission/lesson4" "$submission/lesson4/homework" "$src"; do
  if [[ ! -d "$dir" || -L "$dir" ]]; then
    echo 'Не найдена обычная папка lesson4/homework/src в PR.' >&2
    exit 1
  fi
done
if [[ -z "$(find "$src" -type f -name '*.kt' -print -quit)" || -n "$(find "$src" ! -type f ! -type d -print -quit)" ]]; then
  echo 'В lesson4/homework/src нужны Kotlin-файлы без ссылок и специальных файлов.' >&2
  exit 1
fi

# Работаем только в отдельном checkout курса на CI, не в студенческой копии.
rm -rf -- "$project/src"
mkdir -p -- "$project/src"
cp -R -- "$src/." "$project/src/"
