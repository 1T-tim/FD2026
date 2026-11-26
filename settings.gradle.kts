plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "FD2026"
include(":seminar-4")
project(":seminar-4").projectDir = file("lesson4/seminar")

include(":homework-4")
project(":homework-4").projectDir = file("lesson4/homework")
