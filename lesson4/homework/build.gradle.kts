plugins {
    kotlin("jvm") version "2.4.21"
    application
}
repositories { mavenCentral() }
kotlin {
    jvmToolchain(26)
    sourceSets.main { kotlin.setSrcDirs(listOf("src")) }
    sourceSets.test { kotlin.setSrcDirs(listOf("test")) }
}
application { mainClass.set("homework.GardenKt") }
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}
tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "failed") }
}
