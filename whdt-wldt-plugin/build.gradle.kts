plugins {
    kotlin("jvm")
}

group = "io.github"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":whdt-core"))
    testImplementation(kotlin("test"))

    implementation("io.github.wldt:wldt-core:0.4.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}