plugins {
    kotlin("jvm")
}

group = "io.github.lm98"
version = "0.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":whdt-core"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    implementation("org.slf4j:slf4j-api:1.7.25")
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