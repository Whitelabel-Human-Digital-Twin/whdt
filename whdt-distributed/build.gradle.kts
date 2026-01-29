plugins {
    kotlin("jvm")
    `maven-publish`
    kotlin("plugin.serialization") version "2.2.0"
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
    testImplementation(kotlin("test"))
    implementation(project(":whdt-core"))
}

kotlin {
    jvmToolchain(23)
}

tasks.test {
    useJUnitPlatform()
}