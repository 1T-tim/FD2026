plugins {
    kotlin("jvm") version "2.4.21"
}

repositories { mavenCentral() }

// Compile a generated copy so empty exercises don't block the tests of other tasks.
val exerciseFiles = listOf("Task01.kt", "Task02.kt", "Task03.kt", "Task04.kt", "Task05.kt")
val bonus = providers.gradleProperty("bonus").orNull
require(bonus == null || bonus in listOf("06", "07", "08"))
val emptyBody = Regex("""^(\s*fun\s+[^{}]+?)\s*\{\s*}\s*$""")
val prepareTasks by tasks.registering(Sync::class) {
    filteringCharset = "UTF-8"
    from("tasks") {
        include(exerciseFiles + listOfNotNull(bonus?.let { "Task${it}.kt" }))
        exerciseFiles.forEachIndexed { index, fileName ->
            filesMatching(fileName) {
                filter { line: String ->
                    emptyBody.matchEntire(line)?.let {
                        "${it.groupValues[1]} = throw NotImplementedError(\"Задача ${index + 1} не решена\")"
                    } ?: line
                }
            }
        }
    }
    into(layout.buildDirectory.dir("generated/seminarTasks"))
}

kotlin {
    jvmToolchain(26)
    sourceSets {
        main {
            kotlin.srcDir(prepareTasks)
        }
        test {
            kotlin.setSrcDirs(listOf("tests"))
            kotlin.include(if (bonus != null) listOf("Task${bonus}Test.kt") else (1..5).map { "Task0${it}Test.kt" })
        }
    }
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging { events("failed", "skipped", "passed") }
}
