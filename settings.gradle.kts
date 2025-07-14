plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "whdt"
include("whdt-core")
include("whdt-wldt-plugin")
include("whdt-examples")