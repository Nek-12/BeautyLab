package com.nek12.beautylab.ui.widgets

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.ui.BottomBarDestination
import com.nek12.beautylab.ui.screens.appDestination
import com.nek12.beautylab.ui.screens.destinations.Destination
import com.ramcosta.composedestinations.navigation.navigateTo

@Composable
fun BLBottomBar(
    navController: NavController,
) {
    val currentDestination: Destination? = navController.currentBackStackEntryAsState().value?.appDestination()

    BottomNavigation {
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigateTo(destination.direction) {
                        launchSingleTop = true
                    }
                },
                icon = { BLIcon(destination.icon, contentDescription = destination.label.string()) },
                label = { Text(destination.label.string()) },
            )
        }
    }
}


@Composable
@Preview(name = "BLBottomBar", showSystemUi = false, showBackground = true)
private fun BLBottomBarPreview() {

}