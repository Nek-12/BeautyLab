package com.nek12.beautylab.ui.screens.orders.history

import androidx.paging.map
import com.nek12.beautylab.core.model.net.TransactionStatus.CANCELED
import com.nek12.beautylab.core.model.net.TransactionStatus.COMPLETED
import com.nek12.beautylab.core.model.net.TransactionStatus.PENDING
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.items.TransactionItem
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryAction.GoToCancelTransaction
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryAction.GoToOrderConfirmation
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryAction.GoToProductDetails
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryIntent.ClickedOrder
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryIntent.ClickedOrderAction
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryState.DisplayingTransactions
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryState.Error
import com.nek12.beautylab.ui.screens.orders.history.OrderHistoryState.Loading
import com.nek12.flowMVI.android.MVIViewModel
import kotlinx.coroutines.flow.map

class OrderHistoryViewModel(
    private val repo: BeautyLabRepo,
): MVIViewModel<OrderHistoryState, OrderHistoryIntent, OrderHistoryAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    init {
        val flow = repo.getOwnTransactions().map { data -> data.map { TransactionItem(it) } }
        set(DisplayingTransactions(flow))
    }

    override suspend fun reduce(intent: OrderHistoryIntent): OrderHistoryState = when (intent) {
        is ClickedOrderAction -> {
            when (intent.item.status) {
                PENDING -> {
                    send(GoToCancelTransaction(intent.item.id))
                    currentState
                }
                COMPLETED -> {
                    intent.item.productId?.let { send(GoToOrderConfirmation(it)) }
                }
                CANCELED -> error("Such action is not possible")
            }
            currentState
        }
        is ClickedOrder -> {
            intent.item.productId?.let {
                send(GoToProductDetails(intent.item.productId))
            }
            currentState
        }
    }
}
