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
    implementation(project(":whdt-distributed"))
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