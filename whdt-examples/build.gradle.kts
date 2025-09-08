plugins {
    kotlin("jvm")
}

group = "io.github.whdt"
version = "0.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(project(":whdt-core"))
    implementation(project(":whdt-wldt-plugin"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}