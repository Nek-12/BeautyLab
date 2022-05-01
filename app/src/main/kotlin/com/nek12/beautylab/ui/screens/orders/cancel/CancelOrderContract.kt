package com.nek12.beautylab.ui.screens.orders.cancel

import androidx.compose.runtime.Immutable
import com.nek12.beautylab.ui.items.TransactionItem
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class CancelOrderState: MVIState {

    object Loading: CancelOrderState()
    data class CancellingOrder(val order: TransactionItem): CancelOrderState()
    object Success: CancelOrderState()
    data class Failed(val reason: Exception): CancelOrderState()
}

@Immutable
sealed class CancelOrderIntent: MVIIntent {

    object ClickedConfirmCancellation: CancelOrderIntent()
    object ClickedDeclineCancellation: CancelOrderIntent()
    object ClickedDismiss: CancelOrderIntent()
}

@Immutable
sealed class CancelOrderAction: MVIAction {

    object GoBack: CancelOrderAction()
}
