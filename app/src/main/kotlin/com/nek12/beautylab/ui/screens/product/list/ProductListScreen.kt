package com.nek12.beautylab.ui.screens.product.list

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.screens.cart.BLEmptyView
import com.nek12.beautylab.ui.screens.product.list.ProductListAction.*
import com.nek12.beautylab.ui.screens.product.list.ProductListState.*
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun ProductListScreen(
    filters: FiltersPayload? = null,
    navigator: DestinationsNavigator,
) = MVIComposable(getViewModel<ProductListViewModel>()) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
        }
    }

    ProductListContent(state)
}

@Composable
private fun MVIIntentScope<ProductListIntent, ProductListAction>.ProductListContent(state: ProductListState) {
    when (state) {
        is Empty -> BLEmptyView()
        is Error -> BLErrorView(state.e.genericMessage.string())
        is Loading -> Unit
    }
}

@Composable
@Preview(name = "ProductList", showSystemUi = true, showBackground = true)
private fun ProductListPreview() = ScreenPreview {
    Column {
        ProductListContent(state = Empty)
        ProductListContent(state = Error(Exception()))
    }
}
