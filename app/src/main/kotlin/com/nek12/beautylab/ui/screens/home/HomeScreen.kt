package com.nek12.beautylab.ui.screens.home

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.beautylab.ui.screens.destinations.LoginScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductDetailsScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductsScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProfileScreenDestination
import com.nek12.beautylab.ui.widgets.BLBottomBar
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.beautylab.ui.widgets.BLSpacer
import com.nek12.beautylab.ui.widgets.BLUserProfileCard
import com.nek12.flowMVI.android.compose.MVIComposable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigateTo
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ProductPager(items: List<ProductCardItem>, onClick: (ProductCardItem) -> Unit) {
    HorizontalPager(count = items.size,
        itemSpacing = 8.dp,
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
                popUpTo(LoginScreenDestination.route) {
                    inclusive = true
                }
            }
            is HomeAction.GoToProductDetails -> navController.navigateTo(ProductDetailsScreenDestination())
            is HomeAction.GoToProductList -> navController.navigateTo(ProductsScreenDestination(action.filters))
            is HomeAction.GoToProfile -> navController.navigateTo(ProfileScreenDestination())
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BLBottomBar(navController) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (state) {
                is HomeState.DisplayingContent -> {
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
                    FlowRow {
                        state.brands.forEach {
                            FilterChip(selected = false, onClick = { send(HomeIntent.ClickedBrand(it)) }) {
                                Text(it.name, style = MaterialTheme.typography.caption)
                            }
                        }
                    }
                    BLSpacer()

                    Text(R.string.explore_categories.string(), style = MaterialTheme.typography.h5)
                    FlowRow {
                        state.categories.forEach {
                            FilterChip(selected = false, onClick = { send(HomeIntent.ClickedCategory(it)) }) {
                                Text(it.name, style = MaterialTheme.typography.caption)
                            }
                        }
                    }
                }
                is HomeState.Error -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    BLErrorView(state.text.string()) //Todo retry
                }
                is HomeState.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
@Preview(name = "HomeScreen", showSystemUi = false, showBackground = true)
private fun HomeScreenPreview() {

}
