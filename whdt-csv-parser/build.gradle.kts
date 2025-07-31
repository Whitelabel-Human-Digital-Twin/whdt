plugins {
    kotlin("jvm")
}

group = "io.github.lm98"
version = "0.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":whdt-core"))

    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("io.github.wldt:wldt-core:0.4.0")
    implementation("io.github.wldt:mqtt-physical-adapter:0.1.2")
    implementation("io.github.wldt:mqtt-digital-adapter:0.1.2")
    implementation("io.github.wldt:http-digital-adapter:0.2")

    testImplementation("io.kotest:kotest-runner-junit5:5.8.0") // Required to run tests
    testImplementation("io.kotest:kotest-assertions-core:5.8.0") // Optional: assertions
    testImplementation("io.kotest:kotest-framework-engine:5.8.0") // Optional but useful
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}