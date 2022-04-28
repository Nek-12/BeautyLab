package com.nek12.beautylab.ui.screens.product.order_confirmation

import androidx.compose.runtime.Immutable
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class OrderConfirmationState: MVIState {

    object Loading: OrderConfirmationState()
    object Empty: OrderConfirmationState()
    data class Error(val e: Throwable?): OrderConfirmationState()
}

@Immutable
sealed class OrderConfirmationIntent: MVIIntent

@Immutable
sealed class OrderConfirmationAction: MVIAction {

    object GoBack: OrderConfirmationAction()
}
