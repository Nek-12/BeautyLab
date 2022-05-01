package com.nek12.beautylab.ui.screens.orders.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.nek12.androidutils.compose.string
import com.nek12.androidutils.extensions.core.copies
import com.nek12.beautylab.common.Mock
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.items.TransactionItem
import com.nek12.beautylab.ui.screens.destinations.CancelOrderScreenDestination
import com.nek12.beautylab.ui.screens.destinations.OrderConfirmationScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductDetailsScreenDestination
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryAction.GoBack
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryAction.GoToCancelTransaction
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryAction.GoToOrderConfirmation
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryAction.GoToProductDetails
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryIntent.ClickedOrder
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryIntent.ClickedOrderAction
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryState.DisplayingTransactions
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryState.Error
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryState.Loading
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.flow
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun OrderHistoryScreen(
    navigator: DestinationsNavigator
) = MVIComposable(getViewModel<OrderHistoryViewModel>()) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
            is GoToCancelTransaction -> navigator.navigate(CancelOrderScreenDestination(action.transactionId))
            is GoToOrderConfirmation -> navigator.navigate(OrderConfirmationScreenDestination(action.productId))
            is GoToProductDetails -> navigator.navigate(ProductDetailsScreenDestination(action.productId))
        }
    }

    //todo: on order cancelled or otherwise changed, state is not updated

    OrderHistoryContent(state)
}

@Composable
private fun MVIIntentScope<OrderHistoryIntent, OrderHistoryAction>.OrderHistoryContent(state: OrderHistoryState) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (state) {
            is Error -> BLErrorView(state.e.genericMessage.string())
            is Loading -> Unit
            is DisplayingTransactions -> {
                val items = state.transactions.collectAsLazyPagingItems()

                LazyColumn(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    items(items, key = { it.id }) { item ->
                        item?.let {
                            TransactionItem(
                                item = item,
                                onClick = { send(ClickedOrder(item)) },
                                onActionClicked = { send(ClickedOrderAction(item)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "OrderHistory", showSystemUi = true, showBackground = true)
private fun OrderHistoryPreview() = ScreenPreview {
    val flow = flow {
        emit(PagingData.from(TransactionItem(Mock.transaction).copies(10)))
    }
    OrderHistoryContent(DisplayingTransactions(flow))
}
