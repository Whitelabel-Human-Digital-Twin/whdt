plugins {
    kotlin("jvm")
    `maven-publish`
}

val moduleVersion: String = rootProject.file("${project.name}/version.txt").readText().trim()

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            groupId = "io.github.whdt"
            artifactId = project.name
            version = moduleVersion
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Whitelabel-Human-Digital-Twin/whdt")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GPR_KEY")
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":whdt-core"))

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