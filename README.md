# FD2026 · Семинар 4

[Задания Kotlin](lesson4/seminar/tasks/) · [Домашнее задание](lesson4/homework/) (будет опубликовано отдельно).

## 1. Получите проект

**Форка ещё нет.** На [странице курса](https://github.com/TimofeyYA1/FD2026) нажмите **Fork → Create fork**. На странице **своего форка** скопируйте ссылку **Code → HTTPS**. В IntelliJ IDEA выберите **Get from VCS → Clone** и вставьте её. В терминале можно сделать то же самое: `git clone https://github.com/ВАШ_ЛОГИН/FD2026.git` (подставьте свой логин).

**Форк уже есть.** Если есть незакоммиченные решения, сначала сохраните их коммитом. На GitHub откройте **свой форк** → **Sync fork → Update branch**. Затем в терминале IDEA из корня проекта:

```bash
git switch main
git pull --ff-only origin main
```

Если форк уже обновлён, но ещё не скачан на этот компьютер, просто клонируйте его. `origin` должен указывать на **ваш форк**: это можно проверить через `git remote -v`.

<details>
<summary>Что делать, если Sync fork или pull не сработал</summary>

Не нажимайте **Discard commits**, не используйте `reset --hard` или `push --force`. Сначала сохраните свои изменения коммитом. Если история общая, можно влить обновление и сохранить свои коммиты:

```bash
git switch main
git pull --ff-only origin main
git fetch https://github.com/TimofeyYA1/FD2026.git main
git merge FETCH_HEAD
```

Если уже `pull` отказался из-за расхождения, остановитесь: сначала нужно разобрать различия между вашей локальной веткой и форком. Если `merge` сообщил о конфликте, разрешите его или отмените слияние через `git merge --abort`. После успешного слияния выполните `git push origin main`.

Если Git пишет **`refusing to merge unrelated histories`** (форк сделан до переписывания истории), сохраните старую `main` как есть. Создайте **отдельную** ветку от актуальной репы курса:

```bash
git fetch https://github.com/TimofeyYA1/FD2026.git main
git switch -c seminar4-current FETCH_HEAD
```

Перенесите **только свои решения** из старой `main`, не заменяя целиком новые файлы с заданиями и настройки. Сделайте Commit и `git push -u origin seminar4-current`. Тесты запустятся на этой ветке тоже. Если такая ветка уже существует, не перезаписывайте её: сначала проверьте, есть ли в ней ваши решения.

</details>

## 2. Откройте проект в IDEA и запустите файл

1. Через **File → Open** откройте **корень репозитория**, где лежат `settings.gradle.kts` и `gradlew.bat`. Не подключайте `System` или `lesson4/seminar` как отдельный Gradle-проект. Если они уже подключены, отвяжите их в панели **Gradle** и подключите корневой `settings.gradle.kts`.
2. В **File → Project Structure → Project SDK** выберите **JDK 26**. Если его нет, выберите **Download JDK**. В **Settings → Build Tools → Gradle** выберите **Wrapper** и **Gradle JVM: JDK 26**. Если IDEA пишет «maximum compatible Gradle JVM version is 25», поставьте **JDK 21 только для Gradle JVM**, а **Project SDK оставьте 26**. Gradle использует JDK 26 для компиляции и тестов. Нажмите **Reload All Gradle Projects**. При первом запуске нужен интернет.
3. Откройте `lesson4/seminar/tasks/Task01.kt` и нажмите ▶ рядом с `fun main()`. До решения файл выводит `Задача 01: Kotlin запущен`. Потом раскомментируйте пример вызова под этой строкой или добавьте свой вызов. Так же работают `Task02.kt`–`Task09.kt`.

Gradle и Kotlin отдельно устанавливать не нужно. Для команд в терминале нужна Java: `java -version` должна находить JDK, либо укажите `JAVA_HOME` на каталог JDK (не на `bin`). Если тесты запускаются из IDEA, достаточно правильно выбрать Gradle JVM.

## 3. Запустите тесты

**В IDEA:** выберите `Task01Test` (или другой номер) в списке запусков сверху и нажмите ▶.

**В терминале из корня проекта:**

```powershell
# Windows, PowerShell
.\gradlew.bat :seminar-4:runTask01
.\gradlew.bat :seminar-4:test --tests "*Task01Test"
.\gradlew.bat :seminar-4:test -Pbonus=09
```

```bash
# macOS / Linux
./gradlew :seminar-4:runTask01
./gradlew :seminar-4:test --tests '*Task01Test'
./gradlew :seminar-4:test -Pbonus=09
```

Для запуска файла замените номер в `runTask01`. Для основных тестов замените номер в `Task01Test` на `01`–`05`. Для бонусных тестов замените `09` в `-Pbonus=09` на `06`, `07`, `08` или `09`. Красный тест на нерешённой задаче нормален: если видите `Задача … пока не решена` или `NotImplementedError`, тест уже запустился.

**На GitHub:** включите Actions в своём форке, если GitHub предложит. После **Commit → Push** откройте **Actions → Семинар 4 → последний запуск → Summary**. Смотрите результат своей задачи, даже если весь запуск красный. Бонусы: **Actions → Дополнительные задачи → Run workflow**. Классную работу можно проверять в своём форке без PR. Если PR всё же открыт в репу курса, GitHub может попросить преподавателя одобрить первый запуск проверок.
