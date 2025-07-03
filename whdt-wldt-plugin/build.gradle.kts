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
    implementation("io.github.wldt:mqtt-physical-adapter:0.1.2")
    implementation("io.github.wldt:mqtt-digital-adapter:0.1.2")
    implementation("io.github.wldt:http-digital-adapter:0.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}