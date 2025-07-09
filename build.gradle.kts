plugins {
    kotlin("jvm") version "2.1.20"
    `maven-publish`
}
allprojects {
    group = "io.github.lm98"
    version = System.getenv("packageVersion") ?: "0.0.0-SNAPSHOT"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

subprojects {
    // Optional: shared dependency versions
    val kotestVersion = "5.8.0"

    plugins.withId("org.jetbrains.kotlin.jvm") {
        dependencies {
            "implementation"("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
            "testImplementation"("io.kotest:kotest-runner-junit5:$kotestVersion")
            "testImplementation"("io.kotest:kotest-assertions-core:$kotestVersion")
            "testImplementation"("io.kotest:kotest-framework-engine:$kotestVersion")
        }

        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}

tasks.test {
    useJUnitPlatform()
}