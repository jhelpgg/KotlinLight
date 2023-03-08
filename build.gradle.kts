import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
    id("maven-publish")
}

val VERSION_PREFIX = "1.0.8"
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
    this.testImplementation(this.platform("org.junit:junit-bom:5.7.0"))
    this.testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    this.useJUnitPlatform()
    this.testLogging {
        this.events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    applicationName = "fr.jhelp.kotlinLight.MainKt"
}

publishing {
    this.publications {
        repositories {
            maven {
                this.credentials {
                    this.username = System.getenv("NEXUS_USERNAME")
                    this.password = System.getenv("NEXUS_PASSWORD")
                }

                val nexusUrl = "https://nexus.cloud.feetme.fr/repository/android-dev/"
                url = uri(nexusUrl)
            }
        }

        this.create<MavenPublication>("mavenJava") {
            this.groupId = GROUP_ID
            this.artifactId = ARTIFACT_ID
            this.version = VERSION
            this.from(components["kotlin"])
        }
    }
}
