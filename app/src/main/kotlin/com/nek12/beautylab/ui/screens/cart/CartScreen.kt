package com.nek12.beautylab.ui.screens.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.screens.cart.CartAction.*
import com.nek12.beautylab.ui.screens.cart.CartState.*
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun CartScreen(
    navigator: DestinationsNavigator,
) = MVIComposable(getViewModel<CartViewModel>()) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
        }
    }

    CartContent(state)
}

@Composable
private fun MVIIntentScope<CartIntent, CartAction>.CartContent(state: CartState) {
    when (state) {
        is Empty -> BLEmptyView()
        is Error -> BLErrorView(state.e.genericMessage.string())
        is Loading -> Unit
    }
}

@Composable
fun BLEmptyView() {
    TODO("Not yet implemented")
}

@Composable
@Preview(name = "Cart", showSystemUi = true, showBackground = true)
private fun CartPreview() = ScreenPreview {
    Column {
        CartContent(state = Empty)
        CartContent(state = Error(Exception()))
    }
}
