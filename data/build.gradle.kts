plugins {
    id("com.android.library")
    id("kotlin-android")
    id(Deps.Plugins.versions).version(Versions.Plugin.versions)
//    id(Deps.Plugins.ksp) version "${Versions.kotlin}-${Versions.ksp}"
    kotlin(Deps.Plugins.serialization) version Versions.kotlin
}

android {

    compileSdk = Versions.compileSdk
    buildToolsVersion = Versions.buildTools

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        testInstrumentationRunner = Values.testRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = Values.isMinifyEnabledRelease
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
