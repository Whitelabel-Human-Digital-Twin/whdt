plugins {
    kotlin("jvm")
    `maven-publish`
}
val moduleVersion: String = rootProject.file("${project.name}/version.txt").readText().trim()

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            groupId = "io.github.lm98"
            artifactId = project.name
            version = moduleVersion
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/lm98/whdt")
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
    implementation("com.google.code.gson:gson:2.13.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}