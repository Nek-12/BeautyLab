@file:Suppress("MemberVisibilityCanBePrivate")

object Values {

    const val applicationId = "com.nek12.beautylab"
    val versionCode = generateGitPatchVersion()

    const val majorRelease = 0
    const val minorRelease = 0
    const val patch = 1
    val versionName = "$majorRelease.$minorRelease.${patch} ($versionCode)"

    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val isMinifyEnabledRelease = true
    const val defaultProguardFile = "proguard-android-optimize.txt"
    const val proguardFiles = "proguard-rules.pro"
    const val consumerProguardFiles = "consumer-rules.pro"
    const val debugSymbolLevel = "SYMBOL_TABLE"
    const val detektConfigPath = "detekt.yml"
    const val detektReportFilename = "detekt_report"
    val supportedLocales = listOf("en", "ru")
    val kotlinCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn", "-Xjvm-default=all")
}
