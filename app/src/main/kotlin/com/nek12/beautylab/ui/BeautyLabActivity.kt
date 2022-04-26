package com.nek12.beautylab.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.nek12.beautylab.ui.theme.BeautyLabTheme
import org.koin.core.component.KoinComponent

class BeautyLabActivity : ComponentActivity(), KoinComponent {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        actionBar?.hide()

        setContent {
            BeautyLabTheme {
                BeautyLabNavigation()
            }
        }
    }
}
