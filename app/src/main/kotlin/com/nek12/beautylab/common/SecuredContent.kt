package com.nek12.beautylab.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.nek12.beautylab.data.net.AuthManager
import com.nek12.beautylab.ui.screens.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.navigation.navigateTo
import org.koin.androidx.compose.inject


@Composable
fun SecuredContent(navController: NavController, content: @Composable () -> Unit) {

    val authManager: AuthManager by inject()

    if (authManager.isLoggedIn)
        content()
    else navController.navigateTo(LoginScreenDestination)
}
