package com.nek12.beautylab.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.beautylab.ui.screens.destinations.LoginScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductDetailsScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductListScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProfileScreenDestination
import com.nek12.beautylab.ui.widgets.BLBottomBar
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.beautylab.ui.widgets.BLSpacer
import com.nek12.beautylab.ui.widgets.BLUserProfileCard
import com.nek12.beautylab.ui.widgets.ChipFlowRow
import com.nek12.flowMVI.android.compose.MVIComposable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigateTo
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ProductPager(items: List<ProductCardItem>, onClick: (ProductCardItem) -> Unit) {
    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        count = items.size,
        contentPadding = PaddingValues(4.dp),
        key = { items[it].id }
    ) { page ->
        val item = items[page]

        ProductCardItem(item, onClick = { onClick(item) })
    }
}


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Destination
@Composable
fun HomeScreen(
    navController: NavController,
) = MVIComposable(provider = getViewModel<HomeViewModel>()) { state ->

    val scaffoldState = rememberScaffoldState()

    consume { action ->
        when (action) {
            is HomeAction.GoToLogIn -> navController.navigateTo(LoginScreenDestination()) {
                popUpTo(LoginScreenDestination.route)
                launchSingleTop = true
            }
            is HomeAction.GoToProductDetails -> navController.navigateTo(ProductDetailsScreenDestination(action.id))
            is HomeAction.GoToProductList -> navController.navigateTo(ProductListScreenDestination(action.filters))
            is HomeAction.GoToProfile -> navController.navigateTo(ProfileScreenDestination())
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BLBottomBar(navController) },
    ) { padding ->
        when (state) {
            is HomeState.DisplayingContent -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(4.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                ) {
                    BLUserProfileCard(state.userName, state.userBalance)
                    BLSpacer()

                    Text(R.string.popular_products_title.string(), style = MaterialTheme.typography.h5)
                    ProductPager(state.popularProducts) { send(HomeIntent.ClickedProduct(it)) }
                    BLSpacer()

                    Text(R.string.new_products_title.string(), style = MaterialTheme.typography.h5)
                    ProductPager(state.newProducts) { send(HomeIntent.ClickedProduct(it)) }
                    BLSpacer()


                    Text(R.string.discounted_products_title.string(), style = MaterialTheme.typography.h5)
                    ProductPager(state.newProducts) { send(HomeIntent.ClickedProduct(it)) }
                    BLSpacer()

                    Text(R.string.explore_brands.string(), style = MaterialTheme.typography.h5)
                    ChipFlowRow(state.brands, { it.name }, { send(HomeIntent.ClickedBrand(it)) })
                    BLSpacer()

                    Text(R.string.explore_categories.string(), style = MaterialTheme.typography.h5)
                    ChipFlowRow(state.categories, { it.name }, { send(HomeIntent.ClickedCategory(it)) })
                    BLSpacer()
                }
            }
            is HomeState.Error -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                BLErrorView(state.text.string(), onRetry = { send(HomeIntent.ClickedRetry) }) //Todo retry
            }
            is HomeState.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
@Preview(name = "HomeScreen", showSystemUi = false, showBackground = true)
private fun HomeScreenPreview() {

}
