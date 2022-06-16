import org.gradle.api.JavaVersion

object Versions {

    val java = JavaVersion.VERSION_11
    const val jvmTarget = "11"
    const val compileSdk = 32
    const val targetSdk = compileSdk
    const val minSdk = 26
    const val gradleAndroid = "7.2.1"
    const val coroutines = "1.6.2"
    const val kotlin = "1.6.21"
    const val datetime = "0.3.3"
    const val ksp = "1.0.5"
    const val activity = "1.6.0-alpha05"
    const val serialization = "1.3.2"
    const val room = "2.5.0-alpha01"
    const val koin = "3.2.0"
    const val detekt = "1.18.1"
    const val paging = "3.2.0-alpha01"
    const val utils = "0.7.7"
    const val iconics = "5.3.3"
    const val williamChart = "3.11.0"
    const val arrow = "1.1.3-alpha.19"
    const val material = "1.6.0-alpha01"
    const val logcat = "0.1"
    const val startup = "1.1.0"
    const val work = "2.8.0-alpha01"
    const val splashscreen = "1.0.0-rc01"
    const val vectorDrawable = "1.1.0"
    const val coil = "2.1.0"
    const val coilTransformations = "2.0.2"
    const val lifecycle = "2.5.0-rc02"
    const val mvi = "0.2.5"
    const val ktor = "2.0.0"
    const val flowExt = "0.3.0"

    object Ktx {

        const val collection = "1.2.0"
        const val core = "1.9.0-alpha05"
        const val preferences = "1.2.0"
        const val transition = "1.4.1"
        const val palette = "1.0.0"
    }

    object Compose {

        const val accompanist = "0.24.11-rc"
        const val compose = "1.2.0-rc01"
        const val material3 = "1.0.0-alpha03"
        const val constraintLayout = "1.1.0-alpha02"
        const val navigation = "2.5.0-rc02"
        const val dialogs = "0.7.0"
        const val destinations = "1.5.12-beta"
        const val collapsingToolbar = "2.3.3"
        const val paging = "1.0.0-alpha15"
    }

    object Test {

        const val junit = "4.13.2"
        const val runner = "1.5.0-alpha02"
        const val espresso = "3.5.0-alpha05"
        const val androidJunit = "1.1.4-alpha05"
        const val mockito = "4.4.0"
        const val robolectric = "4.7.3"
    }

    object Plugin {

        const val detektFormatting = "1.19.0"
        const val versions = "0.42.0"
    }
}
