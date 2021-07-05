import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
    id("maven-publish")
    id("maven")
}

val VERSION_PREFIX = "1.0.1"
val VERSION_SUFFIX = ""
val VERSION = VERSION_PREFIX + VERSION_SUFFIX
val GROUP_ID = "fr.jhelp.kotlinLight"
val ARTIFACT_ID = "kotlinLight"

group = GROUP_ID
version = VERSION

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    testImplementation(kotlin("test-junit5"))
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = GROUP_ID
            artifactId = ARTIFACT_ID
            version = VERSION

            from(components["kotlin"])
        }
    }
}


tasks.named<Upload>("uploadArchives") {
    val nexusUrl = "https://nexus.cloud.feetme.fr/repository/android-dev/"

    repositories.withGroovyBuilder {
        "mavenDeployer" {
            "repository"("url" to nexusUrl) {
                "authentication"(
                    "userName" to System.getenv("NEXUS_USERNAME"),
                    "password" to System.getenv("NEXUS_PASSWORD")
                )
            }
            "pom" {
                setProperty("groupId", GROUP_ID)
                setProperty("artifactId", ARTIFACT_ID)
                setProperty("version", VERSION)
            }
        }
    }
}