import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("kotlin-android")
    id(Deps.Plugins.versions).version(Versions.Plugin.versions)
//    id(Deps.Plugins.ksp) version "${Versions.kotlin}-${Versions.ksp}"
    kotlin(Deps.Plugins.serialization) version Versions.kotlin
}

val keyStorePassword: String = gradleLocalProperties(rootDir).getProperty("keyStorePassword")
val keyPasswd: String = gradleLocalProperties(rootDir).getProperty("keyPassword")
val keyStorePath = "../key.jks"

android {

    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        testInstrumentationRunner = Values.testRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false //TODO: Bugged minifyReleaseWithR8 task as of may 2 2022
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile(Values.defaultProguardFile),
                Values.proguardFiles,
            )
            consumerProguardFile(Values.consumerProguardFiles)
            ndk.debugSymbolLevel = Values.debugSymbolLevel
        }
    }
    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
        freeCompilerArgs = Values.kotlinCompilerArgs
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "BeautyLab"
            keyPassword = keyPasswd
            storeFile = file(keyStorePath)
            storePassword = keyStorePassword
        }
        create("release") {
            keyAlias = "BeautyLab"
            keyPassword = keyPasswd
            storeFile = file(keyStorePath)
            storePassword = keyStorePassword
        }
    }

//
//    ksp {
//        arg("room.schemaLocation", "dbschema/")
//        arg("room.incremental", "true")
//        arg("room.expandProjection", "true")
//    }

    buildFeatures {
        aidl = false
        buildConfig = false
        compose = false
        dataBinding = false
        prefab = false
        renderScript = false
        resValues = false
        shaders = false
        viewBinding = false
        androidResources = false
        mlModelBinding = false
        prefabPublishing = false
    }
}

dependencies {
    with(Configuration.Data) {
        implementation.forEach(::implementation)
        api.forEach(::api)
    }

    api(project(":core"))
}
