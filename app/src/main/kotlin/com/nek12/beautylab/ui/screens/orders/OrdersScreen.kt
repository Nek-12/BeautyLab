package com.nek12.beautylab.ui.screens.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.screens.orders.OrdersAction.GoBack
import com.nek12.beautylab.ui.screens.orders.OrdersState.Empty
import com.nek12.beautylab.ui.screens.orders.OrdersState.Error
import com.nek12.beautylab.ui.screens.orders.OrdersState.Loading
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun OrdersScreen(
    navigator: DestinationsNavigator,
) = MVIComposable(getViewModel<OrdersViewModel>()) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
        }
    }

    OrdersContent(state)
}

@Composable
private fun MVIIntentScope<OrdersIntent, OrdersAction>.OrdersContent(state: OrdersState) {
    when (state) {
        is Empty -> BLEmptyView()
        is Error -> BLErrorView(state.e.genericMessage.string())
        is Loading -> Unit
    }
}

@Composable
@Preview(name = "Orders", showSystemUi = true, showBackground = true)
private fun OrdersPreview() = ScreenPreview {
    Column {
        OrdersContent(state = Empty)
        OrdersContent(state = Error(Exception()))
    }
}
