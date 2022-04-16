import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val compileKotlin: KotlinCompile by tasks

plugins {
    id("java-library")
    kotlin("jvm")
    kotlin(Deps.Plugins.serialization) version Versions.kotlin
}

java {
    sourceCompatibility = Versions.java
    targetCompatibility = Versions.java
}

compileKotlin.kotlinOptions {
    jvmTarget = Versions.jvmTarget
}

dependencies {
    Configuration.Core.api.forEach(::implementation)
}
