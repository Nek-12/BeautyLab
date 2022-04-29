package com.nek12.beautylab.ui.screens.product.order_confirmation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationAction.GoBack
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.Empty
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.Error
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.Loading
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
@Destination
fun OrderConfirmationScreen(
    productId: UUID,
    navigator: DestinationsNavigator,
) = MVIComposable(getViewModel<OrderConfirmationViewModel>()) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
        }
    }

    OrderConfirmationContent(state)
}

@Composable
private fun MVIIntentScope<OrderConfirmationIntent, OrderConfirmationAction>.OrderConfirmationContent(state: OrderConfirmationState) {
    when (state) {
        is Empty -> BLEmptyView()
        is Error -> BLErrorView(state.e.genericMessage.string())
        is Loading -> Unit
    }
}

@Composable
@Preview(name = "OrderConfirmation", showSystemUi = true, showBackground = true)
private fun OrderConfirmationPreview() = ScreenPreview {
    Column {
        OrderConfirmationContent(state = Empty)
        OrderConfirmationContent(state = Error(Exception()))
    }
}
