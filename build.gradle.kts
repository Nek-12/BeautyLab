plugins {
    `kotlin-dsl`
    id(Deps.Plugins.detekt).version(Versions.detekt)
}

buildscript {
    repositories {
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath(Deps.Build.gradle)
        classpath(Deps.Build.kotlinPlugin)
    }
}

dependencies {
    detektPlugins(Deps.Plugins.detektFormatting)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        mavenLocal()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = Versions.jvmTarget }

    apply(plugin = Deps.Plugins.detekt)

    detekt {
        toolVersion = Versions.detekt
        config = files(File(rootProject.rootDir, Values.detektConfigPath))
        buildUponDefaultConfig = true
        autoCorrect = true
        parallel = true

        reports {
            xml {
                enabled = false
                destination = file("${Values.detektReportFilename}.xml")
            }
            html {
                enabled = true
                destination = file("${Values.detektReportFilename}.html")
            }
            txt {
                enabled = false
                destination = file("${Values.detektReportFilename}.txt")
            }
        }
    }
}
