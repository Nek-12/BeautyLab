package com.nek12.beautylab.ui

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.mikepenz.iconics.typeface.IIcon
import com.nek12.beautylab.R
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.ui.screens.NavGraphs
import com.nek12.beautylab.ui.screens.destinations.FavoritesScreenDestination
import com.nek12.beautylab.ui.screens.destinations.HomeScreenDestination
import com.nek12.beautylab.ui.screens.destinations.NewsScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductListScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProfileScreenDestination
import com.nek12.beautylab.ui.widgets.BLBottomSheet
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.Direction

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun BeautyLabNavigation() {


    val navController = rememberAnimatedNavController()

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    val navHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }
        ),
    )

    BLBottomSheet(navigator = bottomSheetNavigator) {
        DestinationsNavHost(navGraph = NavGraphs.root,
            navController = navController,
            engine = navHostEngine,
            dependenciesContainerBuilder = {
            }
        )
    }
}


enum class BottomBarDestination(
    val direction: Direction,
    val icon: IIcon,
    @StringRes val label: Int,
) {

    Home(HomeScreenDestination, GMRIcon.gmr_home, R.string.home),
    Products(ProductListScreenDestination(), GMRIcon.gmr_shopping_bag, R.string.products),
    Profile(ProfileScreenDestination, GMRIcon.gmr_account_circle, R.string.me),
    Favorites(FavoritesScreenDestination, GMRIcon.gmr_favorite, R.string.favorites),
    News(NewsScreenDestination, GMRIcon.gmr_receipt_long, R.string.news),
}
