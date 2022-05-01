package com.nek12.beautylab.ui.screens.product.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.beautylab.ui.screens.destinations.ProductDetailsScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductFiltersScreenDestination
import com.nek12.beautylab.ui.screens.product.list.ProductListAction.GoBack
import com.nek12.beautylab.ui.screens.product.list.ProductListAction.GoToFilters
import com.nek12.beautylab.ui.screens.product.list.ProductListAction.GoToProductDetails
import com.nek12.beautylab.ui.screens.product.list.ProductListState.DisplayingContent
import com.nek12.beautylab.ui.screens.product.list.ProductListState.Error
import com.nek12.beautylab.ui.screens.product.list.ProductListState.Loading
import com.nek12.beautylab.ui.widgets.BLBottomBar
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.beautylab.ui.widgets.BLFab
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Destination
fun ProductListScreen(
    filters: FiltersPayload? = null,
    navController: NavController,
    resultRecipient: ResultRecipient<ProductFiltersScreenDestination, FiltersPayload>,
) = MVIComposable(getViewModel<ProductListViewModel> { parametersOf(filters) }) { state ->

    val scaffoldState = rememberScaffoldState()

    resultRecipient.onNavResult { if (it is NavResult.Value) send(ProductListIntent.SelectedFilters(it.value)) }

    consume { action ->
        when (action) {
            is GoBack -> navController.navigateUp()
            is GoToFilters -> navController.navigateTo(ProductFiltersScreenDestination(action.filters))
            is GoToProductDetails -> navController.navigateTo(ProductDetailsScreenDestination(action.id))
        }
    }

    ProductListContent(state, scaffoldState, navController)
}

@Composable
private fun MVIIntentScope<ProductListIntent, ProductListAction>.ProductListContent(
    state: ProductListState,
    scaffoldState: ScaffoldState,
    navController: NavController,
) {

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BLBottomBar(navController) },
        floatingActionButton = {
            BLFab(
                GMRIcon.gmr_filter_alt,
                onClick = { send(ProductListIntent.ClickedFilters) })
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is DisplayingContent -> {
                    val items = state.data.collectAsLazyPagingItems()
                    val isEmpty by derivedStateOf { items.itemCount == 0 }

                    if (isEmpty) {
                        BLEmptyView()
                    } else {
                        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 8.dp)) {
                            items(items) { item ->
                                item?.let {
                                    ProductCardItem(item = it,
                                        onClick = { send(ProductListIntent.ClickedProduct(item)) })
                                }
                            }
                        }
                    }
                }
                is Error -> BLErrorView(state.e.genericMessage.string())
                Loading -> CircularProgressIndicator()
            }
        }
    }
}

@Composable
@Preview(name = "ProductList", showSystemUi = true, showBackground = true)
private fun ProductListPreview() = ScreenPreview {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    Column {
        ProductListContent(state = Error(Exception()), scaffoldState, navController)
    }
}
