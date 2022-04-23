@file:Suppress("unused", "MemberVisibilityCanBePrivate")

import Versions.Plugin

object Deps {
    object Build {

        const val gradle = "com.android.tools.build:gradle:${Versions.gradleAndroid}"
        const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Plugins {

        const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Plugin.detektFormatting}"
        const val serialization = "plugin.serialization"
        const val versions = "com.github.ben-manes.versions"
        const val parcelize = "org.jetbrains.kotlin.plugin.parcelize"
        const val ksp = "com.google.devtools.ksp"
        const val detekt = "io.gitlab.arturbosch.detekt"
    }

    /* Basic Android */
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.datetime}"

    const val material = "com.google.android.material:material:${Versions.material}"
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashscreen}"
    const val vectorDrawable = "androidx.vectordrawable:vectordrawable-animated:${Versions.vectorDrawable}"

    const val startup = "androidx.startup:startup-runtime:${Versions.startup}"
    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    const val roomPaging = "androidx.room:room-paging:${Versions.room}"

    /* Ktx */
    const val collectionKtx = "androidx.collection:collection-ktx:${Versions.Ktx.collection}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.Ktx.core}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activity}"
    const val paletteKtx = "androidx.palette:palette-ktx:${Versions.Ktx.palette}"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val workKtx = "androidx.work:work-runtime-ktx:${Versions.work}"
    const val transitionKtx = "androidx.transition:transition-ktx:${Versions.Ktx.transition}"
    const val preference = "androidx.preference:preference-ktx:${Versions.Ktx.preferences}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val lifecycleService = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"

    /* Async */
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val logcat = "com.squareup.logcat:logcat:${Versions.logcat}"
    const val arrow = "io.arrow-kt:arrow-core:${Versions.arrow}"
    const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilTransformations =
        "com.github.Commit451.coil-transformations:transformations:${Versions.coilTransformations}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val flowExt = "io.github.hoc081098:FlowExt-jvm:${Versions.flowExt}"

    object Compose {

        const val ui = "androidx.compose.ui:ui:${Versions.Compose.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.Compose.compose}"
        const val material = "androidx.compose.material:material:${Versions.Compose.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.Compose.compose}"
        const val activity = "androidx.activity:activity-compose:${Versions.activity}" //watch out! Versions don't match
        const val animation = "androidx.compose.animation:animation:${Versions.Compose.compose}"
        const val graphics = "androidx.compose.animation:animation-graphics:${Versions.Compose.compose}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
        const val icons = "androidx.compose.material:material-icons-core:${Versions.Compose.compose}"
        const val iconsExtended = "androidx.compose.material:material-icons-extended:${Versions.Compose.compose}"
        const val material3 = "androidx.compose.material3:material3:${Versions.Compose.material3}"
        const val nav = "androidx.navigation:navigation-compose:${Versions.Compose.navigation}"

        const val paging = "androidx.paging:paging-compose:${Versions.Compose.paging}"
        const val koin = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
        const val iconics = "com.mikepenz:iconics-compose:${Versions.iconics}"
        const val destinations =
            "io.github.raamcosta.compose-destinations:animations-core:${Versions.Compose.destinations}"
        const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"

        const val collapsingToolbar = "me.onebone:toolbar-compose:${Versions.Compose.collapsingToolbar}"

        //has different naming scheme
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.constraintLayout}"

        const val mvi = "com.github.Nek-12.FlowMVI:android-compose:${Versions.mvi}"

        object Dialogs {

            //https://github.com/vanpra/compose-material-dialogs
            const val dialogs = "io.github.vanpra.compose-material-dialogs:core:${Versions.Compose.dialogs}"
            const val datetime = "io.github.vanpra.compose-material-dialogs:datetime:${Versions.Compose.dialogs}"
            const val color = "io.github.vanpra.compose-material-dialogs:color:${Versions.Compose.dialogs}"
        }

        object Accompanist {
            //https://google.github.io/accompanist/

            const val systemUiController =
                "com.google.accompanist:accompanist-systemuicontroller:${Versions.Compose.accompanist}"
            const val pager = "com.google.accompanist:accompanist-pager:${Versions.Compose.accompanist}"
            const val pagerIndicators =
                "com.google.accompanist:accompanist-pager-indicators:${Versions.Compose.accompanist}"
            const val swiperefresh = "com.google.accompanist:accompanist-swiperefresh:${Versions.Compose.accompanist}"
            const val placeholder =
                "com.google.accompanist:accompanist-placeholder-material:${Versions.Compose.accompanist}"
            const val drawablePainter =
                "com.google.accompanist:accompanist-drawablepainter:${Versions.Compose.accompanist}"

            const val flexBox = "com.google.accompanist:accompanist-flowlayout:${Versions.Compose.accompanist}"
            const val permissions = "com.google.accompanist:accompanist-permissions:${Versions.Compose.accompanist}"

            //Already in compose 1.1: insets, animation, insetsUi

            const val navMaterial =
                "com.google.accompanist:accompanist-navigation-material:${Versions.Compose.accompanist}"
        }

        val all = listOf(
            ui,
            foundation,
            material,
            activity,
            animation,
            graphics,
//                    material3,
            viewModel,
            iconics,
            koin,
            paging,
            nav,
            constraintLayout,
            coilCompose,
            destinations,
            mvi,
        ) + with(Dialogs) {
            listOf(
                dialogs,
                datetime,
                color,
            )
        } + with(Accompanist) {
            listOf(
                systemUiController,
                pager,
                pagerIndicators,
                swiperefresh,
                placeholder,
                drawablePainter,
                flexBox,
                permissions,
                navMaterial,
            )
        }
    }

    /** Complementary **/

    object AndroidUtils {

        private const val group = "com.github.Nek-12.AndroidUtils"

        const val core = "$group:core-ktx:${Versions.utils}"
        const val material = "$group:material-ktx:${Versions.utils}"
        const val android = "$group:android-ktx:${Versions.utils}"
        const val coroutine = "$group:coroutine-ktx:${Versions.utils}"
        const val room = "$group:room:${Versions.utils}"
        const val preferences = "$group:preferences-ktx:${Versions.utils}"
        const val compose = "$group:compose-ktx:${Versions.utils}"
    }

    object WilliamChart {

        const val group = "com.diogobernardino.williamchart"

        //note the : ---------------------------------\/ here
        const val williamCharts = "com.diogobernardino:williamchart:${Versions.williamChart}"
        const val sliderTooltip = "$group:tooltip-slider:${Versions.williamChart}"
        const val tooltipPoints = "$group:tooltip-points:${Versions.williamChart}"
    }

    object Iconics {

        const val core = "com.mikepenz:iconics-core:${Versions.iconics}"
        const val views = "com.mikepenz:iconics-views:${Versions.iconics}"
        const val material = "com.mikepenz:google-material-typeface-rounded:4.0.0.1-kotlin@aar"
        const val fontawesome = "com.mikepenz:fontawesome-typeface:5.13.3.0-kotlin@aar"
    }

    object AndroidTest {

        const val runner = "androidx.test:runner:${Versions.Test.runner}"
        const val junit = "androidx.test.ext:junit:${Versions.Test.androidJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.Test.espresso}"
        const val work = "androidx.work:work-testing:${Versions.work}"
    }

    object Test {

        const val junit = "junit:junit:${Versions.Test.junit}"
        const val compose = "androidx.compose.ui:ui-test-junit4:${Versions.Compose.compose}"
        const val koin = "io.insert-koin:koin-test:${Versions.koin}"
        const val mockito = "org.mockito:mockito-core:${Versions.Test.mockito}"
        const val robolectric = "org.robolectric:robolectric:${Versions.Test.robolectric}"
        const val paging = "androidx.paging:paging-common:${Versions.paging}"
        const val ktor = "io.ktor:ktor-client-mock:${Versions.ktor}"
    }

    object Koin {

        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
        const val workmanager = "io.insert-koin:koin-androidx-workmanager:${Versions.koin}"

        val all = listOf(
            core,
            android,
        )
    }

    object Ktor {

        const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val engine = "io.ktor:ktor-client-cio:${Versions.ktor}"
        const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        const val auth = "io.ktor:ktor-client-auth:${Versions.ktor}"

        val all = listOf(core, engine, logging, contentNegotiation, serialization, auth)
    }

    object Ksp {

        const val destinations = "io.github.raamcosta.compose-destinations:ksp:${Versions.Compose.destinations}"
        const val room = "androidx.room:room-compiler:${Versions.room}"
    }
}
