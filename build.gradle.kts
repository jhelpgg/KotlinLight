import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
    id("maven-publish")
}

group = "fr.jhelp.kotlinLight"
version = "1.0.0"

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
            groupId = "fr.jhelp.kotlinLight"
            artifactId = "kotlinLight"
            version = "1.0.0"

            from(components["kotlin"])
        }
    }
}