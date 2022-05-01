package com.nek12.beautylab.ui.screens.orders.history

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.nek12.beautylab.ui.items.TransactionItem
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState
import kotlinx.coroutines.flow.Flow
import java.util.*

@Immutable
sealed class OrderHistoryState: MVIState {

    object Loading: OrderHistoryState()
    data class Error(val e: Throwable?): OrderHistoryState()

    data class DisplayingTransactions(
        val transactions: Flow<PagingData<TransactionItem>>,
    ): OrderHistoryState()
}

@Immutable
sealed class OrderHistoryIntent: MVIIntent {

    data class ClickedOrder(val item: TransactionItem): OrderHistoryIntent()
    data class ClickedOrderAction(val item: TransactionItem): OrderHistoryIntent()
}

@Immutable
sealed class OrderHistoryAction: MVIAction {

    data class GoToCancelTransaction(val transactionId: UUID): OrderHistoryAction()
    data class GoToOrderConfirmation(val productId: UUID): OrderHistoryAction()
    data class GoToProductDetails(val productId: UUID): OrderHistoryAction()
    object GoBack: OrderHistoryAction()
}
