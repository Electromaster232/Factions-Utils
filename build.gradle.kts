plugins {
    kotlin("jvm") version "2.2.20"
    id("com.gradleup.shadow") version "9.1.0"
}

group = "me.djelectro"
version = "1.0.0"

repositories {
    mavenCentral()
    // Paper API for compileOnly
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven {
        name = "paulemReleases"
        url = uri("https://maven.paulem.net/releases")
    }

}

dependencies {
    // Compile against Paper (works for Spigot/Bukkit too)
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")

    // Point this to the ImprovedFactions jar you run on your server
    compileOnly("com.github.ToberoCat:ImprovedFactions:2.3.1-nightly")

    // Shade Kotlin stdlib so servers don't need a Kotlin runtime plugin
    implementation(kotlin("stdlib"))
}

java {
    toolchain { languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(21)) }
}

tasks {
    build {

    }
}
