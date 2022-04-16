import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id(Deps.Plugins.parcelize)
    id(Deps.Plugins.versions).version(Versions.Plugin.versions)
    id(Deps.Plugins.ksp) version "${Versions.kotlin}-${Versions.ksp}"
}

val keyStorePassword: String = gradleLocalProperties(rootDir).getProperty("keyStorePassword")
val keyPasswd: String = gradleLocalProperties(rootDir).getProperty("keyPassword")
val keyStorePath = "../key.jks"

android {
    compileSdk = Versions.compileSdk
    buildToolsVersion = Versions.buildTools

    defaultConfig {

        buildConfigField(
            "String",
            "APPLICATION_ID_DEFAULT",
            "\"${android.defaultConfig.applicationId}\"",
        )

        resourceConfigurations.clear()
        resourceConfigurations.addAll(Values.supportedLocales)

        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Values.versionCode
        versionName = Values.versionName
        testInstrumentationRunner = Values.testRunner
    }

    applicationVariants.all {
        outputs.all {
            if (name.contains("release")) {
                (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName =
                    "../../apk/$applicationId-$name-$versionName.apk"
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = Values.isMinifyEnabledRelease
            isShrinkResources = Values.isMinifyEnabledRelease

            proguardFiles(
                getDefaultProguardFile(Values.defaultProguardFile),
                Values.proguardFiles,
            )
            ndk.debugSymbolLevel = Values.debugSymbolLevel
        }
        getByName("debug") {
            versionNameSuffix = "-d"
            isRenderscriptDebuggable = true
            applicationIdSuffix = ".debug"
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

    buildFeatures {
        aidl = false
        buildConfig = true
        compose = true
        dataBinding = false
        prefab = false
        renderScript = false
        resValues = true
        shaders = true
        viewBinding = false
        mlModelBinding = false
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
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.compose
        useLiveLiterals = true
    }
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

dependencies {
    ksp(Deps.Ksp.destinations)
    with(Configuration.App) {
        implementation.forEach(::implementation)
        debugImplementation.forEach(::debugImplementation)
    }
    implementation(project(":core"))
    implementation(project(":data"))
}
