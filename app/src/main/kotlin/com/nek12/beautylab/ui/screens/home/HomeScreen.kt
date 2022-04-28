package com.nek12.beautylab.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.beautylab.ui.screens.destinations.LoginScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductDetailsScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductListScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProfileScreenDestination
import com.nek12.beautylab.ui.screens.home.HomeAction.GoToLogIn
import com.nek12.beautylab.ui.screens.home.HomeAction.GoToProductDetails
import com.nek12.beautylab.ui.screens.home.HomeAction.GoToProductList
import com.nek12.beautylab.ui.screens.home.HomeAction.GoToProfile
import com.nek12.beautylab.ui.screens.home.HomeIntent.ClickedBrand
import com.nek12.beautylab.ui.screens.home.HomeIntent.ClickedCategory
import com.nek12.beautylab.ui.screens.home.HomeIntent.ClickedProduct
import com.nek12.beautylab.ui.screens.home.HomeIntent.ClickedRetry
import com.nek12.beautylab.ui.screens.home.HomeState.DisplayingContent
import com.nek12.beautylab.ui.screens.home.HomeState.Error
import com.nek12.beautylab.ui.screens.home.HomeState.Loading
import com.nek12.beautylab.ui.widgets.BLBottomBar
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.beautylab.ui.widgets.BLSpacer
import com.nek12.beautylab.ui.widgets.BLUserProfileCard
import com.nek12.beautylab.ui.widgets.ChipFlowRow
import com.nek12.flowMVI.android.compose.MVIComposable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigateTo
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ProductPager(items: List<ProductCardItem>, onClick: (ProductCardItem) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(items, key = { it.id }) { item ->
            ProductCardItem(item, onClick = { onClick(item) })
        }
    }

// Disabled due to poor performance
//    HorizontalPager(
//        modifier = Modifier.fillMaxWidth(),
//        count = items.size,
//        contentPadding = PaddingValues(4.dp),
//        key = { items[it].id }
//    ) { page ->
//        val item = items[page]
//        ProductCardItem(item, onClick = { onClick(item) })
//    }
}

@RootNavGraph(start = true)
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Destination
@Composable
fun HomeScreen(
    navController: NavController,
) = MVIComposable(provider = getViewModel<HomeViewModel>()) { state ->

    val scaffoldState = rememberScaffoldState()

    consume { action ->
        when (action) {
            is GoToLogIn -> navController.navigateTo(LoginScreenDestination())
            is GoToProductDetails -> navController.navigateTo(ProductDetailsScreenDestination(action.id))
            is GoToProductList -> navController.navigateTo(ProductListScreenDestination(action.filters))
            is GoToProfile -> navController.navigateTo(ProfileScreenDestination())
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BLBottomBar(navController) },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is DisplayingContent -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        BLUserProfileCard(state.userName, state.userBalance, Modifier.padding(8.dp))
                        BLSpacer()

                        Text(R.string.popular_products_title.string(), style = MaterialTheme.typography.h5)
                        ProductPager(state.popularProducts) { send(ClickedProduct(it)) }
                        BLSpacer()

                        Text(R.string.new_products_title.string(), style = MaterialTheme.typography.h5)
                        ProductPager(state.newProducts) { send(ClickedProduct(it)) }
                        BLSpacer()


                        Text(R.string.discounted_products_title.string(), style = MaterialTheme.typography.h5)
                        ProductPager(state.newProducts) { send(ClickedProduct(it)) }
                        BLSpacer()

                        Text(R.string.explore_brands.string(), style = MaterialTheme.typography.h5)
                        ChipFlowRow(state.brands, { it.name }, { send(ClickedBrand(it)) })
                        BLSpacer()

                        Text(R.string.explore_categories.string(), style = MaterialTheme.typography.h5)
                        ChipFlowRow(state.categories, { it.name }, { send(ClickedCategory(it)) })
                        BLSpacer()
                    }
                }
                is Error -> BLErrorView(state.text.string(), onRetry = { send(ClickedRetry) })
                is Loading -> CircularProgressIndicator()
            }
        }
    }
}

@Composable
@Preview(name = "HomeScreen", showSystemUi = false, showBackground = true)
private fun HomeScreenPreview() {

}
