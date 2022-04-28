package com.nek12.beautylab.ui.screens.product.order_confirmation

import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.Error
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.Loading
import com.nek12.flowMVI.android.MVIViewModel

class OrderConfirmationViewModel: MVIViewModel<OrderConfirmationState, OrderConfirmationIntent, OrderConfirmationAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    override suspend fun reduce(intent: OrderConfirmationIntent): OrderConfirmationState = when (intent) {
        else -> TODO()
    }
}
