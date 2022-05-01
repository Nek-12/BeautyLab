package com.nek12.beautylab.ui.screens.orders.cancel

import com.nek12.androidutils.extensions.core.fold
import com.nek12.androidutils.extensions.core.map
import com.nek12.androidutils.extensions.core.orThrow
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.items.TransactionItem
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderAction.GoBack
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderIntent.ClickedConfirmCancellation
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderIntent.ClickedDeclineCancellation
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderIntent.ClickedDismiss
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderState.CancellingOrder
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderState.Failed
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderState.Loading
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderState.Success
import com.nek12.flowMVI.android.MVIViewModel
import java.util.*

class CancelOrderViewModel(
    private val transactionId: UUID,
    private val repo: BeautyLabRepo,
): MVIViewModel<CancelOrderState, CancelOrderIntent, CancelOrderAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Failed(from)

    init {
        loadTransaction()
    }

    override suspend fun reduce(intent: CancelOrderIntent): CancelOrderState = when (intent) {
        is ClickedConfirmCancellation -> {
            cancelTransaction()
            Loading
        }
        is ClickedDeclineCancellation, is ClickedDismiss -> {
            send(GoBack)
            currentState
        }
    }

    private fun loadTransaction() = launchForState {
        CancellingOrder(repo.getTransaction(transactionId).map { TransactionItem(it) }.orThrow())
    }

    private fun cancelTransaction() = launchForState {
        repo.cancelOrder(transactionId).fold(
            onSuccess = { Success },
            onError = { Failed(it) }
        )
    }
}
