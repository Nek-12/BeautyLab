package com.nek12.beautylab.ui.screens.product.details

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsAction.*
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsState.*
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.util.*

@Composable
@Destination
fun ProductDetailsScreen(
    id: UUID,
    navigator: DestinationsNavigator,
) = MVIComposable(getViewModel<ProductDetailsViewModel> { parametersOf(id) }) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
        }
    }

    ProductDetailsContent(state)
}

@Composable
private fun MVIIntentScope<ProductDetailsIntent, ProductDetailsAction>.ProductDetailsContent(state: ProductDetailsState) {
    when (state) {
        is Empty -> BLEmptyView()
        is Error -> BLErrorView(state.e.genericMessage.string())
        is Loading -> Unit
    }
}

@Composable
@Preview(name = "ProductDetails", showSystemUi = true, showBackground = true)
private fun ProductDetailsPreview() = ScreenPreview {
    Column {
        ProductDetailsContent(state = Empty)
        ProductDetailsContent(state = Error(Exception()))
    }
}