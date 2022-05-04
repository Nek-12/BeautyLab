package com.nek12.beautylab.ui.screens.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nek12.androidutils.compose.string
import com.nek12.androidutils.extensions.core.copies
import com.nek12.beautylab.R.string
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.common.Mock
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.beautylab.ui.screens.destinations.AboutAppScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductDetailsScreenDestination
import com.nek12.beautylab.ui.screens.favorites.FavoritesAction.GoBack
import com.nek12.beautylab.ui.screens.favorites.FavoritesAction.GoToAboutApp
import com.nek12.beautylab.ui.screens.favorites.FavoritesAction.GoToProductDetails
import com.nek12.beautylab.ui.screens.favorites.FavoritesIntent.ClickedProduct
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.DisplayingFavorites
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Empty
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Error
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Loading
import com.nek12.beautylab.ui.widgets.BLBottomBar
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.beautylab.ui.widgets.BLIcon
import com.nek12.beautylab.ui.widgets.BLTopBar
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigateTo
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun FavoritesScreen(
    navController: NavController,
) = MVIComposable(getViewModel<FavoritesViewModel>()) { state ->

    val scaffoldState = rememberScaffoldState()

    consume { action ->
        when (action) {
            is GoBack -> navController.navigateUp()
            is GoToProductDetails -> navController.navigateTo(ProductDetailsScreenDestination(action.id))
            is GoToAboutApp -> navController.navigateTo(AboutAppScreenDestination)
        }
    }

    FavoritesContent(state, navController, scaffoldState)
}

@Composable
private fun MVIIntentScope<FavoritesIntent, FavoritesAction>.FavoritesContent(
    state: FavoritesState, navController: NavController, scaffoldState: ScaffoldState
) {

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BLBottomBar(navController = navController) },
        topBar = {
            BLTopBar(title = string.favorites, actions = {
                BLIcon(asset = GMRIcon.gmr_info_outline, onClick = { send(FavoritesIntent.ClickedInfo) })
            })
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is Empty -> BLEmptyView()
                is Error -> BLErrorView(state.e.genericMessage.string())
                is Loading -> CircularProgressIndicator()
                is DisplayingFavorites -> LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.favorites, key = { it.id }) { item ->
                        ProductCardItem(item = item, onClick = { send(ClickedProduct(item)) })
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "Favorites", showSystemUi = true, showBackground = true)
private fun FavoritesPreview() = ScreenPreview {
    FavoritesContent(
        state = DisplayingFavorites(Mock.product.copies(20).map { ProductCardItem(it) }),
        navController = rememberNavController(),
        scaffoldState = rememberScaffoldState(),
    )
}
